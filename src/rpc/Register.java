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
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MySQLConnection conn = new MySQLConnection();
		try {
			JSONObject obj = new JSONObject();
			if (!conn.isConnected()) {
				response.setStatus(500);
				obj.put("status", "database connection error");
				RpcHelper.writeJsonObject(response, obj);
				return;
			}
			/*
			 * JSONObject input = RpcHelper.readJSONObject(request); String userId =
			 * input.getString("user_id"); String password = input.getString("password");
			 * String firstname = input.getString("first_name"); String lastname =
			 * input.getString("last_name");
			 * 
			 * if (conn.registerUser(userId, password, firstname, lastname)) {
			 * obj.put("status", "OK"); } else { obj.put("status", "User Already Exists"); }
			 * RpcHelper.writeJsonObject(response, obj);
			 */
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			conn.close();
		}
	}
}
