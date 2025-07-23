package kr.board.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardChatVO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ChatRoomListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		long board_num = Long.parseLong(request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO vo = dao.getBoard(board_num);
		
		int count = dao.getChatCount(keyfield, keyword, board_num);
		
		//페이지처리
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum),count, 20,10,
																			"list.do", "&board_num="+board_num);
		List<BoardChatVO> list = null;
		if(count > 0) {
			list = dao.getChatList(page.getStartRow(),
										   page.getEndRow(),
										   keyfield, keyword, 
										   board_num, user_num);
		}
		request.setAttribute("board",vo);
		request.setAttribute("count",count);
		request.setAttribute("list",list);
		request.setAttribute("page",page.getPage());
		//JSP경로 반환
		return "board/chat_list.jsp";
	}

}
