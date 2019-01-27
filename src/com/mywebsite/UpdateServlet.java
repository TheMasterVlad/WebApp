package com.mywebsite;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/Update")
public class UpdateServlet extends SignUpServlet {
	private static final long serialVersionUID = 1L;
	private MySQLAccess db = new MySQLAccess();
       
    /**
     * @see SignUpServlet#SignUpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> userdata = new HashMap<String, String>();
		Map<String, String> errors = new HashMap<String, String>(); 
		String login = request.getParameter("login");
		String fname = request.getParameter("fname");
		String mname = request.getParameter("mname");
		String sname = request.getParameter("sname");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass"); 
		String pass2 = request.getParameter("pass2");
		userdata.put("userid", request.getParameter("userid"));
		int userid = Integer.parseInt(request.getParameter("userid"));
		db.setconnection();
		
		if (validateName(fname) == null) {
			userdata.put("fname", fname.trim());
		} else errors.put("fname", validateName(fname)); 
		
		if (validateName(mname) == null) {
			userdata.put("mname", mname.trim());
		} else errors.put("mname", validateName(mname)); 
		
		if (validateName(sname) == null) {
			userdata.put("sname", sname.trim());
		} else errors.put("sname", validateName(sname)); 
		
		String validEmail = checkEmail(email);
		String existingEmail = null;
		try {
			existingEmail = db.emailExists(email);
		} catch (SQLException e1) {
			errors.put("email", e1.getMessage());
		}
		
		if(validEmail == null) {
			if (existingEmail == null || existingEmail.equals(email)) {
				userdata.put("email", email.trim());
			}	else errors.put("email", " This email is already registered!");
		} else {
			errors.put("email", validEmail);
		}
		
		String correctPassword = checkPassword(pass, pass2);
		if (correctPassword != null) {
			errors.put("pass", correctPassword);
		} else {
			userdata.put("pass", pass);
		}
		
		if (errors.isEmpty()) {
			try {
				db.updateUser(userdata, userid);
			} catch (Exception e) {
				e.getMessage();
			}
			request.setAttribute("ok", "User's " + login + " data have been updated.");
			request.getRequestDispatcher("DBActionsServlet").forward(request, response);
		} else {
			db.close();
			request.setAttribute("errors", errors);
			request.setAttribute("userdata", userdata);
			request.getRequestDispatcher("Edit.jsp").forward(request, response);
		}
	}

}
