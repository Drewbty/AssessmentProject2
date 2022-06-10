package ds.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BusinessGraph {
	/**
	 * Representation of the graph using an adjacency list
	 * using businesses as vertexes
	 */
	private ArrayList<Business> vertices = new ArrayList<>();

	/**
	 * Method that add a vertex to the adjacency list representation of the graph
	 * @param vertex vertex to be added
	 * @throws BusinessAlreadyExist Exception in case the vertex already exists
	 * @throws InvalidArguments Exception in case the parameters are invalid
	 */
	public void addVertex(final Business vertex) throws BusinessAlreadyExist, InvalidArguments {
		// We discard the basic cases and we throw an exception in case the parameter is wrong
		if (vertex == null) {
			throw new InvalidArguments("Vertex cannot be null");
		}
		if (vertices.contains(vertex)) {
			throw new BusinessAlreadyExist("Business already exists");
		}
		// We add the vertex to the graph.
		vertices.add(vertex);
	}

	/**
	 * Method that remove a vertex to the adjacency list representation of the graph
	 * @param vertex vertex to be added
	 * @throws BusinessDoesnotExist
	 * @throws InvalidArguments
	 */
	public void removeVertex(final Business vertex) throws BusinessDoesnotExist, InvalidArguments {
		// We discard the basic cases and we throw an exception in case the parameter is wrong
		if (vertex == null) {
			throw new InvalidArguments("Vertex cannot be null");
		}
		if (!vertices.contains(vertex)) {
			throw new BusinessDoesnotExist("Business doesn't exists");
		}
		// We remove the vertex from the adjacency list
		vertices.remove(vertex);
	}

	/**
	 * Process that counts the total number of people infected from the business start
	 * up to 3 steps. Internally it uses a BFS to calculate the infected people.
	 * @param start Infection origin
	 * @return Total amount of people infected.
	 */
	public int totalPersonsInfected(Business start) {
		// Let's store the business that has been visited into the visited set.
		HashSet<Business> visited = new HashSet<>();
		// All the infected people will be stored in this set
		HashSet<Person> infected = new HashSet<>();
		// Queue of the BFS
		LinkedList<DestinationLength> queue = new LinkedList<>();

		// We simply add the initial node to the queue
		queue.add(new DestinationLength(0, start));

		// BFS while we have some node enqueued.
		while(!queue.isEmpty()) {
			DestinationLength currentDestination = queue.poll();

			// If we have more than 3 steps or we have visited it previously, then it should be skipped
			if (currentDestination.getSteps() > 3 || visited.contains(currentDestination.getBusiness())) {
				continue;
			}

			// It is masked as visited
			visited.add(currentDestination.getBusiness());


			for (Person edge : currentDestination.getBusiness().getEdges()) {
				// All the people from that business are infected, so we add it to the infected list.
				infected.add(edge);
				// We also enqueue them with an extra step.
				queue.add(new DestinationLength(currentDestination.getSteps() + 1, edge.getBusiness()));
			}
		}

		// We count the amount of infected people in the set.
		return infected.size();
	}

	/**
	 *
	 * @param start
	 * @param dest
	 * @return
	 * @throws InvalidArguments In case the parameters are invalid
	 * @throws DestinationNotReachable In case the destination business is not reachable from the starting node.
	 */
	public int minStepsToDestFromStart(Business start, Business dest) throws InvalidArguments, DestinationNotReachable {
		// Let's store the business that has been visited into the visited set.
		if (dest == null || start == null) {
			throw new InvalidArguments("Neither the destination nor the start can be null");
		}

		// store in this map the previous optimal step to reach this node
		HashMap<Business, Business> previousOne = new HashMap<>();
		// Let's store here the amount of steps needed to reach this node.
		HashMap<Business, Integer> steps = new HashMap<>();

		// We run the DFS
		shortestPathDFS(start, previousOne, steps, 0);

		if (dest != start && !previousOne.containsKey(dest)) {
			throw new DestinationNotReachable("We are unable to reach the destination from the start business");
		}

		int stepsToDo = 0;
		Business currentBusiness = dest;

		while(currentBusiness != start) {
			++stepsToDo;
			currentBusiness = previousOne.get(currentBusiness);
		}

		return stepsToDo;
	}

	/**
	 * DFS to traverse the graph, storing the shortest path in the map
	 * @param currentBusiness
	 * @param previousOne
	 * @param steps
	 * @param currentSteps
	 */
	private void shortestPathDFS(final Business currentBusiness, HashMap<Business, Business> previousOne, HashMap<Business, Integer> steps, int currentSteps ) {
		for (Person edge : currentBusiness.getEdges()) {
			if (edge.getBusiness() != null) {
				if (!previousOne.containsKey(edge.getBusiness())) {
					previousOne.put(edge.getBusiness(), currentBusiness);
					steps.put(edge.getBusiness(), currentSteps + 1);
					shortestPathDFS(edge.getBusiness(), previousOne, steps, currentSteps + 1);
				}else if (steps.get(edge.getBusiness()) > currentSteps + 1) {
					previousOne.put(edge.getBusiness(), currentBusiness);
					steps.put(edge.getBusiness(), currentSteps + 1);
				}
			}
		}
	}

	/**
	 * True if the graph is strongly connected (All the nodes are reachable from the given start
	 * @param start Start from which we start traversing the graph
	 * @return True if the graph is strongly connected
	 * @throws InvalidArguments
	 */
	public boolean isStronglyConnected(Business start) throws InvalidArguments {
		if (start == null) {
			throw new InvalidArguments("Neither the destination nor the start can be null");
		}

		final HashSet<Business> visited = new HashSet<>();
		final LinkedList<Business> queue = new LinkedList<>();
		queue.add(start);

		// we will do an bfs from the starting business, if we visit all the businesses, then it is strongly connected
		while(!queue.isEmpty()) {
			final Business currentBusiness = queue.poll();

			if (visited.contains(currentBusiness)) {
				continue;
			}

			visited.add(currentBusiness);

			for (Person edge : currentBusiness.getEdges()) {
				if (edge.getBusiness() != null) { // just in case.
					queue.add(edge.getBusiness());
				}

			}

		}

		return visited.containsAll(vertices); // if we have visited all the vertices, then all the businesses are reachable from the start
	}

	public ArrayList<Business> getVertices() {
		return vertices;
	}

}
