package com.mywebsite;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		Cookie[] ck = request.getCookies();
		if (ck != null) {
			for(int i = 0; i < ck.length; i++) {
				if (ck[i].getName().equals("userid"))  request.setAttribute("userid", ck[i].getValue());
				else if (ck[i].getName().equals("login")) request.setAttribute("login", ck[i].getValue());	
			}
		}
		request.getRequestDispatcher("/Home.jsp").include(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
