package kr.web.ch02;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/score")
public class ScoreServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 *[실습] 성적처리
		 *[출력예시]
		 *국어 : 00
		 *영어 : 00
		 *수학 : 00
		 *총점 : 000
		 *평균 : 00
		 *등급 : A 
		 */

		//문서 타입 및 캐릭터셋 지정
		resp.setContentType("text/html;charset=utf-8");

		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>성적 처리</title></head>");
		out.println("<body>");

		int kor = Integer.parseInt(req.getParameter("korean"));
		int eng = Integer.parseInt(req.getParameter("english"));
		int mat = Integer.parseInt(req.getParameter("math"));

		int tot = kor+eng+mat;
		double avg = (double)tot/3;
		char grade;

		switch ((int)avg/10) {
		case 10:case 9:
			grade = 'A';
			break;
		case 8:
			grade = 'B';
			break;
		case 7:
			grade = 'C';
			break;
		case 6:
			grade = 'D';
			break;
		default:
			grade = 'F';
			break;
		}

		out.println("국어 : " + kor + "<br>");
		out.println("영어 : " + eng + "<br>");
		out.println("수학 : " + mat + "<br><br>");
		out.println("총점 : " + tot + "<br>");
		out.printf("평균 : %.3f <br>",avg);
		out.println("등급 : " + grade + "<br>");

		out.println("</body>");
		out.println("</html>");
		out.close();
	}

}
