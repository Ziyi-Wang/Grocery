package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockTableOperator {
	// contains name, address, salary, job title
	private static String tableName = "stock";

	private Connection conn;

	// schema: id, state, price
	public StockTableOperator(Connection conn) {
		this.conn = conn;
	}

	public float getQuantity(String warehouseId, String productId) {
		try {
			String sql = "SELECT quantity from " + tableName + " where `warehouse_id`= ? and `product_id` = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, warehouseId);
			ps.setString(2, productId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getFloat("quantity");
			}

			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean update(String warehouseId, String productId, float quantity) {
		// UPDATE price SET `quantity` = 20000.0 where `warehouse_id`= '1' and
		// `product_id` = 'NY'
		try {
			float previousQuantity = this.getQuantity(warehouseId, productId);
			float newQuantity = previousQuantity + quantity;
			String sql = "UPDATE " + tableName + " SET `quantity` = ? where `warehouse_id`= ? and `product_id` = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, newQuantity);
			ps.setString(2, warehouseId);
			ps.setString(3, productId);
			System.out.println(ps);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean insert(String warehouseId, String productId, float quantity) {
		try {
			String sql = "INSERT IGNORE INTO `" + tableName
					+ "` (`warehouse_id`, `product_id`, `quantity`) VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, warehouseId);
			ps.setString(2, productId);
			ps.setFloat(3, quantity);
			System.out.println(ps);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void reset() {
		this.dropTable();
		this.createTable();
		this.initialize();
	}

	public void initialize() {
		this.insert("W0", "P0", 1000);
		this.insert("W1", "P0", 1100);
		this.insert("W2", "P1", 12);
		this.insert("W3", "P2", 300);
	}

	public void createTable() {
		Statement statement;
		try {
			statement = this.conn.createStatement();

			String sql = "CREATE TABLE " + tableName + " (" + "warehouse_id VARCHAR(255) NOT NULL,"
					+ "product_id VARCHAR(255)," + "quantity FLOAT," + "PRIMARY KEY (warehouse_id, product_id)" + ")";
			System.out.println("table creation: " + sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dropTable() {
		Statement statement;
		try {
			statement = this.conn.createStatement();
			String sql = "DROP TABLE IF EXISTS " + tableName;
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
