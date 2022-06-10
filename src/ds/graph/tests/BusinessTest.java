package ds.graph.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import ds.graph.Business;
import ds.graph.DestinationDoesntExist;
import ds.graph.InvalidArguments;
import ds.graph.Person;

public class BusinessTest {

	@Test
	public void testConstructor() {
		final String BUSINESS_NAME = "RANDOM_BUSINESS_NAME";
		Business newBusiness = new Business(BUSINESS_NAME);
		assertEquals("We have the correct name.", BUSINESS_NAME, newBusiness.getName());
		assertTrue("We don't have any edge, because we haven't added them.", newBusiness.getEdges().isEmpty());
	}
	
	@Test
	public void testSetter() {
		final String NEW_BUSINESS_NAME = "NEW BUSINESS NAME HERE";
		final String OLD_BUSINESS_NAME = "OLD BUSINESS NAME";
		Business newBusiness = new Business(OLD_BUSINESS_NAME);
		newBusiness.setName(NEW_BUSINESS_NAME);
		assertEquals(NEW_BUSINESS_NAME, newBusiness.getName());
	}
	
	@Test
	public void testExceptionAddEdge() {
		Business newBusiness = new Business("Business");
		try {
			newBusiness.addEdge(newBusiness, null);
			fail("We were expecting an exception for invalid arguments"); 
		} catch (InvalidArguments e) {
			// We expect it to fail
			assertEquals(e.getMessage(), "Neither the destination nor the route can be null");
			
		}
		
		try {
			newBusiness.addEdge(null, new Person());
			fail("We were expecting an exception for invalid arguments"); 
		} catch (InvalidArguments e) {
			// We expect it to fail
			assertEquals(e.getMessage(), "Neither the destination nor the route can be null");
			
		}
	}
	
	@Test
	public void testRegularAddEdge() {
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Person personB1B2 = new Person("TestPerson", 20, .7f);
		try {
			business1.addEdge(business2, personB1B2);
		} catch (InvalidArguments e) {
			fail("We didn't expect an exception adding an edge");
		}
		
		assertEquals("We have exactly 1 connection", business1.getEdges().size(), 1);
		assertEquals(business1.getEdges().get(0), personB1B2);
		assertEquals(personB1B2.getBusiness(), business2);
	}
	
	@Test
	public void testReplaceAddEdge() {
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Person person1B1B2 = new Person("TestPerson1", 20, .3f);
		Person person2B1B2 = new Person("TestPerson2", 25, .2f);
		
		try {
			business1.addEdge(business2, person1B1B2);
		} catch (InvalidArguments e) {
			fail("We didn't expect an exception adding an edge");
		}
		
		try {
			business1.addEdge(business2, person2B1B2);
		} catch (InvalidArguments e) {
			fail("We didn't expect an exception adding an edge");
		}
		
		assertEquals("We have exactly 1 connection", business1.getEdges().size(), 1);
		assertEquals("Second person has replaced the first one, because it has lower hygiene (and also more infectiveness", business1.getEdges().get(0), person2B1B2);
		assertEquals("The business of the person is the business 2", person2B1B2.getBusiness(), business2);
		assertEquals("The first person should not have a business now, because it was replaced", person1B1B2.getBusiness(), null);
	}
	
	@Test
	public void testInvalidArgumentRemoveEdge() {
		Business business = new Business("Business");
		try {
			business.removeEdge(null);
			fail("We were expecting an invalid argument exception");
		} catch (InvalidArguments e) {
			// Good, we expect this exception to happen
		} catch (DestinationDoesntExist e) {
			fail("We don't expect this exception");
		}
	}
	

	@Test
	public void testRegularRemoveEdge() {
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Person personB1B2 = new Person("TestPerson", 20, .7f);
		try {
			business1.addEdge(business2, personB1B2);
		} catch (InvalidArguments e) {
			fail("We didn't expect an exception adding an edge");
		}
		
		try {
			business1.removeEdge(business2);
		} catch (InvalidArguments e) {
			fail("We don't expect this exception");
		} catch (DestinationDoesntExist e) {
			fail("We don't expect this exception");
		}
		
		assertTrue("We have removed the connection, they should be isolated now", business1.getEdges().isEmpty());
		assertEquals(personB1B2.getBusiness(), null);
	}
	
	@Test
	public void testRemoveNonExistingConnection() {
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Business business3 = new Business("Business3");
		Person personB1B2 = new Person("TestPerson", 20, .7f);
		try {
			business1.addEdge(business2, personB1B2);
		} catch (InvalidArguments e) {
			fail("We didn't expect an exception adding an edge");
		}
		
		try {
			business1.removeEdge(business3);
			fail("We were expecting a destination didn't exist exception");
		} catch (InvalidArguments e) {
			fail("We don't expect this exception");
		} catch (DestinationDoesntExist e) {
			// We are trying to disconnect business 3, but it should be 2. We expect this exception
		}

		assertEquals("We have exactly 1 connection", business1.getEdges().size(), 1);
		assertEquals("Second person has replaced the first one, because it has lower hygiene (and also more infectiveness", business1.getEdges().get(0), personB1B2);
		assertEquals("The business of the person is the business 2", personB1B2.getBusiness(), business2);
	}
	
	@Test
	public void testEquals() {
		Business business1 = new Business("Business1");
		Business business11 = new Business("Business1"); // Clone of the business 1.
		Business business2 = new Business("Business2");
		Business business3 = new Business("Business3");
		
		assertTrue("trivial case", business1.equals(business1));
		assertTrue("Equal Test different business", !business1.equals(business2));
		assertTrue("Equal Test different business", !business1.equals(business3));
		assertTrue("Testing name comparison", business1.equals(business11));
	}
}
