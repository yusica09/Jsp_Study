package kr.member.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ModifyPasswordAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인이 된 경우
		//전송된 데이터 반환
		String id = request.getParameter("id");
		//현재 비밀번호
		String origin_passwd = 
				request.getParameter("origin_passwd");
		//새비밀번호
		String passwd = request.getParameter("passwd");
		
		//현재 로그인 한 아이디
		String user_id = 
				(String)session.getAttribute("user_id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id);
		boolean check = false;
		
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하지 체크
		if(member!=null && id.equals(user_id)) {
			//비밀번호 일치 여부 체크
			check = member.isCheckedPassword(origin_passwd);
		}
		if(check) {//인증 성공
			//비밀번호 변경
			dao.updatePassword(passwd, user_num);
		}
		
		request.setAttribute("check", check);
		
		return "member/modifyPassword.jsp";
	}

}







