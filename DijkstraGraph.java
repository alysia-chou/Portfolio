// === CS400 File Header Information ===
// Name: Alysia Chou
// Email: wchou22@wisc.edu
// Group and Team: P211.3602
// Lecturer: Florian
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number> extends BaseGraph<NodeType, EdgeType>
		implements GraphADT<NodeType, EdgeType> {

	/**
	 * While searching for the shortest path between two nodes, a SearchNode
	 * contains data about one specific path between the start node and another node
	 * in the graph. The final node in this path is stored in its node field. The
	 * total cost of this path is stored in its cost field. And the predecessor
	 * SearchNode within this path is referened by the predecessor field (this field
	 * is null within the SearchNode containing the starting node in its node
	 * field).
	 *
	 * SearchNodes are Comparable and are sorted by cost so that the lowest cost
	 * SearchNode has the highest priority within a java.util.PriorityQueue.
	 */
	protected class SearchNode implements Comparable<SearchNode> {
		public Node node;
		public double cost;
		public SearchNode predecessor;

		public SearchNode(Node node, double cost, SearchNode predecessor) {
			this.node = node;
			this.cost = cost;
			this.predecessor = predecessor;
		}

		public int compareTo(SearchNode other) {
			if (cost > other.cost)
				return +1;
			if (cost < other.cost)
				return -1;
			return 0;
		}
	}

	/**
	 * Constructor that sets the map that the graph uses.
	 */
	public DijkstraGraph() {
		super(new HashtableMap<>());
	}

	/**
	 * This helper method creates a network of SearchNodes while computing the
	 * shortest path between the provided start and end locations. The SearchNode
	 * that is returned by this method is represents the end of the shortest path
	 * that is found: it's cost is the cost of that shortest path, and the nodes
	 * linked together through predecessor references represent all of the nodes
	 * along that shortest path (ordered from end to start).
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return SearchNode for the final end node within the shortest path
	 * @throws NoSuchElementException when no path from start to end is found or
	 *                                when either start or end data do not
	 *                                correspond to a graph node
	 */
	protected SearchNode computeShortestPath(NodeType start, NodeType end) {
		// check if the graph contains start and end
		if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
			throw new NoSuchElementException();
		}
		if (start.equals(end)) {
        		return new SearchNode(nodes.get(start), 0, null);
    		}
		// create a PriorityQueue
		PriorityQueue<SearchNode> path = new PriorityQueue<>();
		// create a MapADT to store the nodes that have been visited
		MapADT<NodeType, SearchNode> visitedNodes = new HashtableMap<>();
		// add the start node to th priority queue
		path.add(new SearchNode(nodes.get(start), 0, null));
		// while path is no empty continue finding the next node
		while (!path.isEmpty()) {
			// pop the first SearchNode
			SearchNode current = path.poll();
			// if the end is the same as the current return current
			if (current.node.data.equals(end)) {
				return current;
			}
			// if current is not visited
			if (!visitedNodes.containsKey(current.node.data)) {
				visitedNodes.put(current.node.data, current);
				// loop through all the edgesLeaving for this current Node
				for (Edge edge : current.node.edgesLeaving) {
					// add each edge in with updated cost
					path.add(new SearchNode(edge.successor, edge.data.doubleValue() + current.cost, current));
				}
			}
		}
		// check if there is any exception thrown: there doesn't exist a path in between
		// nodes or start and end are not in the graph
		throw new NoSuchElementException();

	}

	/**
	 * Returns the list of data values from nodes along the shortest path from the
	 * node with the provided start value through the node with the provided end
	 * value. This list of data values starts with the start value, ends with the
	 * end value, and contains intermediary values in the order they are encountered
	 * while traversing this shorteset path. This method uses Dijkstra's shortest
	 * path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return list of data item from node along this shortest path
	 */
	public List<NodeType> shortestPathData(NodeType start, NodeType end) {

		if (start == null || end == null) {
        		throw new IllegalArgumentException("Start and end nodes cannot be null.");
    		}

		try {
			// call the helper method with start and end nodes
			SearchNode current = computeShortestPath(start, end);
			// create a list to put the nodes
			LinkedList<NodeType> nodes = new LinkedList<>();
			// add the start node to the list
			nodes.addFirst(current.node.data); 
			// loop through the results of the helper method
			while (current.predecessor != null) {
				nodes.addFirst(current.predecessor.node.data);
				current = current.predecessor;
			}
			return nodes;
			// check if there is any exception thrown or there doesn't exist a path in
			// between nodes or start and end are not in the graph
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Returns the cost of the path (sum over edge weights) of the shortest path
	 * freom the node containing the start data to the node containing the end data.
	 * This method uses Dijkstra's shortest path algorithm to find this solution.
	 *
	 * @param start the data item in the starting node for the path
	 * @param end   the data item in the destination node for the path
	 * @return the cost of the shortest path between these nodes
	 */
	public double shortestPathCost(NodeType start, NodeType end) {
		// implement in step 5.4
		try {
			// get the path from start to end and get the cost
			SearchNode path = computeShortestPath(start, end);
			return path.cost;
			// chech if there are any exception thrown
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
	}
	@Test
	public void test1() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<String, Integer>();
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");
		graph.insertNode("G");
		graph.insertNode("H");

		graph.insertEdge("A", "B", 4);
		graph.insertEdge("A", "C", 2);
		graph.insertEdge("A", "E", 15);
		graph.insertEdge("B", "E", 10);
		graph.insertEdge("C", "D", 5);
		graph.insertEdge("B", "D", 1);
		graph.insertEdge("D", "E", 3);
		graph.insertEdge("D", "F", 0);
		graph.insertEdge("F", "D", 2);
		graph.insertEdge("F", "H", 4);
		graph.insertEdge("G", "H", 4);

		// check if it's implemented as expected
		List<String> actual = graph.shortestPathData("A", "H");
		List<String> expected = new ArrayList<>();
		expected.add("A");
		expected.add("B");
		expected.add("D");
		expected.add("F");
		expected.add("H");
		if (!actual.equals(expected))
			Assertions.fail();

		// get the cost from A to H
		double actual_cost = graph.shortestPathCost("A", "H");
		double expected_cost = 9;
		if (actual_cost != expected_cost)
			Assertions.fail();
	}
	@Test
	public void test2() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<String, Integer>();
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");
		graph.insertNode("G");
		graph.insertNode("H");

		graph.insertEdge("A", "B", 4);
		graph.insertEdge("A", "C", 2);
		graph.insertEdge("A", "E", 15);
		graph.insertEdge("B", "E", 10);
		graph.insertEdge("C", "D", 5);
		graph.insertEdge("B", "D", 1);
		graph.insertEdge("D", "E", 3);
		graph.insertEdge("D", "F", 0);
		graph.insertEdge("F", "D", 2);
		graph.insertEdge("F", "H", 4);
		graph.insertEdge("G", "H", 4);

		// get the data sequence from A to D
		List<String> actual = graph.shortestPathData("A", "D");
		List<String> expected = new LinkedList<>();
		expected.add("A");
		expected.add("B");
		expected.add("D");
		if (!actual.equals(expected))
			Assertions.fail();

		// get the cost from D to H
		double actual_cost = graph.shortestPathCost("D", "H");
		double expected_cost = 4;
		if (actual_cost != expected_cost)
			Assertions.fail();
	}
	@Test
	public void test3() {
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<String, Integer>();
		graph.insertNode("A");
		graph.insertNode("B");
		graph.insertNode("C");
		graph.insertNode("D");
		graph.insertNode("E");
		graph.insertNode("F");
		graph.insertNode("G");
		graph.insertNode("H");

		graph.insertEdge("A", "B", 4);
		graph.insertEdge("A", "C", 2);
		graph.insertEdge("A", "E", 15);
		graph.insertEdge("B", "E", 10);
		graph.insertEdge("C", "D", 5);
		graph.insertEdge("B", "D", 1);
		graph.insertEdge("D", "E", 3);
		graph.insertEdge("D", "F", 0);
		graph.insertEdge("F", "D", 2);
		graph.insertEdge("F", "H", 4);
		graph.insertEdge("G", "H", 4);

		// check if it throws exception when unable to get shortest path
		try {
			graph.computeShortestPath("A", "G");
			Assertions.fail();
		} catch (NoSuchElementException e) {
		}

	}


}
