package kr.order.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyFormAction implements Action{

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
		
		//사용자가 배송상태를 5로 변경했을 경우
		if(order.getStatus() == 5) {
			request.setAttribute("notice_msg", "사용자가 배송상태 주문취소로 변경해서 관리자가 배송지 정보를 수정할 수 없습니다."); 
			request.setAttribute("notice_url", "/order/adminDetail.do?order_num="+order.getOrder_num()); 
			
			return "common/alert_view.jsp";
		}
		
		request.setAttribute("order", order);
		
		return "order/admin_modifyForm.jsp";
	}

}
