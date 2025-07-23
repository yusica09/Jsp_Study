package kr.order.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyStatusFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {
			return "common/accessDenied.jsp";
		}
		
		long order_num = Long.parseLong(request.getParameter("order_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO order = dao.getOrder(order_num);
		
		request.setAttribute("order", order);
		
		return "order/admin_modifyStatusForm.jsp";
	}

}
