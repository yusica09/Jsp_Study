package kr.member.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class MyPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		//회원정보
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(user_num);
		
		//내가 쓴 게시물 목록(채팅)
		BoardDAO boardDAO = BoardDAO.getInstance();
		List<BoardVO> myBoardList = boardDAO.getListBoardByUser(1, 5, null, null, user_num);
		
		//내가 채팅한 게시글 목록(채팅)
		List<BoardVO> myChatList = boardDAO.getListBoardForMyChat(1, 5, null, null, user_num);
		
		//내가 좋아하는 게시물 정보
		List<BoardVO> boardList = 
				boardDAO.getListBoardFav(1, 5, user_num);
		
		//구매 상품 정보
		OrderDAO orderDAO = OrderDAO.getInstance();
		List<OrderVO> orderList = orderDAO.getListOrderByMem_num(1, 5, null, null, user_num);
		
		request.setAttribute("member", member);
		request.setAttribute("myBoardList", myBoardList);
		request.setAttribute("myChatList", myChatList);
		request.setAttribute("boardList", boardList);
		request.setAttribute("orderList", orderList);
		
		//JSP 경로 반환
		return "member/myPage.jsp";
	}

}






