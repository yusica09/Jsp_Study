package kr.web.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//request에 데이터 저장
				request.setAttribute("message", "게시글 삭제");
				//JSP 경로 반환
				return "/views2/delete.jsp";
	}

}
