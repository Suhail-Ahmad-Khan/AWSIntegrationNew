package com.bridgelabz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Model, String> {

	@Override
	public String handleRequest(Model input, Context context) {
		context.getLogger().log("Input: " + input);

		if (insert(input))
			return "Data Inserted";
		else if (delete(input))
			return "Data Deleted";
		else
			return "Process Terminated";
	}

	public boolean insert(Model input) {
		int flag = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://rdstrialtest.cmgtmjfill7k.us-west-2.rds.amazonaws.com/AddingIntegers", "rdstrialtest",
					"rdstrialtest");

			int uniqueId = input.getUniqueId();
			int number1 = input.getNumber1();
			int number2 = input.getNumber2();
			int result = number1 + number2;

			String query = " insert into myDatabase (uniqueId, number1, number2, result)" + " values (?, ?, ?, ?)";
			Class.forName("com.mysql.jdbc.Driver");

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setInt(1, uniqueId);
			preparedStmt.setInt(2, number1);
			preparedStmt.setInt(3, number2);
			preparedStmt.setInt(4, result);
			preparedStmt.execute();
			System.out.println("Data inserted");
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (flag == 1)
			return true;
		else
			return false;
	}

	public boolean delete(Model input) {
		int flag = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://rdstrialtest.cmgtmjfill7k.us-west-2.rds.amazonaws.com/AddingIntegers", "rdstrialtest",
					"rdstrialtest");

			int uniqueId = input.getUniqueId();

			String query = "delete from myDatabase where uniqueId = ?";
			Class.forName("com.mysql.jdbc.Driver");

			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setInt(1, uniqueId);
			preparedStmt.execute();
			System.out.println("Data deleted");
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (flag == 1)
			return true;
		else
			return false;
	}
}
