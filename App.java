import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rise of Gen Inc on 4/21/2017.
 */


public class App {
    static Graph<MyVertex, MyEdge> sparseGraph;

    public static void main(String[] args) {
        Transformer<MyEdge, Integer> vertexIntegerTransformer = new Transformer<MyEdge, Integer>() {
            @Override
            public Integer transform(MyEdge myEdge) {
                return myEdge.getWeight();
            }
        };

        MyGraphGenerator mgg = new MyGraphGenerator();

        //Uncomment one at a time and use different values to generate different
        //types of graphs
        sparseGraph = mgg.generateErdosGraph(100, 0.06);
        //sparseGraph = mgg.generateBarabasiGraph(10, 5, 1000);
        //sparseGraph = mgg.generateEppsteinGraph(1000, 3000, 30);

        //Create an arraylist of vertices to run path finding algorithms on
        ArrayList<MyVertex> myVertices = new ArrayList<>(sparseGraph.getVertices());
        System.out.println("Vertices in graph: " + sparseGraph.getVertexCount());
        System.out.println("Edges in graphL " + sparseGraph.getEdgeCount());


        //Uncomment block below to create a display of the graph
        /*
        Layout<MyVertex, MyEdge> layout = new ISOMLayout<>(sparseGraph);
        layout.setSize(new Dimension(900, 900));
        BasicVisualizationServer<MyVertex, MyEdge> vv = new BasicVisualizationServer<MyVertex, MyEdge>(layout);
        vv.setPreferredSize(new Dimension(900, 900));

        JFrame frame = new JFrame("Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
        */


        DijkstraShortestPath<MyVertex, MyEdge> alg = new DijkstraShortestPath(sparseGraph, vertexIntegerTransformer);
        PriorityDijkstra pd = new PriorityDijkstra(sparseGraph, vertexIntegerTransformer);
        AdjacencyDijkstra ad = new AdjacencyDijkstra(sparseGraph);
        StopWatch timer = new StopWatch();

        //For loop to run 30 times (to get a good mean) that puts the algorithm under the test
        //by making it check every node to every other node in the graph
        for (int i = 0; i < 30; i++) {

            timer.start();

            for (MyVertex myVertex : myVertices) {
                for (MyVertex myVertex1 : myVertices) {
                    List<MyEdge> l = alg.getPath(myVertex, myVertex1);
                }
            }
            timer.stop();
            System.out.println("Dijkstra Time: " + timer.getTime());
            timer.reset();
            timer.start();

            for (MyVertex myVertex : myVertices) {
                for (MyVertex myVertex1 : myVertices) {
                    ad.findShortestPath(myVertex, myVertex1);
                }
            }
            timer.stop();
            System.out.println("Adjacency Time: " + timer.getTime());
            timer.reset();
            timer.start();

            for (MyVertex myVertex : myVertices) {
                for (MyVertex myVertex1 : myVertices) {
                    List<MyVertex> b = pd.getShortestPathTo(myVertex, myVertex1);
                }
            }
            timer.stop();
            System.out.println("Priority Time: " + timer.getTime());
            timer.reset();
        }
    }
}
