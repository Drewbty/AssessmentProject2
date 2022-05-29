package ds.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testConstructor() {
		Person p = new Person("Namehere", 40, .5f);
		assertEquals(p.getName(), "Namehere", "Correct Name");
		assertEquals(p.getAge(), 40, "Correct Age");
		assertEquals(p.getSocialHygiene(), .5f, "Correct Hygiene");
	}

	@Test
	void testSetters() {
		Person p = new Person("Namehere", 40, .5f);
		p.setAge(50);
		p.setName("Andrew");
		p.setSocialHygiene(.1f);
		assertEquals(p.getName(), "Andrew", "Correct Name");
		assertEquals(p.getAge(), 50, "Correct Age");
		assertEquals(p.getSocialHygiene(), .1f, "Correct Social Hygiene");
	}

	@Test
	void testInfectiveness() {
		Person p = new Person("John", 60, .2f);
		assertEquals(p.getinfectiveness(),
				1 - ((p.getAge() - (p.getAge() * p.getSocialHygiene())) / p.getAge()),
				"Correct Infectiveness");

	}

	@Test
	void testInfectiveness2() {
		Person p = new Person("John", 30, .5f);
		assertEquals(p.getinfectiveness(), .5f, "Correct Infectiveness");

	}

}
