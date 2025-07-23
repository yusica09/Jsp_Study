package kr.web.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//request에 데이터 세팅
		request.setAttribute("message", "상세 페이지입니다.");
		//JSP 경로 반환
		return "/views2/detail.jsp";
	}

}
