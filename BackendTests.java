import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Test methods from backend work
 * 
 * @author alysiachou
 *
 */
public class BackendTests {
	/**
	 * test loadGraphData()
	 * 
	 * @return true if all tests pass
	 */
	@Test
	public void  roleTest1() {
		GraphADT<String, Double> graph = new Graph_Placeholder();
		Backend backend = new Backend(graph);
		try {
			backend.loadGraphData("campus.dot");
		} catch (IOException e) {
			Assertions.fail();
		}
	}

	/**
	 * test getListOfAllLocations()
	 * 
	 * @return true if all tests pass
	 */
	@Test
	public void  roleTest2() {
		GraphADT<String, Double> graph = new Graph_Placeholder();
		Backend backend = new Backend(graph);
		List<String> actual = backend.getListOfAllLocations();
		List<String> expected = new ArrayList<>();
		expected.add("Union South");
		expected.add("Computer Sciences and Statistics");
		expected.add("Atmospheric, Oceanic and Space Sciences");
		if (!expected.equals(actual))
			Assertions.fail();
	}

	/**
	 * test findLocationsOnShortestPath()
	 * 
	 * @return true if all test pass
	 */
	@Test
	public void roleTest3() {
		GraphADT<String, Double> graph = new Graph_Placeholder();
		Backend backend = new Backend(graph);
		List<String> actual = backend.findLocationsOnShortestPath("Union South", "Computer Sciences and Statistics");
		List<String> expected = new ArrayList<>();
		expected.add("Union South");
		expected.add("Computer Sciences and Statistics");
		if (!expected.equals(actual))
			Assertions.fail();
	}

	/**
	 * test findTimesOnShortestPath()
	 * 
	 * @return true if all test pass
	 */
	@Test
	public void roleTest4() {
		GraphADT<String, Double> graph = new Graph_Placeholder();
		Backend backend = new Backend(graph);
		List<Double> actual = backend.findTimesOnShortestPath("Union South", "Atmospheric, Oceanic and Space Sciences");
		List<Double> expected = new ArrayList<>();
		expected.add(1.0);
		expected.add(2.0);
		if (!expected.equals(actual))
			Assertions.fail();
	}

	/**
	 * test getLongestLocationListFro()
	 * 
	 * @return true if all test pass
	 */
	@Test
	public void roleTest5() {
		GraphADT<String, Double> graph = new Graph_Placeholder();
		Backend backend = new Backend(graph);
		List<String> actual = backend.getLongestLocationListFrom("Computer Sciences and Statistics");
		List<String> expected = new ArrayList<>();
		expected.add("Computer Sciences and Statistics");
		expected.add("Atmospheric, Oceanic and Space Sciences");
		if (!expected.equals(actual))
			Assertions.fail();
	}
	
	/**
         * test generateShortestPathPromptHTML() and
	 * generateLongestLocationListFromPromptHTML() if they print out output as
	 * expected
         * 
         * @return true if all test pass
         */
        @Test
        public void integrationTest1() {
                GraphADT<String, Double> graph = new DijkstraGraph<>();
                Backend backend = new Backend(graph);
		Frontend frontend = new Frontend(backend);
		// check if the strings are printed as expected
		String output1 = frontend.generateShortestPathPromptHTML();
		if ((!output1.contains("<input type='text' placeholder='enter start location' id='start' />")) || (!output1.contains("<input type='text' placeholder='enter end location' id='end' />")) ||
				(!output1.contains("<input type='button' value='Find Shortest Path' id='buttonFindShortest'/>")) )
			Assertions.fail();
		// check if the strings are printed as expected
		String output2 = frontend.generateLongestLocationListFromPromptHTML();
		if ((!output2.contains("<input type='button' value='Longest Location List From' id='buttonLongestLocation'/>")) || (!output2.contains("<input type='text' placeholder='enter start location' id='from' />")))
			Assertions.fail();
        }

	/**
	 * test loadGraphData() 
	 *
	 * return true if all test pass
	 */
	@Test
	public void integrationTest2() {
		GraphADT<String, Double> graph = new DijkstraGraph<>();
                Backend backend = new Backend(graph);
		try {
                	backend.loadGraphData("./campus.dot");
		} catch (Exception e) {
			Assertions.fail();
		}
                Frontend frontend = new Frontend(backend);
		// check if data has been laoded as expected
		if (backend.getListOfAllLocations().size() != 160) Assertions.fail();
		if ((!backend.graph.containsNode("Lot 36 - Observatory Drive Ramp")) || (!backend.graph.containsNode("Steenbock Memorial Library")) || (!backend.graph.containsNode("Wisconsin State Historical Society"))) Assertions.fail(); 
	}

	/**
         * test loadGraphData() and  generateShortestPathResponseHTML()
         * 
         * @return true if all test pass
         */
        @Test
        public void integrationTest3() {
                GraphADT<String, Double> graph = new DijkstraGraph<>();
                Backend backend = new Backend(graph);
		Frontend frontend = new Frontend(backend);
		try {
                        backend.loadGraphData("./campus.dot");
                } catch (Exception e) {
                        Assertions.fail();
                }

		// check if the outcome is expected
		String output1 = frontend.generateShortestPathResponseHTML("Memorial Union", "Union South");
		if ((!output1.contains("Memorial Union")) || (!output1.contains("Union South"))  || (!output1.contains("Education Building")) || (!output1.contains("X01")) || (!output1.contains("Meiklejohn House")) ) {
			Assertions.fail();
		}
        }


	/**
         * test loadGraphData() and  generateLongestLocationListFromResponseHTML()
         * 
         * @return true if all test pass
         */
        @Test
        public void integrationTest4() {
                GraphADT<String, Double> graph = new DijkstraGraph<>();
                Backend backend = new Backend(graph);
                Frontend frontend = new Frontend(backend);
		try {
                        backend.loadGraphData("./campus.dot");
                } catch (Exception e) {
                        Assertions.fail();
                }

                String output1 = frontend.generateLongestLocationListFromResponseHTML("Wisconsin State Historical Society");
                if ((!output1.contains("Computer Sciences and Statistics")) || (!output1.contains("South Hall"))  || (!output1.contains("Phi Kappa Theta")) || (!output1.contains("Smith Residence Hall")) || (!output1.contains("Meiklejohn House")) ) {
                        Assertions.fail();
                }
        }

	 /**
         * test generateShortestPathResponseHTML() with invalid inputs
         * 
         * @return true if all test pass
         */
        @Test
        public void integrationTest5() {
                GraphADT<String, Double> graph = new DijkstraGraph<>();
                Backend backend = new Backend(graph);
                Frontend frontend = new Frontend(backend);

		try {
			frontend.generateShortestPathResponseHTML("Computer Science",null);
		} catch (Exception e) {
			// didn't handle the exception
			Assertions.fail();
		}
        }


	 /**
         * test generateLongestLocationListFromResponseHTML() with invalid inputs
         * 
         * @return true if all test pass
         */
        @Test
        public void integrationTest6() {
                GraphADT<String, Double> graph = new DijkstraGraph<>();
                Backend backend = new Backend(graph);
                Frontend frontend = new Frontend(backend);

                try {
                        String output = frontend.generateLongestLocationListFromResponseHTML("123");
                        if (!output.contains("<p>Start location does not exist, or there are no other locations that can be reached from there")) Assertions.fail();
                } catch (Exception e) {
			Assertions.fail();
		}                                  
        } 


}
