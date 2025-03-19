package kr.web.ch01;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hundred")
public class HundredServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * [실습] 1부터 100까지의 합 출력 - 반복문 활용
		 * 출력예시
		 * 
		 * 1부터 100까지의 합은 5050
		 * 
		 */
		int sum = 0;
		for (int i = 1; i <= 100; i++) {
			sum += i;
		}
		
		//문서 타입 및 캐릭터셋 지정
		resp.setContentType("text/html;charset=utf-8");
		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>1부터 100까지의 합 구하기</title></head>");
		out.println("<body>");
		out.println("1부터 100까지의 합은 "+ sum);
		out.println("</body>");
		out.println("</html>");

		out.close();
	}


}
