package kr.news.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long num = Long.parseLong(request.getParameter("num"));
		
		NewsDAO dao = NewsDAO.getInstance();
		NewsVO news = dao.getNews(num);
		
		request.setAttribute("news", news);
		
		return "detail.jsp";
	}

}
