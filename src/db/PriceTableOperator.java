package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PriceTableOperator {
	// contains name, address, salary, job title
	private static String tableName = "price";

	private Connection conn;

	// schema: id, state, price
	public PriceTableOperator(Connection conn) {
		this.conn = conn;
	}

	public boolean update(String id, String state, float price) {
		// UPDATE price SET `price` = 20000.0 where `id`= '1' and `state` = 'NY'
		try {
			String sql = "UPDATE " + tableName + " SET `price` = ? where `id`= ? and `state` = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, price);
			ps.setString(2, id);
			ps.setString(3, state);
			System.out.println(ps);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean insert(String id, String state, float price) {
		try {
			String sql = "INSERT IGNORE INTO `" + tableName + "` (`id`, `state`, `price`) VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, state);
			ps.setFloat(3, price);
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
		this.insert("P0", "NY", 100);
		this.insert("P0", "NJ", 110);
		this.insert("P2", "WA", 5);
		this.insert("P3", "IL", 10);
	}

	public void createTable() {
		Statement statement;
		try {
			statement = this.conn.createStatement();

			String sql = "CREATE TABLE " + tableName + " (" + "id VARCHAR(255) NOT NULL," + "state VARCHAR(255),"
					+ "price FLOAT," + "PRIMARY KEY (id, state)" + ")";
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
