package com.mywebsite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MySQLAccess {
	
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null; 
  final private String host = "localhost:3306";
  final private String user = "admin";
  final private String passwd = "admin";
  final private String database = "mydb";

  private String connectString = "jdbc:mysql://" + host + "/" + database
          + "?" + "user=" + user + "&password=" + passwd + "&serverTimezone=UTC";

 public void setconnection(){
	 try { 
		 	Class.forName("com.mysql.jdbc.Driver");
		    connect = DriverManager.getConnection(connectString);
		    statement = connect.createStatement();
		    System.out.println("Connected!");
		
		} catch (SQLException ex) {
		    // handle any errors
			System.out.println("An error occurred. Maybe user/password is invalid");
 			ex.printStackTrace();
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	 
 }
 
  public void insert(Map<String, String> user) throws Exception{
	  java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	try {
		String insertQuary = "INSERT INTO mydb.users (IdUser, First_name,"
				+ " Middle_name, Surname, Login, Password, Email, Registration_data, Role) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		preparedStatement = connect.prepareStatement(insertQuary);
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, user.get("fname"));
		preparedStatement.setString(3, user.get("mname")); 
		preparedStatement.setString(4, user.get("sname"));
		preparedStatement.setString(5, user.get("login"));
		preparedStatement.setString(6, user.get("pass")); 
		preparedStatement.setString(7, user.get("email"));
		preparedStatement.setDate(8, currentDate);
		preparedStatement.setString(9, user.get("role"));
		preparedStatement.executeUpdate();
	}  catch (Exception e) {
		throw e;
	} finally {
		close();
	}
	
  }
  
  public void updateUser(Map<String, String> user, int userid) throws Exception{
	  try {
		  String updateQuary = "UPDATE users SET First_name = '" + user.get("fname") + "' , " + 
				  "Middle_name = '" + user.get("mname") + "' , " + "Surname = '" + user.get("sname") + "' , " +
				  "Password = '" + user.get("pass") + "' , " + "Email = '" + 
				  user.get("email") + "' " + " WHERE idUser = '" + userid + "'";
		  preparedStatement = connect.prepareStatement(updateQuary);
		  preparedStatement.executeUpdate();
	  }  catch (Exception e) {
		  throw e;
	  } finally {
		  close();
	  }
  }
  
  public void deleteUser(int userid) throws Exception{
	  try {
		  statement.executeUpdate("DELETE FROM mydb.users WHERE idUser = '" + userid + "'");
	  }  catch (Exception e) {
		  throw e;
	  } finally {
		  close();
	  }
	  
  }
/*
  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }*/
  
  public Map<Integer, LinkedList<String>> getUsers() throws SQLException {
      resultSet = statement.executeQuery("select * from mydb.users");
      return getResult(resultSet);
  }

  public Map<Integer, LinkedList<String>> checkUser(String login) throws SQLException {
    resultSet = statement.executeQuery("SELECT First_name, Middle_name, Surname, Email, Registration_data, Role  FROM mydb.users WHERE `Login` ='" + login + "'");
    return getResult(resultSet);	
}
  
  public String userId(String loginoremail, String password) throws SQLException {
	  resultSet = statement.executeQuery("SELECT idUser  FROM mydb.users WHERE (`Login` ='" + loginoremail + "' OR `Email` ='" + loginoremail + "') AND Password = '" + password + "'");
	  if (resultSet.first()) {
		  return resultSet.getString("idUser");
	  } else return null;
	  
  }
  
  public Map<String, String> getUser(int userid) throws SQLException {
	  Map<String, String> resMap = new LinkedHashMap<>();
	  resultSet = statement.executeQuery("SELECT Login, First_name, Middle_name, Surname, Password, Email  FROM mydb.users WHERE `idUser` ='" + userid + "'");
	  while (resultSet.next()) {
		  resMap.put("login", resultSet.getString("Login")); 
		  resMap.put("fname", resultSet.getString("First_name")); 
		  resMap.put("mname", resultSet.getString("Middle_name")); 
		  resMap.put("sname", resultSet.getString("Surname")); 
		  resMap.put("pass", resultSet.getString("Password")); 
		  resMap.put("email", resultSet.getString("Email")); 
	  }
	  return resMap;	
  }
  
  public String emailExists(String email) throws SQLException {
	  resultSet = statement.executeQuery("SELECT `Email`  FROM mydb.users WHERE `Email` ='" + email + "'");
	  if (resultSet.first()) {
		  return resultSet.getString("Email");
	  } else return null;
  }
  
  private Map<Integer, LinkedList<String>> getResult (ResultSet set) throws SQLException {
	  Map<Integer, LinkedList<String>> resMap = new LinkedHashMap<>();
	  try {
		  while (set.next()) {
			  resMap.put(Integer.parseInt(set.getString("idUser")), new LinkedList<String> (Arrays.asList(
					  set.getString("Login"), 
					  set.getString("First_name"), 
					  set.getString("Middle_name"), 
					  set.getString("Surname"), 
					  set.getString("Email"), 
					  set.getDate("Registration_data").toString(),
					  set.getString("Role"))));
		  }
	  } catch (SQLException ex) {
		  ex.printStackTrace();
	  }
	  return resMap;		
  }
  
  //  close the resultSet
  public void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

}