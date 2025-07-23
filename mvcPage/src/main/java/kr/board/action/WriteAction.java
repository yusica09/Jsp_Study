package kr.board.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		BoardVO board = new BoardVO();
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setIp(request.getRemoteAddr());
		board.setFilename(
				FileUtil.uploadFile(request, "filename"));
		board.setMem_num(user_num);//작성자 회원번호
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.insertBoard(board);
		
		request.setAttribute("notice_msg", "글쓰기 완료");
		request.setAttribute("notice_url", 
				 request.getContextPath()+"/board/list.do");		
		
		return "common/alert_view.jsp";
	}

}






