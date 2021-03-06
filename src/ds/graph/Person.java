package ds.graph;

import java.util.ArrayList;
import java.util.Objects;

public class Person {

	private float socialHygiene;
	private int age;
	private String name;
	private ArrayList<Person> contacts = new ArrayList<>();
	private Business business;

	public Person() {

	}

	public Person(String name, int age, float socialHygiene) {
		this.name = name;
		this.age = age;
		this.socialHygiene = socialHygiene;
	}

	public float getinfectiveness() {
		return 1 - ((this.age - (this.age * this.socialHygiene)) / this.age);
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


	/**
	 * @return the destination
	 */
	public Business getBusiness() {
		return business;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setBusiness(Business destination) {
		this.business = destination;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (this.hashCode() != obj.hashCode()) || (getClass() != obj.getClass()))
			return false;
		Person other = (Person) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Person: " + getName() + ", " + getAge() + ". Contacts: " + getContacts().size() + ". " + (getBusiness() == null ? "This person doesn't have a business" : getBusiness());

	}

}
