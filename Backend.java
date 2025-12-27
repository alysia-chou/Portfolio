import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
   The class help determine how bakend works
*/
public class Backend implements BackendInterface {
	public GraphADT<String, Double> graph;

	/**
	 * Implementing classes should support the constructor below.
	 * 
	 * @param graph object to store the backend's graph data
	 */
	public Backend(GraphADT<String, Double> graph) {
		this.graph = graph;
	}

	/**
	 * Loads graph data from a dot file. If a graph was previously loaded, this
	 * method should first delete the contents (nodes and edges) of the existing
	 * graph before loading a new one.
	 * 
	 * @param filename the path to a dot file to read graph data from
	 * @throws IOException if there was any problem reading from this file
	 */
	@Override
	public void loadGraphData(String filename) throws IOException {
		
		// clear the previous data
		if (graph.getNodeCount() > 0) {
			for (String node : graph.getAllNodes()) {
				graph.removeNode(node);
			}
		}
		// create a scanner to store the file
		Scanner fileScnr;
		try {
			// read the file
			File file = new File(filename);
			fileScnr = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new IOException("File not found");
		}
		// we don't need the first line
		fileScnr.nextLine();

		// fields to store nodes and edges
		String location1 = null;
		String location2 = null;
		Double second = null;

		// read all the lines in the file
		while (fileScnr.hasNextLine()) {
			// get next line
			String line = fileScnr.nextLine();

			// when get to the last line the while loop will break
			if (line.contains("}"))
				break;

			// find the index of cut-offs for locations and seconds
			int indexOfBetweenLocations = line.indexOf(" -> ");
			int indexOfSeconds = line.indexOf(" [seconds=");
			int indexOfEnd = line.indexOf("];");

			// find the first node
			location1 = line.substring(2, indexOfBetweenLocations - 1);
		
			// find the second node
			location2 = line.substring(indexOfBetweenLocations + 5, indexOfSeconds - 1);
			// find the edge value
			second = Double.parseDouble(line.substring(indexOfSeconds + 10, indexOfEnd));

			// store them in graph
			graph.insertNode(location1);
			graph.insertNode(location2);
			graph.insertEdge(location1, location2, second);
		}

	}

	/**
	 * Returns a list of all locations (node data) available in the graph.
	 * 
	 * @return list of all location names
	 */
	@Override
	public List<String> getListOfAllLocations() {
		return this.graph.getAllNodes();
	}

	/**
	 * Return the sequence of locations along the shortest path from startLocation
	 * to endLocation, or an empty list if no such path exists.
	 * 
	 * @param startLocation the start location of the path
	 * @param endLocation   the end location of the path
	 * @return a list with the nodes along the shortest path from startLocation to
	 *         endLocation, or an empty list if no such path exists
	 */
	@Override
	public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
		return graph.shortestPathData(startLocation, endLocation);
	}

	/**
	 * Return the walking times in seconds between each two nodes on the shortest
	 * path from startLocation to endLocation, or an empty list of no such path
	 * exists.
	 * 
	 * @param startLocation the start location of the path
	 * @param endLocation   the end location of the path
	 * @return a list with the walking times in seconds between two nodes along the
	 *         shortest path from startLocation to endLocation, or an empty list if
	 *         no such path exists
	 */
	@Override
	public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {
		// store all of the locations along the path from startLocation to endLocation
		List<String> locationsOnShortestPath = findLocationsOnShortestPath(startLocation, endLocation);
		// create a list to store the walking times between each two nodes
		List<Double> secondsList = new ArrayList<>();
		// check if locationsOnShortestPath is empty
		if (!locationsOnShortestPath.isEmpty()) {
			// loop through the list and get the edge value between each two nodes and add
			// them to the list
			for (int i = 0; i < locationsOnShortestPath.size() - 1; i++) {
				secondsList.add(graph.getEdge(locationsOnShortestPath.get(i), locationsOnShortestPath.get(i + 1)));
			}
		}
		return secondsList;
	}

	/**
	 * Returns the longest list of locations along any shortest path that starts
	 * from startLocation and ends at any of the reachable destinations in the
	 * graph.
	 * 
	 * @param startLocation the location to search through paths leaving from
	 * @return the longest list of locations found on any shortest path that starts
	 *         at the specified startLocation.
	 * @throws NoSuchElementException if startLocation does not exist, or if there
	 *                                are no other locations that can be reached
	 *                                from there
	 */
	@Override
	public List<String> getLongestLocationListFrom(String startLocation) throws NoSuchElementException {
		// check if startLocation exits
		if (startLocation == null || startLocation.isEmpty() || !graph.containsNode(startLocation))
			throw new NoSuchElementException();
		// get all the nodes
		List<String> allNodes = graph.getAllNodes();
		if (allNodes.isEmpty()) throw new NoSuchElementException();
		// create a list to store possible ending nodes
		List<String> possibleEnds = new ArrayList<>();
		// store the sum of edge values that is the smallest
		double edgeCost = 0;
		// loop through the allNodes list and see if startLocation can reach to it and
		// compare it with edgeCost to get the shortest path and add the end nodes to
		// the list
		for (int i = 0; i < allNodes.size(); i++) {
			// compare the edge weight from startlocation to each nodes in allNodes with edgeCost and find the shortest edge weight 
			// and add the node from allNodes to the list to store possible endLocations
			if (!allNodes.get(i).equals(startLocation)) {
				try { 
					edgeCost = graph.shortestPathCost(startLocation, allNodes.get(i));
					possibleEnds.add(allNodes.get(i));
				} catch (Exception e){
				}
			}
		}

		// check to get to the ending nodes which one will pass the most locations
		int count = 0;
		String end = null;
		for (int i = 0; i < possibleEnds.size(); i++) {
			try {
				if (graph.shortestPathData(startLocation, possibleEnds.get(i)).size() > count) {
					count = graph.shortestPathData(startLocation, possibleEnds.get(i)).size();
					end = possibleEnds.get(i);
				}
			} catch (Exception e) {
			}
		}
		// if there are no other locations that can be reach from startLocation throw
		// NoSuchElementException
		if (end == null)
			throw new NoSuchElementException();
		// return the longest list of locations along any shortest path from
		// startLocation
		return graph.shortestPathData(startLocation, end);
	}

}
