package kr.order.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class UserOrderFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//로그인 한 경우
		
		//POST 방식으로 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "common/accessDenied.jsp";
		}
		
		CartDAO dao = CartDAO.getInstance();
		int all_total = dao.getTotalByMem_num(user_num);
		if(all_total <= 0) {
			request.setAttribute("notice_msg", "정상적인 주문이 아니거나 상품의 수량이 부족합니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/item/list.do");
			return "common/alert_view.jsp";
		}
		
		//정상적인 주문일 경우
		//장바구니에 담겨있는 상품 정보 호출
		List<CartVO> cartList = dao.getListCart(user_num);
		ItemDAO itemDAO = ItemDAO.getInstance();
		for(CartVO cart : cartList) {
			ItemVO item = itemDAO.getItem(cart.getItem_num());
			
			if(item.getStatus() == 1) {
				//상품 미표시
				request.setAttribute("notice_msg","["+item.getName()+"]상품 판매 중지");
				request.setAttribute("notice_url", request.getContextPath()+"/cart/list.do");
				
				return "common/alert_view.jsp";
			}
			if(item.getQuantity() < cart.getOrder_quantity()) {
				//재고 수량 부족
				request.setAttribute("notice_msg", "["+item.getName()+"]재고수량 부족으로 주문 불가");
				request.setAttribute("notice_url", request.getContextPath()+"cart/list.do");
				
				return "common/alert_view.jsp";
			}
		}
		
		request.setAttribute("list", cartList);
		request.setAttribute("all_total", all_total);
		
		return "order/user_orderForm.jsp";
	}

}
