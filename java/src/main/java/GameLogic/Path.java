package GameLogic;

import java.awt.Point;
import java.util.ArrayList;


public final class Path {

    private final ArrayList<Point> points;

    public Path(Point[] Points){
        points = new ArrayList<Point>();
        for (Point point : Points) {
            this.points.add(point);
        }
    }

    @Override
    public String toString() {

        String toString = new String();
        for (Point point : points) {
            toString = toString + point + '\n';
        }
        return toString;
    }

    public Point getPosition(int index) {
        if (index >= 0 && index < points.size()) {
            return points.get(index);
        }
        return null;
    }

    public Point getFirst(){
        System.out.println(points.get(0));
        return points.get(0);
    }

    public int getLength(){
        return points.size();
    }


}

