package kr.item.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.FileUtil;

public class AdminDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {
			return "common/accessDenied.jsp";
		}
		
		//관리자로 로그인한 경우
		Long item_num = Long.parseLong(request.getParameter("item_num"));
		//DB에서 상품정보 읽기
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO db_item = dao.getItem(item_num);
		
		//상품 정보 삭제
		dao.deleteItem(item_num);
		
		//삭제된 상품의 이미지 삭제
		FileUtil.removeFile(request, db_item.getPhoto1());
		FileUtil.removeFile(request, db_item.getPhoto2());
		
		request.setAttribute("notice_msg","정상적으로 삭제되었습니다.");
		request.setAttribute("notice_url",request.getContextPath()+"/item/adminList.do");
		
		return "common/alert_view.jsp";
	}

}
