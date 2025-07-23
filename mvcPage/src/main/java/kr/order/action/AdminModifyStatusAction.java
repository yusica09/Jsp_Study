package kr.order.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyStatusAction implements Action{

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
		
		OrderVO order = new OrderVO();
		order.setStatus(Integer.parseInt(request.getParameter("status")));
		order.setOrder_num(Long.parseLong(request.getParameter("order_num")));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order.getOrder_num());
		
		//배송상태가 5로 변경 되어있는지 체크
		if(db_order.getStatus() == 5) {
			request.setAttribute("notice_msg", "배송상태가 주문취소로 변경되어 배송상태를 수정할 수 없습니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/order/adminDetail.do?order_num="+order.getOrder_num());
			
			return "common/alert_view.jsp";
		}
		
		//배송상태 수정
		dao.updateOrderStauts(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/order/adminDetail.do?order_num="+order.getOrder_num());
		
		return "common/alert_view.jsp";
	}

}
