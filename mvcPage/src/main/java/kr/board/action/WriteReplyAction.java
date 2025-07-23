package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardReplyVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class WriteReplyAction  implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {
			BoardReplyVO reply = new BoardReplyVO();
			reply.setMem_num(user_num); //댓글 작성자 회원번호
			reply.setRe_content(request.getParameter("re_content"));
			reply.setRe_ip(request.getRemoteAddr());
			//댓글의 부모 글번호
			reply.setBoard_num(Long.parseLong(request.getParameter("board_num")));
			
			BoardDAO dao = BoardDAO.getInstance();
			dao.insertReplyBoard(reply);
			
			mapAjax.put("result", "success");
		}
		//JSON 문자열로 반환
		return StringUtil.parseJSON(request, mapAjax);
	}

}
