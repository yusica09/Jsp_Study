package kr.order.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class UserOrderCancelAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		long order_num = Long.parseLong(request.getParameter("order_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_num);
		if(db_order.getMem_num() != user_num) {
			//타인의 주문정보는 취소할 수 없음
			return "common/accessDenied.jsp";
		}
		
		//배송상태 체크
		if(db_order.getStatus() > 1) {
			//배송준비중 이상으로 관리자가 변경한 상품을 주문자는 취소 불가
			request.setAttribute("notice_msg", "배송상태가 변경되어 주문자가 주문을 취소할 수 없음");
			request.setAttribute("notice_url", request.getContextPath()+"/order/orderDetail.do?order_num="+order_num);
			
			return "common/alert_view.jsp";
		}
		//주문 취소
		dao.updateOrderCancel(order_num);
		
		request.setAttribute("notice_msg", "주문취소가 완료되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/order/orderDetail.do?order_num="+order_num);
		
		return "common/alert_view.jsp";
	}

}
