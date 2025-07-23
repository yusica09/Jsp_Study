package kr.web.ch04;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 50
		)
@WebServlet("/profile")
public class UploadServlet3 extends HttpServlet{
	@Override
	public void doPost(HttpServletRequest request,
			           HttpServletResponse response)
	                  throws ServletException,IOException{
		//컨텍스트 경로상의 파일 업로드 절대 경로 구하기
		String realFolder = request.getServletContext()
				                   .getRealPath("/upload");
		//문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");
		//HTML 출력을 위한 출력스트림 생성
		PrintWriter out = response.getWriter();
		try {
			Part part = request.getPart("file");
			String fileName = part.getSubmittedFileName();
			if(!fileName.isEmpty()) {
				//지정된 경로에 파일을 저장
				part.write(realFolder+"/"+fileName);
				
				//파일명을 request에 저장
				request.setAttribute("fileName", fileName);
				
				//JSP 포워드
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher(
						 "/ch08-fileupload/s04_profile.jsp");
				dispatcher.forward(request, response);
			}else {
				out.println("업로드한 파일이 없습니다.");
			}
		}catch(IllegalStateException e) {
			out.println("용량 초과 : " + e.toString());
		}
	}
}