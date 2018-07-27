package junit;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chord.model.User;

public class Tester {

	public static User testUser;
	
	@BeforeClass
	public static void createTestUser() {
		
		testUser = new User("Test",  "Tester",  "test@test.test",  "10-10-10",  "mcTest",
				 "TarpTest",  "CountryTest",  "HardDanceTest",  "test.jpg",  "I am a test");
	}
	
	@Before
	public void beforeTestMessage() {
		System.out.println("RUNNING TEST");
	}
	
	@Test
	public void testChangeFirstName() {
		testUser.setFirstname("NewTest");
		assertEquals("Testing setFirstname()", "NewTest", testUser.getFirstname());
	}
	
	@After
	public void afterTestMessage() {
		System.out.println("TEST COMPLETE");
	}
	
	@AfterClass
	public static void deleteTestUser() {
		testUser = null;
	}
}
