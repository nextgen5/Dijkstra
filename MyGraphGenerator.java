import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.collections15.Factory;

import java.util.HashSet;

public class MyGraphGenerator {

    private HashSet<MyVertex> seedVertices;
    private BarabasiAlbertGenerator<MyVertex, MyEdge> bag;
    private ErdosRenyiGenerator<MyVertex, MyEdge> erg;
    private EppsteinPowerLawGenerator<MyVertex, MyEdge> epg;

    public MyGraphGenerator() {
        seedVertices = new HashSet<>();
    }

    public Graph<MyVertex, MyEdge> generateEppsteinGraph(int vertices, int numEdges, int r) {
        epg = new EppsteinPowerLawGenerator<>(sparseGraphFactory, vertexFactory, edgeFactory, vertices, numEdges, r);
        return epg.create();
    }

    public Graph<MyVertex, MyEdge> generateBarabasiGraph(int vertices, int numEdges, int numTimeSteps) {
        bag = new BarabasiAlbertGenerator<>(sparseGraphFactory, vertexFactory, edgeFactory, vertices, numEdges, seedVertices);
        bag.evolveGraph(numTimeSteps);
        return bag.create();

    }

    public Graph<MyVertex, MyEdge> generateErdosGraph(int vertices, double probability) {
        erg = new ErdosRenyiGenerator<>(undirectedGraphFactory, vertexFactory, edgeFactory, vertices, probability);
        return erg.create();
    }

    Factory<Graph<MyVertex, MyEdge>> sparseGraphFactory = new Factory<Graph<MyVertex, MyEdge>>() {
        @Override
        public SparseGraph<MyVertex, MyEdge> create() {
            return new SparseGraph<>();
        }
    };

    Factory<UndirectedGraph<MyVertex, MyEdge>> undirectedGraphFactory = new Factory<UndirectedGraph<MyVertex, MyEdge>>() {
        @Override
        public UndirectedGraph<MyVertex, MyEdge> create() {
            return new UndirectedSparseGraph<>();
        }
    };

    Factory<MyVertex> vertexFactory = new Factory<MyVertex>() {
        int count;
        @Override
        public MyVertex create() {
            return new MyVertex(count++);
        }
    };

    Factory<MyEdge> edgeFactory = new Factory<MyEdge>() {
        @Override
        public MyEdge create() {
            return new MyEdge();
        }
    };

}
