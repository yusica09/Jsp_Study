package kr.board.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;

public class ModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BoardVO boardVO = new BoardVO();
		boardVO.setNum(Long.parseLong(
				             request.getParameter("num")));
		boardVO.setTitle(request.getParameter("title"));
		boardVO.setName(request.getParameter("name"));
		boardVO.setPasswd(request.getParameter("passwd"));
		boardVO.setEmail(request.getParameter("email"));
		boardVO.setContent(request.getParameter("content"));
		boardVO.setIp(request.getRemoteAddr());
		
		BoardDAO dao = BoardDAO.getInstance();
		//비밀번호 인증을 위해 한 건의 레코드를 자바빈에 담아서 반환
		BoardVO db_board = dao.getBoard(boardVO.getNum());
		boolean check = false;
		if(db_board!=null) {
			//비밀번호 일치 여부 체크
			check = db_board.isCheckedPassword(
					                 boardVO.getPasswd());
		}
		if(check) {
			dao.update(boardVO);
			//상세페이지로 이동하기 위해 글번호 저장
			request.setAttribute("num", boardVO.getNum());
		}
		request.setAttribute("check", check);
		//JSP 경로 반환
		return "modify.jsp";
	}
}





