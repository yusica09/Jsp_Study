package kr.board.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		long board_num = Long.parseLong(
				           request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = dao.getBoard(board_num);
		if(user_num != board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "common/accessDenied.jsp";
		}
		
		//큰 따옴표 처리(수정폼의 input태그에만 명시)
		board.setTitle(StringUtil.parseQuot(board.getTitle()));
		
		request.setAttribute("board", board);
		
		return "board/updateForm.jsp";
	}

}







