package kr.news.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;
import kr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		NewsDAO dao = NewsDAO.getInstance();
		int count = dao.getCount();
		//페이지 처리
		/*
		 * currentPage : 현재 페이지 지정
		 * count : 총레코드 수
		 * rowCount : 현재 페이지에 보여질 레코드 수
		 * pageCount : 현재 페이지에 표시될 페이지 수
		 * pageUrl : 현재 페이지를 보여주기 위한 요청URL 
		 */
		PagingUtil page = 
				new PagingUtil(Integer.parseInt(pageNum),
				                     count,20,10,"list.do");
		List<NewsVO> list = null;
		if(count>0) {
			list = dao.getList(page.getStartRow(),
					           page.getEndRow());
		}
		//request에 데이터 저장
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//JSP 경로 반환
		return "list.jsp";
	}

}






