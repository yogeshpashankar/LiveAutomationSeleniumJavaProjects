package tempExperiments;

import java.util.Date;

public class GenerateEmailDemo {

	public static void main(String[] args) {
		
		Date date = new Date();
		System.out.println(date);
		
		String dateString = date.toString();// To convert date into string because date is in class format
		String noSpaceStringDate = dateString.replaceAll("\\s", "");//Removing spaces 
		
		String npSpaceAndNoColonsString = noSpaceStringDate.replaceAll("\\:", "");
		System.out.println(npSpaceAndNoColonsString);
		
		String emailwithTimestamp = npSpaceAndNoColonsString+"@gmail.com";
		System.out.println(emailwithTimestamp);
	}

}
