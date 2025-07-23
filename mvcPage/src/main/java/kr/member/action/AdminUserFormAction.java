package kr.member.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class AdminUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = 
				(Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "common/accessDenied.jsp";
		}
		
		//전송된 데이터 반환
		long mem_num = Long.parseLong(
				      request.getParameter("mem_num"));
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(mem_num);
		
		request.setAttribute("member", member);
		
		return "member/detailUserForm.jsp";
	}

}





