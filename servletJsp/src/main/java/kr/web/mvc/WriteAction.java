package kr.web.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//request에 데이터 저장
		request.setAttribute("message", "글 등록 완료");
		//JSP 경로 반환
		return "/views2/write.jsp";
	}
	
}
