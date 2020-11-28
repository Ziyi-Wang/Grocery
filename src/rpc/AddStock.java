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
 * Servlet implementation class AddStock
 */
@WebServlet("/AddStock")
public class AddStock extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddStock() {
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
			if (!input.has("warehouse_id") || !input.has("product_id") || !input.has("quantity")) {
				response.setStatus(400);
				obj.put("status", "missing parameter");
				RpcHelper.writeJsonObject(response, obj);
				return;
			}

			String warehouseId = input.getString("warehouse_id");
			String productId = input.getString("product_id");
			float additionalQuantity = (float) input.getDouble("quantity");
			boolean result = conn.stockTableOperator.update(warehouseId, productId, additionalQuantity);
			if (result) {
				obj.put("status", "success");
			} else {
				obj.put("status", "stock update fail");
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
