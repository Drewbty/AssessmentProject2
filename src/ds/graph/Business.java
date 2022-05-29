package ds.graph;

import java.util.ArrayList;
import java.util.Objects;

public class Business {

	private String name;
	private ArrayList<Person> edges = new ArrayList<Person>();
	
	
	
	public Business(String name) {
		super();
		this.name = name;
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
	 * @return the edges
	 */
	public ArrayList<Person> getEdges() {
		return edges;
	}

	public void addEdge(Business dest, Person route) throws InvalidArguments {
		if (dest == null || route == null) {
			throw new InvalidArguments("Neither the destination nor the route can be null");
		}
		
		// check if someone was already going there.
		float myInfec = route.getinfectiveness();
		
		for (Person otherPerson : edges) {
			if (otherPerson.getBusiness() != null && otherPerson.getBusiness().equals(dest)) {
				if (otherPerson.getinfectiveness() > myInfec) {
					try {
						removeEdge(dest);
					} catch (DestinationDoesntExist e) {
						// We are sure that the destination exists. We just checked it in the conditional.
					}
				}else {
					return;
				}
			}
		}
		edges.add(route);
		route.setBusiness(dest);
	}
	
	public void removeEdge(Business dest) throws InvalidArguments, DestinationDoesntExist {
		if (dest == null) {
			throw new InvalidArguments("Route cannot be null");
		}
		
		Person edge = null;
		for (Person otherPerson : edges) {
			if (dest.equals(otherPerson.getBusiness())) {
				edge = otherPerson;
			}
		}
		
		if (edge == null) {
			throw new DestinationDoesntExist("That destination business doesn't exists");
		}
		
		// We remove the person from the edges list
		edge.setBusiness(null);
		edges.remove(edge);
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
		if (getClass() != obj.getClass())
			return false;
		Business other = (Business) obj;
		return Objects.equals(name, other.name);
	}
	
	
}
