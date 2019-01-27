package com.mywebsite;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/LogIn")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("logout")) {
			Cookie idck = new Cookie("userid", "");
			Cookie loginck = new Cookie("login", "");
			idck.setMaxAge(0);
			loginck.setMaxAge(0);
			response.addCookie(idck);
			response.addCookie(loginck);
		}
		response.sendRedirect("Home");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String loginoremail = request.getParameter("loginoremail");
		String password = request.getParameter("pass");
		Map<String, String> errors = new LinkedHashMap<>();
		MySQLAccess dbUsers = new MySQLAccess();
		String userid = null;
		String login = null;
		try {
			dbUsers.setconnection();
			if(loginoremail != null) {
				if(password != null) {
					userid = dbUsers.userId(loginoremail, password);
				} else {
					errors.put("pass", "Please enter the password");
				}
			} else {
				errors.put("loginoremail", "Please enter login or email");
			}
			if(userid != null) {
				login = dbUsers.getUser(Integer.parseInt(userid)).get("login");
				Cookie idck = new Cookie("userid", userid);
				Cookie loginck = new Cookie("login", login);
				idck.setMaxAge(300000);
				loginck.setMaxAge(300000);
				response.addCookie(idck);
				response.addCookie(loginck);
			} else errors.put("pass", "Wrong data! Try again.");
			dbUsers.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("errors", errors);
		if(errors.isEmpty()) {
			response.sendRedirect("Home");	
		} else request.getRequestDispatcher("/Home.jsp").include(request, response);
	}

}
