package kr.web.ch03;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/lifeCycle")
public class LifeCycle extends HttpServlet{
	int initCnt = 0;
	int serviceCnt = 0;
	int destroyCnt = 0;
	
	@Override
	public void init() throws ServletException {
		System.out.println("init 메서드는 첫 요청만 호출됨 : " + (++initCnt));
	}

	@Override            
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		System.out.println("service 메서드가 요청할 때마다 호출됨 : " + (++serviceCnt));
		
		//문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");
		//HTML 출력을 위한 출력스트림 생성
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Servlet Life Cycle</title></head>");
		out.println("<body>");
		out.println("service 호출 개수 : " + serviceCnt);
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public void destroy() {
		System.out.println("destroy 메서드는 본 Servlet이 "
				+ "더 이상 사용되지 않을 때만 호출됨 : " + (++destroyCnt));
	}
	
	
}
