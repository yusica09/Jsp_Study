package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.util.StringUtil;

public class CheckUniqueInfoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		
		MemberDAO dao = MemberDAO.getInstance();
		boolean flag = dao.checkUniqueInfo(id, email);
		
		Map<String,String> mapAjax = 
				             new HashMap<String,String>();
		if(flag) {//중복
			if(id!=null && email==null) {//아이디 체크
				mapAjax.put("result", "idDuplicated");
			}else if(id==null && email!=null) {//email 체크
				mapAjax.put("result", "emailDuplicated");
			}
		}else {//미중복
			if(id!=null && email==null) {//아이디 체크
				mapAjax.put("result", "idNotFound");
			}else if(id==null && email!=null) {//email 체크
				mapAjax.put("result", "emailNotFound");
			}
		}
		
		/*
		 * JSON 형식으로 변환하기를 원하는 문자열을 HashMap에 
		 * key와 value의 쌍으로 저장한 후 ObjectMapper의
		 * writeValueAsString()에 Map객체를 전달해서 일반 문자열
		 * 데이터를 JSON 형식의 문자열 데이터로 변환 후 반환
		 */
		
		//JSON 문자열로 변환하고 JSON 문자열을 클라이언트로 전송할
		//JSP 반환
		return StringUtil.parseJSON(request, mapAjax);
	}

}






