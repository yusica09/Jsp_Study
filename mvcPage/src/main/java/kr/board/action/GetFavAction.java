package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.board.dao.BoardDAO;
import kr.board.vo.BoardFavVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class GetFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 반환
		long board_num = Long.parseLong(
				         request.getParameter("board_num"));
		
		Map<String,Object> mapAjax = 
				          new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		BoardDAO dao = BoardDAO.getInstance();
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("status", "noFav");
		}else {//로그인이 된 경우
			BoardFavVO boardFav = dao.selectFav(
					         new BoardFavVO(board_num,user_num));
			
			if(boardFav!=null) {
				//로그인한 회원이 좋아요 클릭
				mapAjax.put("status", "yesFav");
			}else {
				//로그인한 회원이 좋아요 미클릭
				mapAjax.put("status", "noFav");
			}
		}
		
		//좋아요 개수
		mapAjax.put("count", dao.selectFavCount(board_num));
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}






