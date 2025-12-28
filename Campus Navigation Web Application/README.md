# Campus Navigation Web Application

### Overview

This project is a Java-based campus navigation web application that computes the shortest path between campus locations using graph algorithms. It integrates a modular backend implementing Dijkstra’s algorithm with a lightweight web frontend, allowing users to query optimal routes between buildings.

The project emphasizes clean abstraction design, algorithmic correctness, and test-driven development, making it suitable for both academic and real-world engineering contexts.

### Features

* 📍 Graph-based representation of campus locations

* 🚀 Shortest-path computation using Dijkstra’s algorithm

* 🧱 Modular architecture with clear ADTs and interfaces

* 🌐 Web-accessible frontend

* ✅ Comprehensive backend and frontend tests

* 🔧 Build automation via Makefile

### Tech Stack

* Language: Java

* Algorithms: Dijkstra’s Shortest Path

* Web: HTML templating

* Testing: JUnit-style unit tests

* Build Tools: Makefile

* Data Representation: Graph (.dot file)

### Project Architecture
#### Backend

The backend is responsible for graph construction, pathfinding, and data access.

Key components:

* GraphADT.java – Abstract graph interface
* BaseGraph.java – Core graph implementation
* DijkstraGraph.java – Shortest-path logic using Dijkstra’s algorithm
* Backend.java / BackendInterface.java – Application logic layer
* HashtableMap.java, MapADT.java – Custom map implementation

#### Frontend

The frontend handles user input and displays routing results.

Key components:

* Frontend.java
* FrontendInterface.java
* template.html
* index.cgi

#### Data

* campus.dot – Graph definition of campus locations and paths

### How It Works

1. Campus locations are loaded into a weighted graph.
2. User inputs a start and destination location via the web interface.
3. The backend computes the shortest path using Dijkstra’s algorithm.
4. The frontend displays the resulting route and distance.

### How to Build & Run
#### Compile
```bash
make
```
#### Web Usage

* Deploy index.cgi and template.html on a CGI-enabled server
* Ensure the Java backend is compiled and accessible
* Open the web page and submit start/end locations
### Testing

* BackendTests.java validates:
  * Graph construction
  * Path correctness
  * Edge cases (no path, same start/end)

* FrontendTests.java verifies:
  * Input handling
  * Output formatting
  * Integration with backend responses

### Design Highlights

* Separation of concerns through interfaces (ADT-first design)

* Custom data structures instead of built-in collections

* Algorithm-focused implementation suitable for scaling

* Emphasis on readability and maintainability

### Future Improvements

* Add interactive map visualization
* Support multiple routing algorithms (A*, BFS)
* Improve UI/UX with modern frontend frameworks
* Cache frequently requested paths
* Add accessibility enhancements

### Acknowledgements

Developed as part of a data structures and algorithms coursework project, with a focus on real-world applicability of graph algorithms.





