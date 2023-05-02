package ApiRequests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files_data.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJsonFilesBooks {

	
	@Test
	public void addBook() throws IOException
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(getPath("C:\\Users\\13313\\Desktop\\Testing Material\\Api docs\\addBook.json"))
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id = js.get("ID");

		System.out.println("book id is: " + id);
	}
	
	public static String getPath(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
