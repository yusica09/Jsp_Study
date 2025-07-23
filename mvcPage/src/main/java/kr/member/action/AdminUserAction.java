package kr.member.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.member.dao.MemberDAO;

public class AdminUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않는 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = 
				(Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "common/accessDenied.jsp";
		}
		
		//관리자로 로그인한 경우
		//전송된 데이터 반환
		long mem_num = Long.parseLong(
				          request.getParameter("mem_num"));
		int auth = Integer.parseInt(
				          request.getParameter("auth"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.updateMemberByAdmin(auth, mem_num);
		
		request.setAttribute("notice_msg", 
				                "회원등급이 수정되었습니다.");
		request.setAttribute("notice_url", 
				request.getContextPath() 
				  + "/member/adminUserForm.do?mem_num="+mem_num);
		
		return "common/alert_view.jsp";
	}

}








