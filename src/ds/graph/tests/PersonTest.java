package ds.graph.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ds.graph.Person;

public class PersonTest {

	@Test
	public void testConstructor() {
		Person p = new Person("Namehere", 40, .5f);
		assertEquals("Correct Name", p.getName(), "Namehere");
		assertEquals("Correct Age", p.getAge(), 40);
		assertTrue("Correct Hygiene", Math.abs(p.getSocialHygiene() - .5f) < 1e-10);
	}

	@Test
	public void testSetters() {
		Person p = new Person("Namehere", 40, .5f);
		p.setAge(50);
		p.setName("Andrew");
		p.setSocialHygiene(.1f);
		assertEquals("Correct Name", p.getName(), "Andrew");
		assertEquals("Correct Age", p.getAge(), 50);
		assertTrue("Correct Social Hygiene", Math.abs(p.getSocialHygiene() - .1f) < 1e-10);
	}

	@Test
	public void testInfectiveness() {
		Person p = new Person("John", 60, .2f);
		assertTrue("Correct Infectiveness", Math.abs(p.getinfectiveness() -
				(1 - ((p.getAge() - (p.getAge() * p.getSocialHygiene())) / p.getAge()) )) < 1e-8);

	}

	@Test
	public void testInfectiveness2() {
		Person p = new Person("John", 30, .5f);
		assertTrue("Correct Infectiveness", Math.abs(p.getinfectiveness() - .5f) < 1e-10);

	}

}
