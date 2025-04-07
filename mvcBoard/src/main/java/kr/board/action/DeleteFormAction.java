package kr.board.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;

public class DeleteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long num = Long.parseLong(request.getParameter("num"));
		
		//request에 데이터 저장
		request.setAttribute("num", num);
		//JSP 경로 반환
		return "deleteForm.jsp";
	}

}
