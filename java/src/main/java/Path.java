import java.awt.Point;
import java.util.ArrayList;

public class Path {
    private ArrayList<Point> points;
    private int length;
    
    public Path() {
        points = new ArrayList<Point>();
        length = 0;
    }
    
    public void addPoint(Point point) {
        points.add(point);
        length++;
    }
    
    public Point getPosition(int index) {
        return points.get(index);
    }
    
    public int getLength() {
        return length;
    }
}
