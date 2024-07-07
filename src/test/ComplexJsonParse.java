package test;

import main.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js=new JsonPath(payload.CoursePrice());
		//Print No of courses returned by API
		
int count=	js.getInt("courses.size()");
System.out.println(count);
//Print Purchase Amount
int totalAmount= js.getInt("dashboard.purchaseAmount");
System.out.println(totalAmount);
//Print Title of the first course


  String titleFirstCourse=js.get("courses[2].title");
  System.out.println(titleFirstCourse);
  
  //Print All course titles and their respective Prices
  
  for(int i=0;i<count;i++)
  {
	  String courseTitles=js.get("courses["+i+"].title");
	  System.out.println(js.get("courses["+i+"].price").toString());
	  System.out.println(courseTitles);
	  
  }
  //Print no of copies sold by RPA Course
  
 System.out.println("Print no of copies sold by RPA Course");
 	for(int i=0; i<count; i++) {
		 String courseTitle = js.get("courses["+i+"].title");
		 if(courseTitle.equalsIgnoreCase("RPA")) {
			 int copies = js.getInt("courses["+i+"].copies");
			 System.out.println("Number of copies sold by RPA course are: " + copies);
			 break;
		 }
	}
	 //sum validation
		int coursesPrice = 0;
	 	int coursesCopies = 0;
		 int amount = 0;
	for(int i=0; i<count; i++) {
		coursesPrice = js.getInt("courses["+i+"].price");
		coursesCopies = js.getInt("courses["+i+"].copies");
		amount = amount + (coursesPrice*coursesCopies);
	}
		System.out.println("Amount is: " + amount);
		Assert.assertEquals(amount,totalAmount);
	}

}
