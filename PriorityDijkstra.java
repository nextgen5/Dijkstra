import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import org.apache.commons.collections15.Transformer;

import java.util.*;

public class PriorityDijkstra{

    private Hypergraph<MyVertex, MyEdge> g;
    private Transformer<MyEdge, ? extends Number> nev;
    private List<MyVertex> path;
    PriorityQueue<MyVertex> vertexQueue;

    public PriorityDijkstra(Graph<MyVertex, MyEdge> g, Transformer<MyEdge, ? extends Number> nev) {
        this.g = g;
        this.nev = nev;
        path = new ArrayList<>();
        vertexQueue = new PriorityQueue<>();
    }

    private void computePaths(MyVertex source) {

        resetminDistance();
        source.setMinDistance(0);
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {

            MyVertex u = vertexQueue.poll();
            for (MyVertex v : g.getNeighbors(u)){

                if (v != source) {
                    MyEdge e = g.findEdge(u, v);
                    int weight = this.nev.transform(e).intValue();
                    int distanceThroughU = u.getMinDistance() + weight;
                    if (distanceThroughU < v.getMinDistance()) {
                        vertexQueue.remove(v);
                        v.setMinDistance(distanceThroughU);    //v.minDistance = distanceThroughU ;
                        v.setPrevious(u);                      // v.previous = u;
                        vertexQueue.add(v);
                    }
                }
            }
        }
    }
    /*
     * Place each vertex in a list after the shortest path has been found
     */

    public List<MyVertex> getShortestPathTo(MyVertex source, MyVertex target) {
        path.clear();
        computePaths(source);
        for (MyVertex vertex = target; vertex != null; vertex = vertex.getPrevious()) {
            path.add(vertex);
        }
        resetVertices();
        Collections.reverse(path);
        return path;

}
    /*
     * Function to reset min distance of each vertex after each iteration
     */
    private void resetminDistance() {
        for (MyVertex vertex: g.getVertices()) {
            vertex.setMinDistance(Integer.MAX_VALUE);
        }
    }

    /*
     * Function to reset the "previous" value of each vertex after the shortest path
     * between two vertices has been found
     */
    private void resetVertices() {
        for(MyVertex vertex: g.getVertices()) {
            vertex.setPrevious(null);
        }
    }
}
