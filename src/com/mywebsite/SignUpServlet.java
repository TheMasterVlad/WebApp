package com.mywebsite;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MySQLAccess db = new MySQLAccess();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> user = new HashMap<String, String>();
		Map<String, String> errors = new HashMap<String, String>(); 
		String login = request.getParameter("login");
		String fname = request.getParameter("fname");
		String mname = request.getParameter("mname");
		String sname = request.getParameter("sname");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass"); 
		String pass2 = request.getParameter("pass2");
		String role = request.getParameter("role");
		String userid = null;
		db.setconnection();
		
		if (validateLogin(login) == null) {
			user.put("login", login.trim());
		} else errors.put("login", validateLogin(login));
		
		if (validateName(fname) == null) {
			user.put("fname", fname.trim());
		} else errors.put("fname", validateName(fname)); 
		
		if (validateName(mname) == null) {
			user.put("mname", mname.trim());
		} else errors.put("mname", validateName(mname)); 
		
		if (validateName(sname) == null) {
			user.put("sname", sname.trim());
		} else errors.put("sname", validateName(sname)); 
		
		String validEmail = checkEmail(email);
		if(validEmail == null) {
			try {
				if (db.emailExists(email) != null) {
					errors.put("email", " This email is already registered!");
				}	else {
					user.put("email", email.trim());
				}
			} catch (SQLException e) {
				errors.put("email", e.getMessage());
			}
		} else {
			errors.put("email", validEmail);
		}
		
		String correctPassword = checkPassword(pass, pass2);
		if (correctPassword != null) {
			errors.put("pass", correctPassword);
		} else {
			user.put("pass", pass);
		}
		
		user.put("role", role);
		
		if (errors.isEmpty()) {
			try {
				db.insert(user);
				userid = db.userId(login, pass);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			Cookie idck = new Cookie("userid", userid);
			Cookie loginck = new Cookie("login", login);
			idck.setMaxAge(300000);
			loginck.setMaxAge(300000);
			response.addCookie(idck);
			response.addCookie(loginck);
			response.sendRedirect("Home");
		} else {
			db.close();
			request.setAttribute("errors", errors);
			request.setAttribute("user", user);
			request.getRequestDispatcher("SignUp.jsp").forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	protected String validateName(String name) {
		if (name.length() > 45 || !name.chars().allMatch(x -> Character.isAlphabetic(x))) 
		{
			return "  Incorrect data for a name...";
		} else 
			return null;
	}
	
	protected String validateLogin(String login) {
		if (login.isEmpty()) {
			return "  Please fill the login field...";
		} else if (login.length() > 45 || !login.chars().allMatch(x -> Character.isLetterOrDigit(x))) 
		{
			return "  Incorrect data for a name...";
		} else 		
			try {
				if (db.checkUser(login).isEmpty()) {
					return null;
				}	else return " This username is already exists!";
			} catch (SQLException e1) {
				return e1.getMessage();
			}
	}
	
	protected String checkEmail(String email) {
		if(email.isEmpty()) {
			return " Please fill the email field..."; 
		} else if (email.length() > 25) {
			return " Email is too big..."; 
		} else if (email.chars().noneMatch(x -> Character.isLetterOrDigit(x)) || !email.contains("@") || !email.contains(".") || email.startsWith("@") || email.startsWith(".")) {
			return " Incorrect data...";
		} else return null;
	}
	
	protected String checkPassword(String pass1, String pass2) {
		String error = null;
		if(pass1 == null || pass2 == null) {
			error = " Please fill the password field...";
		} else if (pass1.length() > 45 || pass2.length() > 45) {
			error = " Password is too big...";
		} else if(!pass1.equals(pass2)) {
			error = " Passwords doesn't match!"; 
		}
		return error;
	}
}
