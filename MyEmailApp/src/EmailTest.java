import static org.junit.Assert.*;

import org.junit.Test;


public class EmailTest {

	@Test
	public void SuccessfulLoginTest() {
		String email = "OrangeTest3@hotmail.com";
		String password = "hellopass1+";		//correct password
		EmailApp app = new EmailApp(email, password);
		boolean successful = app.login();
		assertEquals(true, successful);
	}
	
	@Test
	public void InvalidLoginTest() {
		String email = "OrangeTest3@hotmail.com";
		String password = "hellopas";		//incorrect password
		EmailApp app = new EmailApp(email, password);
		boolean successful = app.login();
		assertEquals(true, successful);
	}
	
	@Test
	public void FetchEmails() {
		String email = "OrangeTest3@hotmail.com";
		String password = "hellopass1+";
		EmailApp app = new EmailApp(email, password);
		boolean successful = app.getInbox();
		assertEquals(true, successful);
	}
	
	@Test
	public void OpenAnEmail() {
		String email = "OrangeTest3@hotmail.com";
		String password = "hellopass1+";
		EmailApp app = new EmailApp(email, password);
		app.getInbox();
		String message = app.getMessage(0);
		if (message.equals("")) {
			assertEquals(true, true);
		}
		else {
			assertEquals(true, false);
		}
	}

}