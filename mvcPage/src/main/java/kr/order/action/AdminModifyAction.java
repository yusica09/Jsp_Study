package kr.order.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.jsp";
		}
		
		Integer user_auth = 
			(Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "common/notice.jsp";
		}
		
		long order_num = Long.parseLong(
				  request.getParameter("order_num"));
		
		OrderDAO orderDAO = OrderDAO.getInstance();
		OrderVO db_order = orderDAO.getOrder(order_num);
		
		if(db_order.getStatus()>1) {
			//주문정보를 수정하기 전에 DB에 저장되어 있는 배송상태가
			//주문취소이면 정보 수정 불가능(사용자가 관리자 정보 수정전에
			//주문 취소한 경우)
			request.setAttribute("notice_msg", 
					"배송상태가 변경되어서 관리자가 배송지정보를 수정할 수 없습니다.");
			request.setAttribute("notice_url", request.getContextPath()
					  +"/order/adminDetail.do?order_num="+order_num);
			return "common/alert_view.jsp";
		}
		
		OrderVO order = new OrderVO();
		order.setOrder_num(order_num);
		order.setReceive_name(request.getParameter("receive_name"));
		order.setReceive_post(request.getParameter("receive_post"));
		order.setReceive_address1(request.getParameter("receive_address1"));
		order.setReceive_address2(request.getParameter("receive_address2"));
		order.setReceive_phone(request.getParameter("receive_phone"));
		order.setNotice(request.getParameter("notice"));
		
		orderDAO.updateOrder(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()
			  +"/order/adminDetail.do?order_num="+order_num);
		
		return "common/alert_view.jsp";
	}

}

