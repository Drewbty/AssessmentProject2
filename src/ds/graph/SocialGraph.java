package ds.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

public class SocialGraph {

	private ArrayList<Person> vertices = new ArrayList<>();

	public SocialGraph() {

	}

	/**
	 * Add the given person to the graph. The person needs to be added to the list
	 * of vertices.
	 *
	 * @param p
	 * @throws PersonAlreadyExists If the person is already present in the graph,
	 *                             this method should throw a PersonAlreadyPresent
	 *                             exception.
	 */
	public void addVertex(Person p) throws PersonAlreadyExists {
		if (vertices.contains(p)) {
			throw new PersonAlreadyExists("Person already exists");
		}

		vertices.add(p);

	}

	/**
	 * Remove the given Person from the graph. Any edges to this person should also
	 * be removed.
	 *
	 * @throws PersonDoesNotExist If the given person is not found inside the graph.
	 * @param p
	 */
	public void removeVertex(Person p) throws PersonDoesNotExist {
		if (!vertices.contains(p)) {
			throw new PersonDoesNotExist("Person doesn't exist");
		}

		vertices.remove(p);

		for (Person person : vertices) {
			person.getContacts().remove(p);
		}
	}

	/**
	 * Add an edge between the two people (vertices) in the graph. The graph is
	 * undirected, so both People will need to list the other, in their list of
	 * contacts.
	 *
	 * If the edge already exists, this method should return true.
	 *
	 * @param a
	 * @param b
	 *
	 * @throws PersonDoesNotExist If the person is not found within the graph.
	 */
	public void addEdge(Person a, Person b) throws PersonDoesNotExist {
		if (!vertices.contains(a) || !vertices.contains(b)) {
			throw new PersonDoesNotExist("Person doesn't exist");
		}

		a.getContacts().add(b);
		b.getContacts().add(a);
	}

	/**
	 * Remove the edge between the given People from the graph. If no edge existed
	 * between these people, this method should throw an EdgeDoesNotExist exception.
	 *
	 * @throws EdgeDoesNotExist
	 * @param a
	 * @param b
	 */
	public void removeEdge(Person a, Person b) throws EdgeDoesNotExist {

		if (!a.getContacts().contains(b) || !b.getContacts().contains(a)) {
			throw new EdgeDoesNotExist("Link doesn't exist");
		}

		a.getContacts().remove(b);
		b.getContacts().remove(a);

	}

	/**
	 * Implement a breadth-first search, from Person start to target. This method
	 * should consider the graph unweighted: the order that the Persons are stored
	 * inside the contacts list will determine the order that the BFS operates.
	 *
	 * @throws PersonDoesNotExist if either start or target are not in the graph.
	 * @param start
	 * @param target
	 * @return A list of nodes that must be traversed to get to target, from start.
	 */
	public ArrayList<Person> searchBFS(Person start, Person target) throws PersonDoesNotExist {
		if (!vertices.contains(start) || !vertices.contains(target)) {
			throw new PersonDoesNotExist("Start or Target person doesn't exist");
		}

		return searchBFSinternal(start, target, false);
	}

	private ArrayList<Person> searchBFSinternal(Person start, Person target, boolean useWeights) {
		ArrayList<Person> path = new ArrayList<>();

		LinkedList<Person> queue = new LinkedList<>();
		queue.add(start);
		HashSet<Person> visited = new HashSet<>();

		while (!queue.isEmpty()) { // only important if we have visited all the people
			Person currentPerson = queue.poll();

			if (visited.contains(currentPerson)) {
				continue;
			}

			visited.add(currentPerson);
			path.add(currentPerson);

			if (currentPerson.equals(target)) {
				return path;
			}

			queue.addAll(weightedInfectivenessCompare(currentPerson, useWeights));
		}

		return path;
	}

	/**
	 * Implement a breadth-first search, from Person start to target. The weights
	 * associated with each edge should determine the order that the BFS operates.
	 * Higher weights are preferred over lower weights. The weight is found by
	 * calling getInfectiveness() on the Person.
	 *
	 * @throws PersonDoesNotExist if either start or target are not in the graph.
	 * @param start
	 * @param target
	 * @return A list of nodes that must be traversed to get to target, from start.
	 */
	public ArrayList<Person> searchWeightedBFS(Person start, Person target) throws PersonDoesNotExist {
		if (!vertices.contains(start) || !vertices.contains(target)) {
			throw new PersonDoesNotExist("Start or Target person doesn't exist");
		}

		return searchBFSinternal(start, target, true);
	}

	/**
	 * Implement a depth-first search, from Person start to target. This method
	 * should consider the graph unweighted: the order that the Persons are stored
	 * inside the contacts list will determine the order that the DFS operates.
	 *
	 * @throws PersonDoesNotExist if either start or target are not in the graph.
	 * @param start
	 * @param target
	 * @return A list of nodes that must be traversed to get to target, from start.
	 */
	public ArrayList<Person> searchDFS(Person start, Person target) throws PersonDoesNotExist {
		if (!vertices.contains(start) || !vertices.contains(target))
			throw new PersonDoesNotExist("Person isn't in the graph");

		return searchDFS(start, target, new HashSet<>(), false);
	}

	private ArrayList<Person> searchDFS(Person start, Person target, HashSet<Person> visited, boolean useWeight) {
		System.out.println(start);
		visited.add(start);
		ArrayList<Person> path = new ArrayList<>();
		path.add(start);
		if (start.equals(target)) {

			return path;
		}

		ArrayList<Person> personToVisit = weightedInfectivenessCompare(start, useWeight);

		for (Person person : personToVisit) {
			if (!visited.contains(person)) {
				ArrayList<Person> subpath = searchDFS(person, target, visited, useWeight);
				path.addAll(subpath);
				if (path.get(path.size() - 1).equals(target)) {
					return path;
				}
			}
		}

		// In this case, we were in a path that didn't reach the target, because the
		// other nodes were previously visited
		return path;
	}

	private ArrayList<Person> weightedInfectivenessCompare(Person start, boolean useWeight) {
		ArrayList<Person> personToVisit = new ArrayList<>(start.getContacts());
		if (useWeight) {
			personToVisit.sort(new Comparator<Person>() {

				@Override
				public int compare(Person o1, Person o2) {
					if (o1.getinfectiveness() > o2.getinfectiveness()) {
						return -1;
					} else if (o1.getinfectiveness() == o2.getinfectiveness()) {
						return 0;
					}
					return 1;
				}
			});
		}
		return personToVisit;
	}

	/**
	 * Implement a depth-first search, from Person start to target. The weights
	 * associated with each edge should determine the order that the DFS operates.
	 * Higher weights are preferred over lower weights. The weight is found by
	 * calling getInfectiveness() on the Person.
	 *
	 * @throws PersonDoesNotExist if either start or target are not in the graph.
	 * @param start
	 * @param target
	 * @return A list of nodes that must be traversed to get to target, from start.
	 */
	public ArrayList<Person> searchWeightedDFS(Person start, Person target) throws PersonDoesNotExist {
		if (!vertices.contains(start) || !vertices.contains(target))
			throw new PersonDoesNotExist("Person isn't in the graph");

		return searchDFS(start, target, new HashSet<>(), true);
	}

	/**
	 * This method should return an int value showing the total number of
	 * contacts-of-contacts of the start person. This is the equivalent to doing a
	 * BFS around the start person, and counting the vertices 2 steps away from the
	 * start node.
	 *
	 * @throws PersonDoesNotExist if either start or target are not in the graph.
	 * @param start
	 * @return
	 */
	public int countContacts(Person start) {
		HashSet<Person> visited = new HashSet<>();

		LinkedList<Person> queue = new LinkedList<>();
		queue.addAll(start.getContacts());

		visited.add(start);
		visited.addAll(start.getContacts());

		int amountOfContacts = 0;
		while (!queue.isEmpty()) { // While we have still contacts of contacts to visit
			Person currentPerson = queue.poll();

			for (Person p : currentPerson.getContacts()) {
				if (!visited.contains(p)) {
					visited.add(p);
					++amountOfContacts;
				}
			}
		}
		return amountOfContacts;
	}

}
