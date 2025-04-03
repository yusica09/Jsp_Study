package kr.news.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 반환
		long num = Long.parseLong(request.getParameter("num"));
		String passwd = request.getParameter("passwd");

		NewsDAO dao = NewsDAO.getInstance();
		//비밀번호 인증 작업 수행
		NewsVO db_news = dao.getNews(num);
		boolean check = false;
		if(db_news!=null) {
			//비밀번호 인증
			check = db_news.isCheckedPassword(passwd);
		}
		if(check) {//비밀번호 일치
			//글 삭제
			dao.deleteNews(num);
			//파일 삭제
			FileUtil.removeFile(request, db_news.getFilename());
		}
		//UI 처리를 위해서 check 저장
		request.setAttribute("check", check);
		//JSP 경로 반환
		return "delete.jsp";
	}

}










