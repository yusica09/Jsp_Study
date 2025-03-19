package kr.web.ch01;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/now") //호출할 주소 부여
public class NowServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy년MM월dd일 a hh:mm:ss");
		//문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");
		
		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>현재 날짜와 시간</title></head>");
		out.println("<body>");
		out.println("현재 날짜와 시간 : " + sf.format(now));
		out.println("</body>");
		out.println("</html>");
		
		out.close();
	}
	

}
