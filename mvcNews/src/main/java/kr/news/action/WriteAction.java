package kr.news.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		NewsVO news = new NewsVO();
		news.setTitle(request.getParameter("title"));
		news.setWriter(request.getParameter("writer"));
		news.setPasswd(request.getParameter("passwd"));
		news.setEmail(request.getParameter("email"));
		news.setArticle(request.getParameter("article"));
		//파일 업로드 안됐을 시 빈 문자열로 처리
		news.setFilename(FileUtil.uploadFile(request, "filename"));
		
		NewsDAO dao = NewsDAO.getInstance();
		dao.registerNews(news);
		
		return "write.jsp";
	}

}
