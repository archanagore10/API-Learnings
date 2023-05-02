package ApiRequests;

import org.testng.Assert;

import files_data.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js=new JsonPath(Payload.CoursePrice());	//getting data from payload class method
		
//		1. Print No of courses returned by API
		
		int count=js.getInt("courses.size()");
		System.out.println("no. of corses are: "+count);

//		2.Print Purchase Amount
		int pamount=js.getInt("dashboard.purchaseAmount");
		
		System.out.println("purchase amount is: "+pamount);
		

//		3. Print Title of the first course
		
		//String firsttitle=js.getString("courses[0].title");		//can use any one
		String firsttitle=js.get("courses[0].title");	//get by default returns string
		System.out.println("title of first course: "+firsttitle);

//		4. Print All course titles and their respective Prices
			//get count of the courses. iterate through all the courses to get title 
		
		for(int i=0; i<js.getInt("courses.size()"); i++)	//can use either way
		//for(int i=0; i<count; i++)
			{
				String title=js.get("courses["+i+"].title");		//if we concatenate i then only it treats it as a variable.
																	//otherwise it takes it as a string. because it is in ""
				System.out.println("course title is: "+title);
				System.out.println("price: "+js.get("courses["+i+"].price").toString());		//tostring() is to convert it into string. otherwise it gives error. it needs to know what type of data to print									
				
			}
		

//		5. Print no of copies sold by RPA Course
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$Print no of copies sold by RPA Course***");
		//iterate through all the courses. 
		//get the count of the courses. here using above count
		//get the title first
		//then put it in a condition
		//print when you get expected title
		
		for(int i=0; i<count; i++)
		{
			String title=js.get("courses["+i+"].title");
			if(title.equalsIgnoreCase("RPA"))
			{
				int rpaCopies=js.get("courses["+i+"].copies");
				System.out.println("RPA copies are: "+rpaCopies);
				break;
			}
		}
		
//		6. Verify if Sum of all Course prices matches with Purchase Amount
		//iterate through all the courses. 
		//get the count of the courses. here using above count
		//get all of the courses price and all copies
		// calculate sum of all courses price
		//get purchase amount
		//compare sum of all courses and purchase amount
		
		
		int totalPrice=0;
		for(int i=0;i<count;i++)
		{
			//get all of the courses price and all copies
			int coursesPrice=js.get("courses["+i+"].price");
			int coursesCopies=js.get("courses["+i+"].copies");
			
			// calculate sum of all courses price
			 totalPrice=totalPrice+(coursesPrice*coursesCopies);
		}
		System.out.println("total price: "+totalPrice);
		
		//get purchase amount
		int purAmount=js.get("dashboard.purchaseAmount");
		System.out.println("purchase amount is: "+purAmount);
		
		//compare sum of all courses and purchase amount
		//Assert.assertEquals(totalPrice, purAmount);		//another way of assertion
		
		if(totalPrice==purAmount)
		{
			System.out.println("test passed");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("test failed");
			Assert.assertTrue(false);
		}
	}

}
