package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MySQLConnection {
	private Connection conn;

	public StaffTableOperator staffTableOperator;

	public PriceTableOperator priceTableOperator;

	public StockTableOperator stockTableOperator;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			this.conn = DriverManager.getConnection(MySQLDBUtil.URL);
			this.staffTableOperator = new StaffTableOperator(this.conn);
			this.priceTableOperator = new PriceTableOperator(this.conn);
			this.stockTableOperator = new StockTableOperator(this.conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return conn != null;
	}

	public void close() {
		try {
			if (conn == null) {
				return;
			} else {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetAllTables() {
		this.staffTableOperator.reset();
		this.priceTableOperator.reset();
		this.stockTableOperator.reset();
	}

	public boolean verifyLogin(String userId, String password) {
		if (conn == null) {
			return false;
		}

		try {
			String sql = "SELECT * FROM users WHERE user_id = ? AND password = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean registerUser(String userId, String password, String firstname, String lastname) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		try {
			String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			ps.setString(3, firstname);
			ps.setString(4, lastname);

			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
