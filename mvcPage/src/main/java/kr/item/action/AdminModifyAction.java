package kr.item.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.FileUtil;

public class AdminModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//관리자 여부 체크
		Integer user_auth =
				(Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {
			return "common/accessDenied.jsp";
		}
		//관리자로 로그인한 경우
		long item_num = Long.parseLong(request.getParameter("item_num"));
		//DB에 저장된 정보 읽기
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO db_item = dao.getItem(item_num);
		
		//전송된 정보 저장
		ItemVO item = new ItemVO();
		item.setItem_num(item_num);
		item.setName(request.getParameter("name"));
		item.setPrice(Integer.parseInt(request.getParameter("price")));
		item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		item.setDetail(request.getParameter("detail"));
		item.setPhoto1(FileUtil.uploadFile(request, "photo1"));
		item.setPhoto2(FileUtil.uploadFile(request, "photo2"));
		item.setStatus(Integer.parseInt(request.getParameter("status")));
		
		//상품정보수정
		dao.updateItem(item);
		
		//상품사진이 수정된 경우 이전 파일을 삭제
		if(item.getPhoto1() != null && !"".equals(item.getPhoto1())) {
			FileUtil.removeFile(request, db_item.getPhoto1());
		}
		if(item.getPhoto2() != null && !"".equals(item.getPhoto2())) {
			FileUtil.removeFile(request, db_item.getPhoto2());
		}
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+
											"/item/adminModifyForm.do?item_num="+item_num);
		
		return "common/alert_view.jsp";
	}

}
