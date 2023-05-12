import java.awt.Point;

public class Tile {
    
    private Point position;
    private boolean walkable;
    private Tower tower;
    private Enemy enemy;
    
    public Tile(Point position, boolean walkable) {
        this.position = position;
        this.walkable = walkable;
        this.tower = null;
        this.enemy = null;
    }
    
    public Point getPosition() {
        return position;
    }
    
    public boolean isWalkable() {
        return walkable;
    }
    
    public Tower getTower() {
        return tower;
    }
    
    public void setTower(Tower tower) {
        this.tower = tower;
    }
    
    public Enemy getEnemy() {
        return enemy;
    }
    
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
    
    public boolean isEmpty() {
        return (tower == null && enemy == null);
    }
}
