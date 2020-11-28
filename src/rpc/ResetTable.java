package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.MySQLConnection;

/**
 * Servlet implementation class ResetTable
 */
@WebServlet("/ResetTable")
public class ResetTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResetTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MySQLConnection conn = new MySQLConnection();
		try {
			JSONObject obj = new JSONObject();
			if (!conn.isConnected()) {
				response.setStatus(500);
				obj.put("status", "database connection error");
				RpcHelper.writeJsonObject(response, obj);
			} else {
				conn.resetAllTables();
				obj.put("status", "tables have been reset successfully");
				RpcHelper.writeJsonObject(response, obj);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			conn.close();
		}
	}
}
