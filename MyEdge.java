import java.util.Random;

public class MyEdge {

    private int weight;
    private Random random;

    public MyEdge() {
        this.random = new Random();
        this.weight = random.nextInt(100) + 1;
    }



    public int getWeight() {
        return weight;
    }
}
