package kr.member.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.PagingUtil;

public class AdminUserListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = 
				(Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "common/accessDenied.jsp";
		}
		
		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		MemberDAO dao = MemberDAO.getInstance();
		int count = 
				dao.getMemberCoutByAdmin(keyfield, keyword);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,
				           Integer.parseInt(pageNum),count,
				                  20,10,"adminList.do");
		
		List<MemberVO> list = null;
		if(count > 0) {
			list = dao.getListMemberByAdmin(page.getStartRow(),
					                        page.getEndRow(),
					                        keyfield,keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "member/memberList.jsp";
	}

}





