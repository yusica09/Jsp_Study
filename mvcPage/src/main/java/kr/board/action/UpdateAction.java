package kr.board.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인이 된 경우
		long board_num = Long.parseLong(
				         request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		//수정전 데이터 
		BoardVO db_board = dao.getBoard(board_num);
		if(user_num != db_board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "common/accessDenied.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		BoardVO board = new BoardVO();
		board.setBoard_num(board_num);
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		board.setIp(request.getRemoteAddr());
		board.setFilename(
				FileUtil.uploadFile(request, "filename"));
		
		dao.updateBoard(board);
		
		//새파일로 교체할 때 원래 파일 제거
		if(board.getFilename()!=null 
				    && !"".equals(board.getFilename())) {
			FileUtil.removeFile(request, db_board.getFilename());
		}
		
		return "redirect:/board/detail.do?board_num="+board_num;
	}

}







