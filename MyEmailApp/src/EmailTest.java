import static org.junit.Assert.*;

import org.junit.Test;


public class EmailTest {

	@Test
	public void SuccessfulLoginTest() {
		EmailReader er = new EmailReader();
		String email = "TestEmailOrange@hotmail.com";
		String password = "verysecure";		//correct password
		boolean successful = er.login(emailAddress, password);
		assertEquals(true, successful);
	}
	
	@Test
	public void InvalidLoginTest() {
		EmailReader er = new EmailReader();
		String email = "TestEmailOrange@hotmail.com";
		String password = "verysecurr"; 	//incorrect password
		boolean successful = er.login(emailAddress, password);
		assertEquals(false, successful);
	}
	
	@Test
	public void FetchEmails() {
		EmailReader er = new EmailReader();
		boolean successful = er.fetchContent();
		assertEquals(true, successful);
	}
	
	@Test
	public void OpenAnEmail() {
		EmailReader er = new EmailReader();
		boolean successful = er.openEmailContent(int id);
		assertEquals(true, successful);
	}

}