package kr.item.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.StringUtil;

public class UserDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 상품번호 반환
		long item_num = Long.parseLong(request.getParameter("item_num"));
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO item = dao.getItem(item_num);
		
		//상품 설명 줄바꿈처리(HTML 태그 허용)
		item.setDetail(StringUtil.useBrHtml(item.getDetail()));
		
		request.setAttribute("item", item);
		return "item/user_detail.jsp";
	}

}
