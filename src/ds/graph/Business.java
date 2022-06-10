package ds.graph;

import java.util.ArrayList;
import java.util.Objects;

public class Business {

	/**
	 * Name of the business
	 */
	private String name;
	/**
	 * Connection to other businesses via person
	 */
	private ArrayList<Person> edges = new ArrayList<>();



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
		// We discard the basic cases and we throw an exception in case parameters are wrong
		if (dest == null || route == null) {
			throw new InvalidArguments("Neither the destination nor the route can be null");
		}

		// check if someone was already going there.
		float myInfec = route.getinfectiveness();

		for (Person otherPerson : edges) {
			if (otherPerson.getBusiness() != null && otherPerson.getBusiness().equals(dest)) {
				// In case we have a connection to the same business, but via a different person.
				if (otherPerson.getinfectiveness() > myInfec) {
					// If it has more infectiveness, then this person will replace the previous one
					try {
						// to replace, we remove the previous edge and then we add the new one
						removeEdge(dest);
						break;
					} catch (DestinationDoesntExist e) {
						// We are sure that the destination exists. We just checked it in the conditional.
					}
				}else {
					// In this case, we saw that previously we had a connection to the same business with a person having more infectiveness.
					return;
				}
			}
		}
		// We didn't found a route to this business or we removed a route with less infectiveness, so we add the new one
		edges.add(route);
		// We assign to the person the business that we are arriving to
		route.setBusiness(dest);
	}

	public void removeEdge(Business dest) throws InvalidArguments, DestinationDoesntExist {
		// We discard the basic cases and we throw an exception in case the parameter is wrong
		if (dest == null) {
			throw new InvalidArguments("Route cannot be null");
		}

		Person edge = null;
		for (Person otherPerson : edges) {
			// we search the person who arrives to the business desired
			if (dest.equals(otherPerson.getBusiness())) {
				edge = otherPerson;
			}
		}

		if (edge == null) {
			throw new DestinationDoesntExist("That destination business doesn't exists");
		}

		// we remove the business destination from the person.
		edge.setBusiness(null);
		// We remove the person from the edges list
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
		if (obj == null || obj.hashCode() != hashCode() || (getClass() != obj.getClass()))
			return false;
		Business other = (Business) obj;
		return Objects.equals(name, other.name);
	}


}
