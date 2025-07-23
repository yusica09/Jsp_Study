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

public class ModifyCartAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			long item_num = Long.parseLong(request.getParameter("item_num"));
			int order_quantity = Integer.parseInt(request.getParameter("order_quantity"));
			
			ItemDAO itemDAO = ItemDAO.getInstance();
			ItemVO item = itemDAO.getItem(item_num);
			if(item.getStatus()==1) { //상품 미표시
				mapAjax.put("result", "noSale");
			}else if(item.getQuantity() < order_quantity){
				//상품 재고 수량보다 장바구니에 담은 구매 수량이 더 많음
				mapAjax.put("result", "overQuantity");
			}else {
				//표시 상품이며 재고가 부족하지 않음
				CartVO cart = new CartVO();
				cart.setCart_num(Integer.parseInt(request.getParameter("cart_num")));
				cart.setOrder_quantity(order_quantity);
				
				CartDAO cartDAO = CartDAO.getInstance();
				//구매 수량 변경
				cartDAO.updateCart(cart);
				
				mapAjax.put("result", "success");
			}
		}
		return StringUtil.parseJSON(request, mapAjax);
	}

}
