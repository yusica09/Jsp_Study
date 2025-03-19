package kr.web.ch02;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/gugudan")
public class GugudanServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//문서 타입 및 캐릭터셋 지정
		resp.setContentType("text/html;charset=utf-8");
		
		//전송된 데이터 반환 - getParameter 반환타입 : String
		int dan = Integer.parseInt(req.getParameter("dan"));
		
		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>구구단</title></head>");
		out.println("<body>");
		out.println(dan + "단<br>");
		out.println("---------------------<br>");
		
		for (int i = 1; i <= 9; i++) {
			out.println(dan+"*"+i+"="+(dan*i) +"<br>");
		} //for i
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

}
