package kr.board.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ChatMyListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		BoardDAO dao = BoardDAO.getInstance();
		int count = dao.getBoardCountForMyChat(keyfield, keyword, user_num);
		
		//페이지처리
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum),
															count,20,10,"list.do");
		
		List<BoardVO> list = null;
		if(count > 0) {
			list = dao.getListBoardForMyChat(page.getStartRow(), page.getEndRow(),
																	keyfield, keyword, user_num);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "board/myChatList.jsp";
	}

}
