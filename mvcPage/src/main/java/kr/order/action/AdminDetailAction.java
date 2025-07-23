package kr.order.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class AdminDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) { //관리자로 로그인하지 않은 경우
			return "common/accessDenied.jsp";
		}
		
		long order_num = Long.parseLong(request.getParameter("order_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		//주문내역
		OrderVO order = dao.getOrder(order_num);
		//주문 상품 정보
		List<OrderDetailVO> detailList = dao.getListOrderDetail(order_num);
		
		request.setAttribute("order", order);
		request.setAttribute("detailList", detailList);
		
		return "order/admin_datail.jsp";
	}

}
