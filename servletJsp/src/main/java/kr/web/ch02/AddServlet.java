package kr.web.ch02;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add")
public class AddServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//전송된 데이터 반환
		int num1 = Integer.parseInt(req.getParameter("num1"));
		int num2 = Integer.parseInt(req.getParameter("num2"));
		
		//문서 타입 및 캐릭터셋 지정
		resp.setContentType("text/html;charset=utf-8");
		
		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>덧셈</title></head>");
		out.println("<body>");
		out.printf("%d + %d = %d", num1, num2, num1+num2);
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
	
}
