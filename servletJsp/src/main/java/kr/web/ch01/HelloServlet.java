package kr.web.ch01;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//서블릿에서 사용 주소 명시
@WebServlet("/hello")
public class HelloServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");
		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Hello Servlet</title></head>");
		out.println("<body>");
		out.println("처음 작성하는 servlet입니다.");
		out.println("</body>");
		out.println("</html>");
		
		out.close();
		
	}
	
}
