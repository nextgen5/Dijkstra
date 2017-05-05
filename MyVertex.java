public class MyVertex implements Comparable<MyVertex> {

    private int id;
    private Integer minDistance;
    private MyVertex previous;

    public MyVertex(int id) {
        this.id = id;
        this.minDistance = 0;
    }

    @Override
    public String toString() {
        return "V: " + id;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyVertex getPrevious() {
        return previous;
    }

    public void setPrevious(MyVertex previous) {
        this.previous = previous;
    }

    @Override
    public int compareTo(MyVertex v) {
        return this.minDistance.compareTo(v.getMinDistance());
    }
}
