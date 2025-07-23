package kr.board.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardChatRoomVO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class ChatMessageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		String chatroom_num = request.getParameter("chatroom_num");
		BoardDAO dao = BoardDAO.getInstance();
		BoardChatRoomVO room;
		if(chatroom_num == null) { //채팅방 번호가 없는 경우
			long board_num = Long.parseLong(request.getParameter("board_num"));
			BoardVO board = dao.getBoard(board_num);
			room = dao.checkChatRoom(board_num, user_num);
			
			if(room == null) { //개설된 채팅방이 없는 경우
				BoardChatRoomVO vo = new BoardChatRoomVO();
				vo.setBoard_num(board_num);
				vo.setWriter_num(board.getMem_num());
				vo.setReader_num(user_num);
				//채팅방 생성
				dao.insertChatRoom(vo);
				//채팅방 생성 여부를 확인하고 생성된 채팅방 정보 반환
				room = dao.checkChatRoom(board_num, user_num);
	
			}
			
		}else { //채팅방 번호가 있는 경우
			room = dao.selectChatRoom(Long.parseLong(chatroom_num));
		}
		
		//제목 HTML 태그 불허
		room.setTitle(StringUtil.useNoHtml(room.getTitle()));
		
		request.setAttribute("boardChatRoomVO", room);
		
		return "board/chat_detail.jsp";
	}

}
