
runServer: WebApp.class
	sudo java WebApp 80
runTests: Backend.class Frontend.class HashtableMap.class DijkstraGraph.class BackendTests.class 
	java -jar ../junit5.jar --class-path=. --select-class=BackendTests   
clean:
	rm -f *.class


Backend.class: Backend.java BackendInterface.java
	javac -cp ../junit5.jar:. Backend.java
Frontend.class: Frontend.java FrontendInterface.java
	javac -cp ../junit5.jar:. Frontend.java
FrontendTests.class: FrontendTests.java Frontend.class Backend.class
	javac -cp ../junit5.jar:. FrontendTests.java
BackendTests.class: BackendTests.java Frontend.class Backend.class
	javac -cp ../junit5.jar:. BackendTests.java
HashtableMap.class: HashtableMap.java MapADT.java  
	 javac -cp ../junit5.jar:. HashtableMap.java
DijkstraGraph.class: DijkstraGraph.java BaseGraph.java GraphADT.java 
	javac -cp ../junit5.jar:. DijkstraGraph.java
WebApp.class: WebApp.java  Backend.class Frontend.class HashtableMap.class DijkstraGraph.class 
	javac -cp ../junit5.jar:. WebApp.java 
