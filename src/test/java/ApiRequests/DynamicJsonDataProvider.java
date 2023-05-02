package ApiRequests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files_data.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJsonDataProvider {

	//parameterize the API test with multiple data tests
	

	
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn1, String aisle1) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").body(Payload.AddBook(isbn1,aisle1))//dynamic json with single input
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		 String id= js.get("ID");

		System.out.println("book id is: " + id);
		
		//delete request code
		//will delete every time the book is created
		given().log().all().header("Content-Type", "application/json").body(Payload.DeleteBookData(id))
//		.body("{\r\n"
//				+ " \r\n"
//				+ "\"ID\" : \""+id+"\"\r\n"			
//				+ " \r\n"
//				+ "} \r\n"
//				+ "")
		.when().delete("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"yyx","443"},{"yyz","445"},{"yyw","554"}};
	}
	
	
	
	
}
