package kr.web.ch02;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 *  데이터 전송 방식 :   get -> doGet 메서드 구현
 *  					 	 post -> doPost 메서드 구현
 * */
@WebServlet("/greeting")
public class GreetingServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//문서 타입 및 캐릭터셋 지정
		resp.setContentType("text/html;charset=utf-8");
		
		//전송된 데이터 반환
		String name = req.getParameter("name");

		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>Greeting</title></head>");
		out.println("<body>");
		out.println(name + "님의 방문을 환영합니다.");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}
