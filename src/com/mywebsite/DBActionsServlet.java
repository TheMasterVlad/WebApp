package com.mywebsite;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/DBActionsServlet")
public class DBActionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBActionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		Map<Integer, LinkedList<String>> resMap = new LinkedHashMap<>();		
		MySQLAccess dbUsers = new MySQLAccess();
		try {
			dbUsers.setconnection();
			
			if(request.getParameter("showusers") != null) {
				resMap.putAll(dbUsers.getUsers());
			} else {
			resMap.putAll(dbUsers.getUsers());
			}
			dbUsers.close();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("users", resMap);
		request.getRequestDispatcher("/ViewData.jsp").include(request, response);	
		response.getWriter().println("No errors ");
	
		
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String deleteUserId = request.getParameter("deleteuserid");
		String editUserId = request.getParameter("edituserid");
		MySQLAccess dbUsers = new MySQLAccess();
		if (editUserId != null) { //edit user
			Map<String, String> userdata = new LinkedHashMap<>();
			try {
				dbUsers.setconnection();
				userdata.putAll(dbUsers.getUser(Integer.parseInt(editUserId)));
				dbUsers.close();
			} catch (Exception e) {
				response.getWriter().println(e.getMessage());
			}
			request.setAttribute("userdata", userdata);
			request.setAttribute("userid", editUserId);	
			request.getRequestDispatcher("/Edit.jsp").include(request, response);
		}	else if (deleteUserId != null) { //delete user
			try {
				dbUsers.setconnection();
				dbUsers.deleteUser(Integer.parseInt(deleteUserId));
			} catch (Exception e) {
			response.getWriter().println(e.getMessage());
			}
			doPost(request, response);
		} else	doPost(request, response);
	}
}
