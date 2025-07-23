package kr.member.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ModifyUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				  (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MemberVO member = new MemberVO();
		member.setMem_num(user_num);//회원번호
		member.setName(request.getParameter("name"));
		member.setPhone(request.getParameter("phone"));
		member.setZipcode(request.getParameter("zipcode"));
		member.setAddress1(request.getParameter("address1"));	
		member.setAddress2(request.getParameter("address2"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.updateMember(member);
		
		request.setAttribute("notice_msg", "회원정보 수정 완료!");
		request.setAttribute("notice_url", 
				  request.getContextPath()+"/member/myPage.do");
		//JSP 경로 반환
		return "common/alert_view.jsp";
	}
}







