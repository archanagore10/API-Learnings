package ApiRequests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files_data.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class PostRequest_InputFromAnotherclass {

	public static void main(String[] args) 
	{
		RestAssured.baseURI="https://rahulshettyacademy.com"; 
		
		//response variable is to store response
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")	//header is input
		.body(Payload.postData())		//getting payload from payload class
		.when()
		.post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))		//response body
		.header("Server","Apache/2.4.41 (Ubuntu)")			//response body header section
		.extract().response().asString();
		
		System.out.println("response is: "+response);
		
		//below saving printing place_id from response
		JsonPath js=new JsonPath(response);
		String placeID=js.getString("place_id");
		
		System.out.println("place id is: "+placeID);
		
		
		//update request
		String newAddress="70 riverwalk, USA";
		
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"place_id\":\""+placeID+"\",\r\n"
				//+ "    \"address\":\"70 winter walk, USA\",\r\n"	
				+ "    \"address\":\""+newAddress+"\",\r\n"		//instead of hard coding taking address from variable
				+ "    \"key\":\"qaclick123\"\r\n"
				+ "\r\n"
				+ "}")
		
		.when()
		.put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get request
		
		String getresponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeID)
		.when()
		.put("/maps/api/place/get/json")
		.then().log().all().assertThat()	//body("address", equalTo(newAddress))
		//another way of assertion
		.extract().response().asString();
		
		System.out.println("get request response: "+getresponse);
		
		JsonPath js1=new JsonPath(getresponse);
		String actualAddr=js1.getString("address");
		
		System.out.println("actual address: "+actualAddr);
		Assert.assertEquals(actualAddr, newAddress);
		
		
		//delete request
		//write deletebook code.  you will have fresh payload every time you send request
		given().log().all().queryParam("key", "qaclick123").body(placeID)
		.when().delete("/maps/api/place/delete/json")
		.then().log().all().assertThat().statusCode(200);
		
	}
}