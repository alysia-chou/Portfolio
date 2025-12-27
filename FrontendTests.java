
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class FrontendTests {

	/**
	 * Testing generateShortestPathPromptHTML() and
	 * generateLongestLocationListFromPromptHTML()
	 */
	@Test
	public void roleTest1() {

		Graph_Placeholder graph1 = new Graph_Placeholder();
		Backend_Placeholder backend1 = new Backend_Placeholder(graph1);
		Frontend frontend1 = new Frontend(backend1);

		String result1 = frontend1.generateShortestPathPromptHTML();

		Assertions.assertTrue(result1.contains("<input type='text' placeholder='enter start location' id='start' />"));
		Assertions.assertTrue(result1.contains("<input type='text' placeholder='enter end location' id='end' />"));
		Assertions.assertTrue(
				result1.contains("<input type='button' value='Find Shortest Path' id='buttonFindShortest'/>"));
		Assertions.assertTrue(result1.contains("\n"));

		result1 = frontend1.generateLongestLocationListFromPromptHTML();

		Assertions.assertTrue(result1
				.contains("<input type='button' value='Longest Location List From' id='buttonLongestLocation'/>"));
		Assertions.assertTrue(result1.contains("<input type='text' placeholder='enter start location' id='from' />"));
		Assertions.assertTrue(result1.contains("\n"));
	}

	/**
	 * Testing generateShortestPathResponseHTML()
	 */
	@Test
	public void roleTest2() {
		Graph_Placeholder graph2 = new Graph_Placeholder();
		Backend_Placeholder backend2 = new Backend_Placeholder(graph2);
		Frontend frontend2 = new Frontend(backend2);

		String result2 = frontend2.generateShortestPathResponseHTML("Union South",
				"Atmospheric, Oceanic and Space Sciences");

		System.out.println(result2);

		// result string should show 3 locations and total travel time of 6.0
		Assertions.assertTrue(result2.contains("Union South"));
		Assertions.assertTrue(result2.contains("Computer Sciences and Statistics"));
		Assertions.assertTrue(result2.contains("Atmospheric, Oceanic and Space Sciences"));
		Assertions.assertTrue(result2.contains("Total travel time along this path: 6.0"));

		result2 = frontend2.generateShortestPathResponseHTML("Computer Sciences and Statistics",
				"Atmospheric, Oceanic and Space Sciences");

		System.out.println(result2);

		// result string should show 2 locations and total travel time of 3.0
		Assertions.assertTrue(!result2.contains("Union South"));
		Assertions.assertTrue(result2.contains("Computer Sciences and Statistics"));
		Assertions.assertTrue(result2.contains("Atmospheric, Oceanic and Space Sciences"));
		Assertions.assertTrue(result2.contains("Total travel time along this path: 3.0"));

		/*
		 * This test doesn't work anymore because frontend method doesn't check for
		 * start.equals(end) anymore. It only checks if the returned list is empty,
		 * since the backend method should return an empty method when there is an
		 * error. However, the backend placeholder doesn't have this behavior.
		 */
		// result2 = frontend2.generateShortestPathResponseHTML("Union South", "Union
		// South");
		// Assertions.assertTrue(result2.contains("No such shortest path from Union
		// South to Union South."));
		// System.out.println(result2);

		// testing invalid input
		result2 = frontend2.generateShortestPathResponseHTML("a", "b");

		Assertions.assertTrue(result2.contains("No such shortest path from a to b."));

		System.out.println(result2);

	}

	/**
	 * Testing generateLongestLocationListFromResponseHTML()
	 */
	@Test
	public void roleTest3() {

		Graph_Placeholder graph3 = new Graph_Placeholder();
		Backend_Placeholder backend3 = new Backend_Placeholder(graph3);
		Frontend frontend3 = new Frontend(backend3);

		// returns the locations leading to the last node
		String result3 = frontend3.generateLongestLocationListFromResponseHTML("Union South");

		// result string should show all three locations and should show location count
		// of 3
		Assertions.assertTrue(result3.contains("Union South"));
		Assertions.assertTrue(result3.contains("Computer Sciences and Statistics"));
		Assertions.assertTrue(result3.contains("Atmospheric, Oceanic and Space Sciences"));
		Assertions.assertTrue(result3.contains("Total number of locations on path: 3"));

		System.out.println(result3);

		result3 = frontend3.generateLongestLocationListFromResponseHTML("Atmospheric, Oceanic and Space Sciences");

		// no other locations can be reached from start point
		Assertions.assertTrue(!result3.contains("Union South"));
		Assertions.assertTrue(!result3.contains("Computer Sciences and Statistics"));
		Assertions.assertTrue(result3.contains("Atmospheric, Oceanic and Space Sciences"));
		Assertions.assertTrue(result3.contains("Total number of locations on path: 1"));

		System.out.println(result3);

		// invalid input, should show error message
		result3 = frontend3.generateLongestLocationListFromResponseHTML("bad_input");
		Assertions.assertTrue(result3.contains(
				"Start location does not exist, or there are no other locations that can be reached from there"));

		System.out.println(result3);

	}

}
