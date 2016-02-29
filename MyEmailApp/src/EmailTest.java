import static org.junit.Assert.*;

import org.junit.Test;


public class EmailTest {
	
	String email = "OrangeTest3@hotmail.com";
	String password = "hellopass1+";

	@Test
	public void SuccessfulLoginTest() {
		//correct password
		EmailApp app = new EmailApp(email, password);
		boolean successful = app.login();
		assertEquals(true, successful);
	}
	
	@Test
	public void InvalidLoginTest() {
		password = "hellopas";		//incorrect password
		EmailApp app = new EmailApp(email, password);
		boolean successful = app.login();
		assertEquals(false, successful);
	}
	
	@Test
	public void FetchEmails() {
		EmailApp app = new EmailApp(email, password);
		app.login();
		boolean successful = app.getInbox();
		assertEquals(true, successful);
	}
	
	@Test
	public void OpenAnEmail() {
		EmailApp app = new EmailApp(email, password);
		app.login();
		app.getInbox();
		String message = app.getBody(app.getMessage(0));
		if (message.equals(null)) {
			assertEquals(true, false);
		}
		else {
			assertEquals(true, true);
		}
	}
	
	@Test
	public void GetEmailSubject() {
		EmailApp app = new EmailApp(email, password);
		app.login();
		app.getInbox();
		String subject = app.getEmailSubject(app.getMessage(0));
		if (subject.equals(null)) {
			assertEquals(true, false);
		}
		else {
			assertEquals(true, true);
		}
	}
	
	@Test
	public void GetEmailReceivedDate() {
		EmailApp app = new EmailApp(email, password);
		app.login();
		app.getInbox();
		String date = app.getReceivedDate(app.getMessage(0));
		if (date.equals(null)) {
			assertEquals(true, false);
		}
		else {
			assertEquals(true, true);
		}
	}

}