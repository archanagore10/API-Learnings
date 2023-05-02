package ApiRequests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

public class StaticJsonFiles {

	
	//How to send static json files(payload) directly into post method of rest assured
	//convert payload which is in json file into String.--> content of file can convert into Byte--> Byte data to string
	
	@Test
	public void addPlace() throws IOException
	{
	RestAssured.baseURI="https://rahulshettyacademy.com";
	
	String response=given().log().all().queryParam("key", "qaclick123")
	.header("Content-Type","application/json")
	//files is a static package in java. readAllBytes reads and converts the content of the file from the path which you have provided
	//paths is another class which accepts path of json file
	
	.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\13313\\Desktop\\Testing Material\\Api docs\\postData.json"))))
	
	//Files.readAllBytes takes data in bytes format
	//to get data in string format we need new String
	
	.when().post("/maps/api/place/add/json")
	.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	System.out.println("response is: "+response);
	JsonPath js=new JsonPath(response);
	String place_id=js.get("place_id");
	
	System.out.println("place_id is :"+place_id);
	}
}
