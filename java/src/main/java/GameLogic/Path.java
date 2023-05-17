package GameLogic;

import java.awt.Point;
import java.util.ArrayList;


public class Path {
    private ArrayList<Point> points;

    public Path() {
        points = new ArrayList<>();
    }

    public Path(Point[] Points){
        for (Point point : Points) {
            this.points.add(point);
        }
    }
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public void addPoint(Point pointToAdd){
        points.add(pointToAdd);
    }

    public int getLength() {
        return points.size();
    }

    public Point getPosition(int index) {
        if (index >= 0 && index < points.size()) {
            return points.get(index);
        }
        return null;
    }


}

