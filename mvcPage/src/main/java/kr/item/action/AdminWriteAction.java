package kr.item.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.FileUtil;

public class AdminWriteAction implements Action{

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
		ItemVO item = new ItemVO();
		item.setName(request.getParameter("name"));
		item.setPrice(Integer.parseInt(request.getParameter("price")));
		item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		item.setPhoto1(FileUtil.uploadFile(request, "photo1"));
		item.setPhoto2(FileUtil.uploadFile(request, "photo2"));
		item.setDetail(request.getParameter("detail"));
		item.setStatus(Integer.parseInt(request.getParameter("status")));
		
		ItemDAO dao = ItemDAO.getInstance();
		dao.insertItem(item);
		
		//Refresh 정보를 응답 헤더에 추가
		String url = request.getContextPath() + "/item/adminList.do";
		response.addHeader("Refresh", "2;url=" + url);
		request.setAttribute("result_title", "상품등록 완료");
		request.setAttribute("result_msg", "성공적으로 등록되었습니다.");
		request.setAttribute("result_url", url);
		
		return "common/result_view.jsp";
	}

}
