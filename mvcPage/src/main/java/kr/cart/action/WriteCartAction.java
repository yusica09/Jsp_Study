package kr.cart.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.StringUtil;

public class WriteCartAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else { //로그인이 된 경우
			CartVO cart = new CartVO();
			cart.setItem_num(Long.parseLong(request.getParameter("item_num")));
			cart.setOrder_quantity(Integer.parseInt(request.getParameter("order_quantity")));
			cart.setMem_num(user_num);
			
			CartDAO dao = CartDAO.getInstance();
			CartVO db_cart = dao.getCart(cart);
			
			if(db_cart == null) { //동일 상품이 없을 경우
				dao.insertCart(cart);
				mapAjax.put("result", "success");
			}else {//동일 상품이 있을 경우
				//재고수를 구하기 위해 ItemDAO 호출
				ItemDAO itemDAO = ItemDAO.getInstance();
				ItemVO item = itemDAO.getItem(db_cart.getItem_num());
				//구매수량 합산(기존 장바구니에 저장된 구매수량 + 새로 입력한 구매수량)
				int order_quantity = db_cart.getOrder_quantity()+cart.getOrder_quantity();
				
				if(item.getQuantity() < order_quantity) {
					//상품재고 수량보다 장바구니에 담은 구매수량이 더 많음
					mapAjax.put("result", "overQuantity");
				}else {
					//합산한 구매수량으로 변경
					cart.setOrder_quantity(order_quantity);
					dao.updateCartByItem_num(cart);
					mapAjax.put("result", "success");
				}
			}
		}
		//JSON 문자열로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}
