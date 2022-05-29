package ds.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BusinessGraph {
	private ArrayList<Business> vertices = new ArrayList<Business>();
	
	public void addVertex(final Business vertex) throws BusinessAlreadyExist {
		if (vertices.contains(vertex)) {
			throw new BusinessAlreadyExist("Business already exists");
		}
		vertices.add(vertex);
	}
	
	public void removeVertex(final Business vertex) throws BusinessDoesnotExist {
		if (!vertices.contains(vertex)) {
			throw new BusinessDoesnotExist("Business doesn't exists");
		}
		vertices.remove(vertex);
	}
	
	public int totalPersonsInfected(Business start) {
		
	}
	
	
	public int minStepsToDestFromStart(Business start, Business dest) {
		HashMap<Business, Business> previousOne = new HashMap<>();
		HashMap<Business, Integer> steps = new HashMap<Business, Integer>();
		
		shortestPathDFS(start, previousOne, steps, 0);
		
		int stepsToDo = 0;
		Business currentBusiness = dest;
		
		while(currentBusiness != start) {
			++stepsToDo;
			currentBusiness = previousOne.get(currentBusiness);
		}
		
		return stepsToDo;
	}
	
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
	
	public boolean isStronglyConnected(Business start) {
		final HashSet<Business> visited = new HashSet<Business>();
		final LinkedList<Business> queue = new LinkedList<>();
		queue.add(start);
		visited.add(start);
		
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
}
