package ApiRequests;

import static io.restassured.RestAssured.given;

import files_data.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DeleteRequest {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://216.10.245.166";
			given().log().all().header("Content-Type", "application/json").body(Payload.DeleteBookData("xxx334"))
				.when().delete("/Library/DeleteBook.php")
				.then().log().all().assertThat().statusCode(200);
				
	}

}
