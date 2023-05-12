import java.awt.*;

public class Bullet {
    
    private Point position;
    private Enemy target;
    private int damage;
    private int speed;
    private boolean active;
    
    public Bullet(Point position, Enemy target, int damage) {
        this.position = position;
        this.target = target;
        this.damage = damage;
        this.speed = 10;
        this.active = true;
    }
    
    public void update() {
        if (active) {
            if (target.isDead()) {
                active = false;
            } else {
                Point targetPosition = target.getPosition();
                double dx = targetPosition.getX() - position.getX();
                double dy = targetPosition.getY() - position.getY();
                double distance = Math.sqrt(dx*dx + dy*dy);
                double angle = Math.atan2(dy, dx);
                int vx = (int) (Math.cos(angle) * speed);
                int vy = (int) (Math.sin(angle) * speed);
                if (distance <= speed) {
                    target.damage(damage);
                    active = false;
                } else {
                    position.translate(vx, vy);
                }
            }
        }
    }
    
    public boolean isActive() {
        return active;
    }
    
    public Point getPosition() {
        return position;
    }

    public boolean hasReachedTarget() {
        return position.equals(target.getPosition());
    }

    public Enemy getTarget() {
        return target;
    }
}
