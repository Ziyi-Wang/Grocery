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
 * Servlet implementation class UpdatePrice
 */
@WebServlet("/UpdatePrice")
public class UpdatePrice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdatePrice() {
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
		JSONObject obj = new JSONObject();
		try {
			if (!conn.isConnected()) {
				response.setStatus(500);
				obj.put("status", "database connection error");
				RpcHelper.writeJsonObject(response, obj);
				return;
			}

			JSONObject input = RpcHelper.readJSONObject(request);
			if (!input.has("id") || !input.has("state") || !input.has("price")) {
				response.setStatus(400);
				obj.put("status", "wrong parameter");
				RpcHelper.writeJsonObject(response, obj);
				return;
			}

			String productId = input.getString("id");
			String state = input.getString("state");
			float newPrice = (float) input.getDouble("price");
			boolean result = conn.priceTableOperator.update(productId, state, newPrice);
			if (result) {
				obj.put("status", "success");
			} else {
				obj.put("status", "update fail");
			}

			RpcHelper.writeJsonObject(response, obj);

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
		} finally {
			conn.close();
		}
	}

}
