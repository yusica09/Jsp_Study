package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardChatVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class ChatWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			mapAjax.put("request", "logout");
		}else {
			BoardChatVO chat = new BoardChatVO();
			chat.setChatroom_num(Long.parseLong(request.getParameter("chatroom_num")));
			
			chat.setMem_num(user_num);
			chat.setMessage(request.getParameter("message"));
			
			BoardDAO dao = BoardDAO.getInstance();
			dao.insertChat(chat);
			
			mapAjax.put("result", "success");
		}
		//JSON에 문자열로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}
