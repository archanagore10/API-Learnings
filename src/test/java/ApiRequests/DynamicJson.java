package ApiRequests;

import org.testng.annotations.Test;

import files_data.Payload;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	
	//dynamically build json payload with external data inputs
	
	@Test
	public void addBook() {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").body(Payload.AddBook("cvfd","549"))//dynamic json with single input
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id = js.get("ID");

		System.out.println("book id is: " + id);
		
		//write deletebook code. it will create and delete book so that you will have fresh isbn and aisle every time you send request
		given().log().all().body(id)
		.when().delete("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);
	}
	
	
	
	
}
