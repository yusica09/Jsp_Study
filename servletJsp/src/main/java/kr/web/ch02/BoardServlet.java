package kr.web.ch02;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board")
public class BoardServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/*
		 * [실습] 게시판에 글쓰기
		 * [출력 예시]
		 * 이름 : 홍길동
		 * 제목 : 봄
		 * 내용 : 봄이와요 봄봄봄!! 
		 */
		
		//전송된 데이터 반환
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String content = req.getParameter("content");

		//문서 타입 및 캐릭터셋 지정
		resp.setContentType("text/html;charset=utf-8");

		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>게시판에 글쓰기</title></head>");
		out.println("<body>");
		out.println("이름 : " + name);
		out.println("<br>제목 : " + title);
		out.println("<br>내용 : " + content);
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}
