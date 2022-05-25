package ds.graph;

import java.util.ArrayList;
import java.util.Objects;

public class Person {

	private float socialHygiene;
	private int age;
	private String name;
	private ArrayList<Person> contacts;

	public Person(String name, int age, float socialHygiene) {
		this.name = name;
		this.age = age;
		this.socialHygiene = socialHygiene;
		this.contacts = new ArrayList<Person>();
	}

	public float getinfectiveness() {
		return (getAge() / 100 - (getSocialHygiene() * getAge()));
	}

	/**
	 * @return the socialHygiene
	 */
	public float getSocialHygiene() {
		return socialHygiene;
	}

	/**
	 * @param socialHygiene the socialHygiene to set
	 */
	public void setSocialHygiene(float socialHygiene) {
		this.socialHygiene = socialHygiene;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the contacts
	 */
	public ArrayList<Person> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(ArrayList<Person> contacts) {
		this.contacts = contacts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.hashCode() != obj.hashCode()) {
			return false;
		}
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(name, other.name);
	}

	public String toString() {
		return "Person: " + getName() + ", " + getAge() + ". Contacts: " + getContacts().size() + ".";

	}

}
