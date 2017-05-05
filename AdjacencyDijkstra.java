import edu.uci.ics.jung.graph.Graph;

import java.util.*;

public class AdjacencyDijkstra {

    private Graph<MyVertex, MyEdge> g;
    private MyVertex[][] adjacencyMatrix;
    private int[][] judgementMatrix;
    private int maxAdjacentNodes;
    private Vector<Integer> distanceVector;
    private Vector<Integer> currentVector;
    private Vector<Integer> markingVector;
    private Vector<Integer> tempVector;
    private List<Integer> vertexList;

    public AdjacencyDijkstra(Graph<MyVertex, MyEdge> g) {
        this.g = g;
        maxAdjacentNodes = findMaxAdjacentNodes();
        adjacencyMatrix = new MyVertex[g.getVertexCount()][maxAdjacentNodes];
        judgementMatrix = new int[g.getVertexCount()][maxAdjacentNodes];
        populateMatrices();
        distanceVector = new Vector<>();
        currentVector = new Vector<>();
        markingVector = new Vector<>();
        tempVector = new Vector<>();
        vertexList = new ArrayList<>();

        int counter = 0;
        for (MyVertex myVertex : g.getVertices()) {
            distanceVector.add(Integer.MAX_VALUE);
            markingVector.add(-1);
            tempVector.add(counter++);
        }
    }

    public void findShortestPath(MyVertex source, MyVertex target) {

        //While running the double for loops, each new run will have to
        //reset the values of the distance, marking, and temp vectors,
        //as they are basically the meta data for finding the shortest path
        for (int i = 0; i < distanceVector.size(); i++) {
            distanceVector.set(i, Integer.MAX_VALUE);
            markingVector.set(i, -1);
            tempVector.set(i, i);
        }

        //Initialize the distance and marking vectors from the source node
        for (MyVertex myVertex: g.getNeighbors(source)) {
            distanceVector.set(source.getId(), g.findEdge(source, myVertex).getWeight());
            markingVector.set(myVertex.getId(), source.getId());
        }


        for (int i = 0; i < g.getVertexCount(); i++) {
            int minDistanceVector = Integer.MAX_VALUE;
            int minDistanceIndex = 0;
            //First loop finds the minimum weight of the vectors added to the distance vector
            for (int j = 0; j < distanceVector.size(); j++) {
                if (tempVector.get(j) != -1) {
                    if (distanceVector.get(j) < minDistanceVector) {
                        minDistanceVector = distanceVector.get(j);
                        minDistanceIndex = j;
                    }
                }
            }
            tempVector.set(minDistanceIndex, -1);
            //Second loop updates the minimum distance from the source node to any new nodes found,
            //if necessary
            for (int j = 0; j < maxAdjacentNodes; j++) {
                int newEdge = minDistanceVector + judgementMatrix[minDistanceIndex][j];
                int nextVertex;
                if (adjacencyMatrix[minDistanceIndex][j] != null) {
                    nextVertex = adjacencyMatrix[minDistanceIndex][j].getId();
                    if (newEdge < distanceVector.get(nextVertex)) {
                        distanceVector.set(nextVertex, newEdge);
                        markingVector.set(nextVertex, minDistanceIndex);
                    }
                }
            }
        }
    }

    private void populateJudgementMatrix(MyVertex myVertex, int count) {
        for (int i = 0; i < maxAdjacentNodes; i++) {
            MyVertex thisVertex = adjacencyMatrix[count][i];
            if (thisVertex != null) {
                judgementMatrix[count][i] = g.findEdge(myVertex, thisVertex).getWeight();
            }
            else {
                judgementMatrix[count][i] = Integer.MAX_VALUE;
            }
        }
    }

    private void populateMatrices() {
        int count = 0;
        for (MyVertex myVertex: g.getVertices()) {
            myVertex.setId(count++);
            int neighborCount = 0;
            for(MyVertex myVertex1: g.getNeighbors(myVertex)) {
                adjacencyMatrix[count - 1][neighborCount++] = myVertex1;
            }
            populateJudgementMatrix(myVertex, count - 1);
        }
    }

    /*
     * Find the max adjacent nodes any vertex has to
     * determine the size of the adjacency and judgment matrices
     */

    private int findMaxAdjacentNodes() {
        int maxEdges = 0;
        for (MyVertex myVertex: g.getVertices()) {
            int currentEdgeCount = g.getNeighborCount(myVertex);
            if (currentEdgeCount > maxEdges) {
                maxEdges = currentEdgeCount;
            }
        }
        return maxEdges;
    }
}
