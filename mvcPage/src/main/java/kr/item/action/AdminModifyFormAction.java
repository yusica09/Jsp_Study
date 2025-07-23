package kr.item.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class AdminModifyFormAction implements Action{

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
		//관리자로 로그인한 경우
		long item_num = Long.parseLong(request.getParameter("item_num"));
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO item = dao.getItem(item_num);
		
		request.setAttribute("item", item);
		return "item/admin_modifyForm.jsp";
	}

}
