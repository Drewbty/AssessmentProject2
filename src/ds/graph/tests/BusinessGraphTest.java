package ds.graph.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import ds.graph.Business;
import ds.graph.BusinessAlreadyExist;
import ds.graph.BusinessDoesnotExist;
import ds.graph.BusinessGraph;
import ds.graph.DestinationNotReachable;
import ds.graph.InvalidArguments;
import ds.graph.Person;

public class BusinessGraphTest {
	
	@Test
	public void addVertexTest() {
		final BusinessGraph graph = new BusinessGraph();
		Business business1 = new Business("Business1");
		Business business11 = new Business("Business1"); // same business
		Business business2 = new Business("Business2");
		
		try {
			graph.addVertex(null);
			fail("We expected an Invalid Exception");
		} catch (BusinessAlreadyExist e) {
			fail("We didn't expect this exception");
		} catch (InvalidArguments e) {
			// GOOD
		}
		
		try {
			graph.addVertex(business1);
			graph.addVertex(business2);
		} catch (BusinessAlreadyExist e) {
			fail("We didn't expect this exception");
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		}
		
		try {
			graph.addVertex(business11);
			fail("We expected an Business Already Exist Exception");
		} catch (BusinessAlreadyExist e) {
			// GOOD
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		}
		
		assertEquals("Amount of vertices", graph.getVertices().size(), 2);
		assertEquals("Correct Vertex", graph.getVertices().get(0), business1);
		assertEquals("Correct Vertex", graph.getVertices().get(1), business2);
	}
	
	@Test
	public void testRemoveVertex() {
		final BusinessGraph graph = new BusinessGraph();
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Business business3 = new Business("Business3");

		try {
			graph.removeVertex(null);
			fail("We expected an BusinessDoesnotExist Exception");
		} catch (InvalidArguments e) {
			// GOOD
		} catch (BusinessDoesnotExist e) {
			fail("We didn't expect this exception");
		}
		
		try {
			graph.addVertex(business1);
			graph.addVertex(business2);
		} catch (BusinessAlreadyExist e) {
			fail("We didn't expect this exception");
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		}

		try {
			graph.removeVertex(business3);
			fail("We expected an BusinessDoesnotExist Exception");
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		} catch (BusinessDoesnotExist e) {
			// GOOD
		}
		

		try {
			graph.removeVertex(business2);
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		} catch (BusinessDoesnotExist e) {
			fail("We didn't expect this exception");
		}
		
		assertEquals("Amount of vertices", graph.getVertices().size(), 1);
		assertEquals("Correct Vertex", graph.getVertices().get(0), business1);

		try {
			graph.removeVertex(business1);
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		} catch (BusinessDoesnotExist e) {
			fail("We didn't expect this exception");
		}
		
		assertTrue("It should be empty", graph.getVertices().isEmpty());
	}
	
	@Test
	public void testTotalInfected() {
		final BusinessGraph graph = new BusinessGraph();
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Business business3 = new Business("Business3");
		Business business4 = new Business("Business4");
		Business business5 = new Business("Business5");
		Business business6 = new Business("Business6");
		Person person1 = new Person("A", 15, .3f);
		Person person2 = new Person("B", 15, .2f);
		Person person3 = new Person("C", 15, .4f);
		Person person4 = new Person("D", 15, .6f);
		Person person5 = new Person("E", 15, .1f);

		try {
			graph.addVertex(business1);
			graph.addVertex(business2);
			graph.addVertex(business3);
			graph.addVertex(business4);
			graph.addVertex(business5);
			graph.addVertex(business6);
			business1.addEdge(business2, person1);
			business2.addEdge(business3, person2);
			business3.addEdge(business4, person3);
			business4.addEdge(business5, person4);
			business5.addEdge(business6, person5);
		} catch (BusinessAlreadyExist e) {
			fail("We didn't expect this exception");
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		}
		
		assertEquals("Total Infected Person", graph.totalPersonsInfected(business1), 4);
	}

	@Test
	public void testIsStronglyConnected() {
		final BusinessGraph graph = new BusinessGraph();
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Business business3 = new Business("Business3");
		Person person1 = new Person("A", 15, .23f);
		Person person2 = new Person("B", 15, .255f);

		try {
			graph.addVertex(business1);
			graph.addVertex(business2);
			graph.addVertex(business3);
			business1.addEdge(business2, person1);
			business2.addEdge(business3, person2);
		} catch (BusinessAlreadyExist e) {
			fail("We didn't expect this exception");
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		}

		try {
			assertTrue("It is strongly connected", graph.isStronglyConnected(business1));
			assertTrue("It is strongly connected", !graph.isStronglyConnected(business3));
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		}
		

		try {
			assertTrue("Invalid", graph.isStronglyConnected(null));
			fail("We expected this exception");
		} catch (InvalidArguments e) {
			// GOOD
		}
	}
	
	@Test
	public void testMinStepsToDestFromStart() {
		final BusinessGraph graph = new BusinessGraph();
		Business business1 = new Business("Business1");
		Business business2 = new Business("Business2");
		Business business3 = new Business("Business3");
		Business business4 = new Business("Business4");
		Person person1 = new Person("A", 15, .3f);
		Person person2 = new Person("B", 15, .4f);
		Person person3 = new Person("C", 15, .6f);

		try {
			graph.addVertex(business1);
			graph.addVertex(business2);
			graph.addVertex(business3);
			graph.addVertex(business4);
			business1.addEdge(business2, person1);
			business2.addEdge(business3, person2);
			business2.addEdge(business4, person3);
		} catch (BusinessAlreadyExist e) {
			fail("We didn't expect this exception");
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		}
		
		try {
			assertEquals("Min steps from start", graph.minStepsToDestFromStart(business1, business3), 2);
			
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		} catch (DestinationNotReachable e) {
			fail("We didn't expect this exception");
		}
		
		try {
			graph.minStepsToDestFromStart(business3, business1);
			fail("We did expected an exception");
		} catch (InvalidArguments e) {
			fail("We didn't expect this exception");
		} catch (DestinationNotReachable e) {
			// GOOD
		}
	}
}
