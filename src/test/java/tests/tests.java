package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.Application;

public class tests {
	private static Thread t;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		t = new Thread()
		{
			public void run(){
			    try {
				Application.main(null);
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			}
		};
		t.start();
		Thread.sleep(3000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		t.interrupt();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	
	
	
	
	
	
	
	
}
