package kr.member.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class RegisterUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//자바빈(VO) 생성
		MemberVO member = new MemberVO();
		member.setId(request.getParameter("id"));
		member.setName(request.getParameter("name"));
		member.setPasswd(request.getParameter("passwd"));
		member.setPhone(request.getParameter("phone"));
		member.setEmail(request.getParameter("email"));
		member.setZipcode(request.getParameter("zipcode"));
		member.setAddress1(request.getParameter("address1"));
		member.setAddress2(request.getParameter("address2"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.insertMember(member);
		
		request.setAttribute("result_title", "회원가입 완료");
		request.setAttribute("result_msg", 
				                   "회원가입이 완료되었습니다.");
		request.setAttribute("result_url", 
				request.getContextPath()+"/main/main.do");
		
		//JSP 경로 반환
		return "common/result_view.jsp";
	}

}






