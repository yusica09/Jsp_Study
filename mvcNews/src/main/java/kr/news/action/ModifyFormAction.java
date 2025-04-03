package kr.news.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;

public class ModifyFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long num = Long.parseLong(request.getParameter("num"));
		NewsDAO dao = NewsDAO.getInstance();
		NewsVO newsVO = dao.getNews(num);
		
		request.setAttribute("newsVO", newsVO);
		
		return "modifyForm.jsp";
	}

}





