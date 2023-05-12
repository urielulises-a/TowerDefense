import java.awt.*;

public class Enemy {
    
    private Point position;
    private int health;
    private int speed;
    private int reward;
    private int currentPathIndex;
    private Path currentPath;
    
    public Enemy(Point position, int health, int speed, int reward, Path path) {
        this.position = position;
        this.health = health;
        this.speed = speed;
        this.reward = reward;
        this.currentPathIndex = 0;
        this.currentPath = path;
    }
    
    public void update() {
        if (currentPathIndex < currentPath.getLength()) {
            Point nextPosition = currentPath.getPosition(currentPathIndex);
            double distance = position.distance(nextPosition);
            if (distance <= speed) {
                position.setLocation(nextPosition);
                currentPathIndex++;
            } else {
                double dx = nextPosition.getX() - position.getX();
                double dy = nextPosition.getY() - position.getY();
                double angle = Math.atan2(dy, dx);
                int vx = (int) (Math.cos(angle) * speed);
                int vy = (int) (Math.sin(angle) * speed);
                position.translate(vx, vy);
            }
        }
    }
    
    public boolean isDead() {
        return health <= 0;
    }
    
    public void damage(int amount) {
        health -= amount;
    }
    
    public void setPosition(Point position) {
        this.position = position;
    }
    
    public Point getPosition() {
        return position;
    }
    
    public int getReward() {
        return reward;
    }

    public int getX() {
        return (int) position.getX();
    }

    public int getY() {
        return (int) position.getY();
    }
}
