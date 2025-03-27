package tutorialsninja.register;

import java.time.Duration;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;



public class TC_RF_002_Trial {

	@Test
	public void verifyConfirmationEmail() {
		
		
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://www.joom.com/");
		
		driver.findElement(By.xpath("//div[text()='Log in']")).click();
		driver.findElement(By.xpath("//button[text()='Show more login options']")).click();
		driver.findElement(By.xpath("//a[text()='Email']")).click();
		driver.findElement(By.xpath("//a[text()='Forgotten your password?']")).click();
		driver.findElement(By.name("email")).sendKeys("ypashankar@gmail.com");
		driver.findElement(By.xpath("//div[text()='Send reset link']")).click();
		
		String email = "ypashankar@gmail.com";
		String appPasscode= "rpch vriy glzo htqi";
	
		// Gmail IMAP settings
        String host = "imap.gmail.com";
        String port = "993";
        String username = email;
        String password = appPasscode;  // Not recommended, use OAuth2 for better security
       
        try {
            // Mail server connection properties
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", port);
            properties.put("mail.imap.ssl.enable", "true");
           
            // Connect to the mail server
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect("imap.gmail.com", username, password); // replace email password with App password
            
            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            // Search for unread emails
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            
            boolean found = false;
            for (int i = messages.length - 1; i >= 0; i--) {
            	
            	Message message = messages[i];
            
                if (message.getSubject().contains("Reset your password")) {
                    found = true;
                    System.out.println("Email Subject: " + message.getSubject());
                    System.out.println("Email From: " + message.getFrom()[0].toString());
                    System.out.println("Email Body: " + getTextFromMessage(message));
                    break;
                }
            }

            if (!found) {
                System.out.println("No confirmation email found.");
            }

            // Close the store and folder objects
            inbox.close(false);
            store.close();

        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        driver.quit();
       
	}

	 private static String getTextFromMessage(Message message) throws Exception {
	        String result = "";
	        if (message.isMimeType("text/plain")) {
	            result = message.getContent().toString();
	        } else if (message.isMimeType("text/html")) {
	            result = message.getContent().toString();
	        } else if (message.isMimeType("multipart/*")) {
	            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
	            result = getTextFromMimeMultipart(mimeMultipart);
	        }
	        return result;
    }
	 
	 private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
	        StringBuilder result = new StringBuilder();
	        int count = mimeMultipart.getCount();
	        for (int i = 0; i < count; i++) {
	            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	            if (bodyPart.isMimeType("text/plain")) {
	                result.append(bodyPart.getContent());
	            } else if (bodyPart.isMimeType("text/html")) {
	                result.append(bodyPart.getContent());
	            } else if (bodyPart.getContent() instanceof MimeMultipart) {
	                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
	            }
	        }
	        return result.toString();
	 }

}