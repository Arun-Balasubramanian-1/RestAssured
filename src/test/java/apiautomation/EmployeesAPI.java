package apiautomation;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EmployeesAPI {
	static int empId = 5506;

	@Test
	public void Test_1_getAllEmployee() {

		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/employees";
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String responseBody = response.body().asString();
		System.out.println(responseBody);
		System.out.println("Response code is:" + response.statusCode());
		AssertJUnit.assertEquals(response.statusCode(), 200);

	}
	
	@Test
	public void Test_2_createEmployee() throws IOException {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/create";
		RequestSpecification request = RestAssured.given().contentType("application/json");
		File requestBodyFile = new File("./src/test/java/apiautomation/employee.json");
		String requestBody = new String(Files.readAllBytes(requestBodyFile.toPath()));
		Response response = request.body(requestBody).post();
		String responseBody = response.body().asString();
		System.out.println(responseBody);
		System.out.println("Response code is:" + response.statusCode());
		AssertJUnit.assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();
		String empName = json.getString("data.name");
		empId = json.getInt("data.id");
		System.out.println("Created Employee ID" + empId);
		AssertJUnit.assertEquals(empName, "Arun");
		
	}

	@Test
	public void Test_3_getOneEmployee() {
		System.out.println("Employee ID" + empId);
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/employee/" + empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String responseBody = response.body().asString();
		System.out.println(responseBody);
		System.out.println("Response code is:" + response.statusCode());
		AssertJUnit.assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();
		String status = json.getString("status");
		AssertJUnit.assertEquals(status, "success");

	}
	

	@Test
	public void Test_4_deleteEmployee() {
		System.out.println("Employee ID" + empId);
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/delete/" + empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete();
		String responseBody = response.body().asString();
		System.out.println(responseBody);
		System.out.println("Response code is:" + response.statusCode());
		AssertJUnit.assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();
		String status = json.getString("status");
		AssertJUnit.assertEquals(status, "success");

	}
	
	@Test
	public void Test_5_deleteDeletedEmployee() {
		System.out.println("Employee ID" + empId);
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/employee/" + empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String responseBody = response.body().asString();
		System.out.println(responseBody);
		System.out.println("Response code is:" + response.statusCode());
		AssertJUnit.assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();
		String status = json.getString("status");
		AssertJUnit.assertEquals(status, "success");

	}
}
