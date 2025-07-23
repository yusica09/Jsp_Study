package kr.item.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;

public class AdminWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//관리자 여부 체크
		Integer user_auth =
				(Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {
			return "common/accessDenied.jsp";
		}
		//관리자로 로그인한 경우
		return "item/admin_writeForm.jsp";
	}

}
