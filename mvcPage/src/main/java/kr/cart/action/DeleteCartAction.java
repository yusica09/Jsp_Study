package kr.cart.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.cart.dao.CartDAO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DeleteCartAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			 mapAjax.put("result", "logout");
		}else {
			CartDAO dao = CartDAO.getInstance();
			dao.deleteCart(Long.parseLong(
					 request.getParameter("cart_num")));
			mapAjax.put("result", "success");
		}
		//JSON 문자열 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}







