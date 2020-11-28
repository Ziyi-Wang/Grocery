package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffTableOperator {
	// contains name, address, salary, job title
	private static String tableName = "staff";

	private Connection conn;

	public StaffTableOperator(Connection conn) {
		this.conn = conn;
	}

	public boolean insert(String id, String name, String address, float salary, String jobTitle) {
		try {
			// INSERT INTO `staff`(`id`, `name`, `address`, `salary`, `job_title`) VALUES
			// (`id`,`id`,`id`,4.0,`id`)
			String sql = "INSERT IGNORE INTO `" + tableName
					+ "` (`id`, `name`, `address`, `salary`, `job_title`) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, address);
			ps.setFloat(4, salary);
			ps.setString(5, jobTitle);
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
		this.setStaff();
	}

	public void setStaff() {
		this.insert("1000", "Andy Smith", "long island city", 65000, "staff level 1");
		this.insert("1001", "Bob Watson", "new port", 68000, "staff level 2");
		this.insert("1002", "Mary Lee", "new york", 78000, "staff level 3");
	}

	public void createTable() {
		Statement statement;
		try {
			statement = this.conn.createStatement();

			String sql = "CREATE TABLE " + tableName + " (" + "id VARCHAR(255) NOT NULL," + "name VARCHAR(255),"
					+ "address VARCHAR(255)," + "salary FLOAT," + "job_title VARCHAR(255)," + "PRIMARY KEY (id)" + ")";
			System.out.println("table creation staff: " + sql);
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
