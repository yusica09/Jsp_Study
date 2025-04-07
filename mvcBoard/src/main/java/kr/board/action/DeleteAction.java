package kr.board.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long num = Long.parseLong(request.getParameter("num"));
		String passwd = request.getParameter("passwd");
		
		BoardDAO dao = BoardDAO.getInstance();
		//비밀번호 인증을 위해 BoardVO를 반환
		BoardVO db_board = dao.getBoard(num);
		boolean check = false;
		if(db_board!=null){
			//비밀번호 일치 여부 체크
			check = db_board.isCheckedPassword(passwd);
		}
		if(check) {
			dao.delete(num);
		}
		
		request.setAttribute("check", check);
		//JSP 경로반환
		return "delete.jsp";
	}

}
