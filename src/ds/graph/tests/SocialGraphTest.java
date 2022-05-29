package ds.graph.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ds.graph.EdgeDoesNotExist;
import ds.graph.Person;
import ds.graph.PersonAlreadyExists;
import ds.graph.PersonDoesNotExist;
import ds.graph.SocialGraph;

class SocialGraphTest {

	SocialGraph sg = new SocialGraph();
	Person alice, abigail, ana, andy, calisa, bryony, blake, bernice, bethany;

	@BeforeEach
	void setUp() throws Exception {

		// Create Persons to insert into the social graph.
		Person a1 = new Person("Alice", 20, .2f);
		Person a2 = new Person("Abigail", 30, .43f);
		Person a3 = new Person("Anna", 40, .13f);
		Person a4 = new Person("Andy", 50, .79f);
		Person a5 = new Person("Aaron", 60, .98f);
		alice = a1;
		abigail = a2;
		ana = a3;
		andy = a4;

		sg.addVertex(a1);
		sg.addVertex(a2);
		sg.addVertex(a3);
		sg.addVertex(a4);
		sg.addVertex(a5);

		sg.addEdge(a1, a2);
		sg.addEdge(a1, a3);
		sg.addEdge(a1, a4);

		sg.addEdge(a2, a4);
		sg.addEdge(a2, a5);

		Person b1 = new Person("Bethany", 35, .78f);
		Person b2 = new Person("Bernice", 55, .86f);
		Person b3 = new Person("Blake", 85, .27f);
		Person b4 = new Person("Bryony", 95, .82f);
		Person b5 = new Person("Bertha", 5, .01f);

		bethany = b1;
		bernice = b2;
		blake = b3;
		bryony = b4;

		sg.addVertex(b1);
		sg.addVertex(b2);
		sg.addVertex(b3);
		sg.addVertex(b4);
		sg.addVertex(b5);

		sg.addEdge(b1, b2);
		sg.addEdge(b2, b3);
		sg.addEdge(b3, b4);
		sg.addEdge(b4, b5);

		sg.addEdge(b1, a2);
		sg.addEdge(b5, a3);

		Person c1 = new Person("Calissa", 36, .78f);
		Person c2 = new Person("Cassy", 37, .78f);
		Person c3 = new Person("Chanai", 42, .78f);
		Person c4 = new Person("Colleen", 45, .78f);
		Person c5 = new Person("Caylee", 21, .78f);
		Person c6 = new Person("Charvik", 16, .78f);
		Person c7 = new Person("Calab", 63, .78f);
		Person c8 = new Person("Cornell", 92, .78f);
		Person c9 = new Person("Chadwick", 27, .78f);
		Person c10 = new Person("Charlie", 23, .78f);

		sg.addVertex(c1);
		sg.addVertex(c2);
		sg.addVertex(c3);
		sg.addVertex(c4);
		sg.addVertex(c5);
		sg.addVertex(c6);
		sg.addVertex(c7);
		sg.addVertex(c8);
		sg.addVertex(c9);
		sg.addVertex(c10);

		sg.addEdge(a1, c1);
		calisa = c1;
		sg.addEdge(c2, c3);
		sg.addEdge(c4, c5);
		sg.addEdge(c6, c1);

		sg.addEdge(b1, c3);
		sg.addEdge(b4, c4);

		sg.addEdge(a5, c7);
		sg.addEdge(c8, c9);
		sg.addEdge(b3, c10);
		sg.addEdge(c10, c8);

		sg.addEdge(c8, c1);
	}

	@Test
	void testContacts() {
		assertEquals(alice.getContacts().size(), 4, "Amount of contacts of Alice");

		assertTrue(alice.getContacts().containsAll(Arrays.asList(new Person[] { ana, andy, abigail, calisa })),
				"Correct contacts");

	}

	@Test
	void testEdgeDoesNotExist() {
		try {
			sg.removeEdge(andy, ana);

			fail("We were expecting an exception, because that edge didn't exist");
		} catch (EdgeDoesNotExist e) {
			// We expect it to fail
		}

	}

	@Test
	void testPersonAlreadyExists() {
		Person a6 = new Person("Alice", 40, .5f);
		Person a7 = new Person("Johnny", 30, .2f);
		Person a8 = new Person("Alice", 20, .1f);

		SocialGraph socialGraph = new SocialGraph();

		try {
			socialGraph.addVertex(a6);
			socialGraph.addVertex(a7);
		} catch (PersonAlreadyExists e) {
			fail("We don't expect an error inserting the first person");
		}

		try {
			socialGraph.addVertex(a8);

			// if it doesn't fail, then, it is not working well
			fail("We expected an exception Person Already Exists");
		} catch (PersonAlreadyExists e) {
			// We expect an error
		}

	}

	@Test
	void testPersonDoesNotExist() {
		Person a6 = new Person("Alice", 40, .5f);
		Person a7 = new Person("Johnny", 30, .2f);
		Person a8 = new Person("Pete", 20, .1f);

		SocialGraph socialGraph = new SocialGraph();

		try {
			socialGraph.addVertex(a6);
			socialGraph.addVertex(a7);
		} catch (PersonAlreadyExists e) {
			fail("We expect this to pass");
		}

		try {
			socialGraph.removeVertex(a8);
			fail(a8 + "isn't in the graph so we expect to fail");
		} catch (PersonDoesNotExist e) {

		}

	}

	@Test
	void testEdgeRemoval() {
		try {
			sg.removeEdge(alice, abigail);
			assertEquals(alice.getContacts().size(), 3, "Amount of contacts of Alice");

			assertTrue(alice.getContacts().containsAll(Arrays.asList(new Person[] { ana, andy, calisa })),
					"Correct contacts");
		} catch (EdgeDoesNotExist e) {
			// It should not fail
			fail("Exception during removal of alice and abigail");
		}

	}

	// Test add edge

	@Test
	void testAddEdge() {

		if (ana.getContacts().contains(andy)) {
			try {
				sg.removeEdge(ana, andy);
			} catch (EdgeDoesNotExist e) {
				fail("We didnt expect exception");
			}
		}

		assertTrue(!ana.getContacts().contains(andy), "We are sure they are not contact");

		try {
			sg.addEdge(andy, ana);

			assertTrue(ana.getContacts().containsAll(Arrays.asList(new Person[] { andy })), "Correct contacts");
		} catch (PersonDoesNotExist e) {
			// We expect it to fail
			fail("Exception during the addition of alice and abigail");
		}

	}

	// Test bfs

	@Test
	void testBFS() {

		try {
			ArrayList<Person> path = sg.searchBFS(alice, bryony);
			assertEquals(path.get(0), alice, "Alice is the first person");
			assertEquals(path.get(path.size() - 1), bryony, "Bryony is the last person");
		} catch (PersonDoesNotExist e) {
			fail("Exception during BFS");
		}

	}
	// Test dfs

	@Test
	void testDFS() {

		try {
			ArrayList<Person> path = sg.searchDFS(alice, bryony);
			assertEquals(path.get(0), alice, "Alice is the first person");
			assertEquals(path.get(1), abigail, "Abigail is the second person");
			assertEquals(path.get(path.size() - 1), bryony, "Bryony is the last person");
		} catch (PersonDoesNotExist e) {
			fail("Exception during DFS");
		}

	}

	@Test
	void testCountContacts() {

		assertEquals(sg.countContacts(andy), 4, "Correct amount of contacts of c");
		assertEquals(sg.countContacts(abigail), 5, "Correct amount of contacts of c");
		assertEquals(sg.countContacts(bryony), 4, "Correct amount of contacts of c");

	}

	@Test
	void testWeightedDFS() {

		try {
			ArrayList<Person> path = sg.searchWeightedDFS(alice, bryony);
			assertEquals(path.get(0), alice, "Alice is the first person");
			assertEquals(path.get(path.size() - 1), bryony, "Bryony is the last person");
			assertEquals(path.get(1), andy, "Andy is the second person");
			assertEquals(path.get(2), abigail, "Abigail is the 3rd person");
		} catch (PersonDoesNotExist e) {
			fail("Exception during DFS");
		}

	}

	@Test
	void testWeightedBFS() {

		try {
			ArrayList<Person> path = sg.searchWeightedBFS(bernice, blake);
			assertEquals(path.get(0), bernice, "Alice is the first person");
			assertEquals(path.get(path.size() - 1), blake, "blake is the last person");
			assertEquals(path.get(1), bethany, "Bethany is the second person");
			assertEquals(path.get(2), blake, "Blake is the 3rd person");
		} catch (PersonDoesNotExist e) {
			fail("Exception during DFS");
		}

	}

}
