package kr.order.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class UserModifyAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");

		if (user_num == null) { //로그인되지 않은 경우
			return "redirect:/member/loginForm.do";
		} // if

		//로그인한 경우
		long order_num = Long.parseLong(request.getParameter("order_num"));

		//DB에 저장된 정보 읽기
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_num);

		if (db_order.getMem_num() != user_num) {
			//구매자 회원번호와 로그인한 회원번호가 불일치할 경우
			return "common/accessDenied.jsp";
		} // if

		//배송지 수정 전 배송상태 체크
		if (db_order.getStatus() > 1) {
			//배송준비중 이상으로 관리자가 변경한 상품을 주문자가 변경할 수 없음
			request.setAttribute("notice_msg", 
					"배송상태가 변경되어 주문자가 주문정보 변경 불가");
			request.setAttribute("notice_url", 
					request.getContextPath()+"/order/orderList.do");
			return "common/alert_view.jsp";
		} // if

		//전송된 정보 저장
		OrderVO order = new OrderVO();
		order.setOrder_num(order_num);
		order.setReceive_name(request.getParameter("receive_name"));
		order.setReceive_post(request.getParameter("receive_post"));
		order.setReceive_address1(request.getParameter("receive_address1"));
		order.setReceive_address2(request.getParameter("receive_address2"));
		order.setReceive_phone(request.getParameter("receive_phone"));
		order.setNotice(request.getParameter("notice"));
		
		//배송지 정보 수정
		dao.updateOrder(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", 
				request.getContextPath()+"/order/orderDetail.do?order_num="+order_num);
		
		return "common/alert_view.jsp";
	}
}