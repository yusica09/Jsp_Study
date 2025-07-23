package kr.main.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ItemDAO itemDAO = ItemDAO.getInstance();
		List<ItemVO> itemList = itemDAO.getListItem(1, 8, null, null, 1);
		
		request.setAttribute("itemList", itemList);
		
		//JSP 경로 반환
		return "main/main.jsp";
	}

}




