import java.util.List;
import java.util.NoSuchElementException;

public class Frontend implements FrontendInterface {

	private BackendInterface backend;

	// Backend_Placeholder will be passed in - not anymore
	public Frontend(BackendInterface backend) {

		this.backend = backend;
	}

	/**
	 * Returns an HTML fragment that can be embedded within the body of a larger
	 * html page. This HTML output should include:
	 * 
	 * - a text input field with the id="start", for the start location
	 * 
	 * - a text input field with the id="end", for the destination
	 * 
	 * - a button labelled "Find Shortest Path" to request this computation
	 * 
	 * Ensure that these text fields are clearly labelled, so that the user can
	 * understand how to use them.
	 * 
	 * @return an HTML string that contains input controls that the user can make
	 *         use of to request a shortest path computation
	 */
	@Override
	public String generateShortestPathPromptHTML() {

		String result = "<input type='text' placeholder='enter start location' id='start' />" + "\n"
				+ "<input type='text' placeholder='enter end location' id='end' />" + "\n"
				+ "<input type='button' value='Find Shortest Path' id='buttonFindShortest'/>";

		return result;
	}

	/**
	 * Returns an HTML fragment that can be embedded within the body of a larger
	 * html page. This HTML output should include:
	 * 
	 * - a paragraph (p) that describes the path's start and end locations
	 * 
	 * - an ordered list (ol) of locations along that shortest path
	 * 
	 * - a paragraph (p) that includes the total travel time along this path
	 * 
	 * Or if there is no such path, the HTML returned should instead indicate the
	 * kind of problem encountered.
	 * 
	 * @param start is the starting location to find a shortest path from
	 * @param end   is the destination that this shortest path should end at
	 * @return an HTML string that describes the shortest path between these two
	 *         locations
	 */
	@Override
	public String generateShortestPathResponseHTML(String start, String end) {

		/*
		 * a list with the nodes along the shortest path from startLocation to
		 * endLocation, or an empty list if no such path exists
		 */
		List<String> locations = backend.findLocationsOnShortestPath(start, end);

		// check input validity (see if list is empty)
		if (locations.size() == 0) { // start.equals(end) || - removed
			return "<p>No such shortest path from " + start + " to " + end + ".</p><ol></ol>";
		}

		/*
		 * a list with the walking times in seconds between two nodes along the shortest
		 * path from startLocation to endLocation, or an empty list if no such path
		 * exists
		 */
		List<Double> times = backend.findTimesOnShortestPath(start, end);

		// create list of locations
		StringBuilder path = new StringBuilder("<ol>");

		for (String location : locations) {
			path.append("<li>").append(location).append("</li>");
		}

		path.append("</ol>");

		// add up total time
		double totalTime = 0.0;

		for (Double time : times) {
			totalTime += time;
		}

		String result = "</p>The shortest path from " + start + " to " + end + " is: </p>" + path
				+ "<p>Total travel time along this path: " + totalTime + " </p>";

		return result;

	}

	/**
	 * Returns an HTML fragment that can be embedded within the body of a larger
	 * html page. This HTML output should include:
	 * 
	 * - a text input field with the id="from", for the start location
	 * 
	 * - a button labelled "Longest Location List From" to submit this request
	 * 
	 * Ensure that this text field is clearly labelled, so that the user can
	 * understand how to use it.
	 * 
	 * @return an HTML string that contains input controls that the user can make
	 *         use of to request a longest location list calculation
	 */
	@Override
	public String generateLongestLocationListFromPromptHTML() {

		String result = "<input type='button' value='Longest Location List From' id='buttonLongestLocation'/>" + "\n"
				+ "<input type='text' placeholder='enter start location' id='from' />";
		return result;

	}

	/**
	 * Returns an HTML fragment that can be embedded within the body of a larger
	 * html page. This HTML output should include:
	 * 
	 * - a paragraph (p) that describes the path's start and end locations
	 * 
	 * - an ordered list (ol) of locations along that shortest path
	 * 
	 * - a paragraph (p) that includes the total number of locations on path
	 * 
	 * Or if no such path can be found, the HTML returned should instead indicate
	 * the kind of problem encountered.
	 * 
	 * @param start is the starting location to find the longest list from
	 * @return an HTML string that describes the longest list of locations along a
	 *         shortest path starting from the specified location
	 */
	@Override
	public String generateLongestLocationListFromResponseHTML(String start) {

		try {

			/*
			 * Returns the longest list of locations along any shortest path that starts
			 * from startLocation and ends at any of the reachable destinations in the
			 * graph.
			 */
			List<String> locations = backend.getLongestLocationListFrom(start);

			String end = "";

			// create list of locations
			StringBuilder path = new StringBuilder("<ol>");

			for (int i = 0; i < locations.size(); i++) {
				String location = locations.get(i);
				path.append("<li>").append(location).append("</li>");

				// save last location to use in String result - to describe end location
				if (i == locations.size() - 1) {
					end = locations.get(i);
				}
			}

			path.append("</ol>");

			// add up total time
			int locationCount = 0;
			
			for (String location : locations) {
				locationCount += 1;
			}
			
			// this means that the given start location does not exist
			if (locationCount == 0) {
				throw new NoSuchElementException();
			}

			String result = "<p>Longest list of locations starting from " + start + " to " + end + ": </p>" + path
					+ "<p>" + "Total number of locations on path: " + locationCount + " </p>";

			return result;
		}

		/*
		 * no such path can be found: startLocation does not exist, or there are no
		 * other locations that can be reached from the input
		 * 
		 * the backend method of getLongestLocationListFrom() will throw NoSuchElementException
		 * 
		 * when this happens, return error message and empty list
		 */
		catch (NoSuchElementException e) {
			return "<p>Start location does not exist, or there are no other locations that can be reached from there"
					+ ".</p><ol></ol>";
		}
	}

}

