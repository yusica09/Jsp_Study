package kr.web.ch02;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/travel")
public class TravelServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * [실습] 여행지 선택
		 * [출력예시]
		 * 이름 : 홍길동
		 * 여행지 : 서울, 파리 <-- 여행지를 선택한 경우
		 * 여행지 : 집 <-- 여행지를 선택하지 않은 경우 
		 */
		
		//문서 타입 및 캐릭터셋 지정
		resp.setContentType("text/html;charset=utf-8");

		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>여행지 선택</title></head>");
		out.println("<body>");
		
		out.println("<h3>여행지 선택</h3>");
		String name = req.getParameter("name");
		out.println("이름 : " + name + "<br>");

		//전송된 데이터를 String 배열로 반환
		String[] values = req.getParameterValues("city");
		out.println("여행지 : ");
		
		if(values != null) {
			for (int i = 0; i < values.length; i++) {
				out.println(i==0?values[i]:", "+values[i]);
			} //for i
		}else {
			out.println("집");
		}

		out.println("</body>");
		out.println("</html>");
		out.close();

	}

}
