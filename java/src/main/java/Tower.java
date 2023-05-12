import java.awt.Point;
import java.util.Map;

public class Tower {
    private int x, y, range, damage, attackSpeed, attackCooldown;
    private Enemy target;

    public Tower(int x, int y, int range, int attackPower, int attackSpeed) {
        this.x = x;
        this.y = y;
        this.range = range;
        this.damage = attackPower;
        this.attackSpeed = attackSpeed;
        this.attackCooldown = 0;
        this.target = null;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public void update() {
        if (target == null) {
            findTarget();
        } else {
            if (isInRange(target)) {
                attack();
            } else {
                target = null;
            }
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }
    }

    private void findTarget() {
        Enemy closestEnemy = null;
        double closestDistance = Double.POSITIVE_INFINITY;
        for (Enemy enemy : map.getEnemies()) {
            double distance = getDistance(enemy.getPosition(), getPosition());
            if (distance < closestDistance && distance < range) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }
        target = closestEnemy;
    }

    private boolean isInRange(Enemy enemy) {
        double distance = getDistance(enemy.getPosition(), getPosition());
        return distance <= range;
    }

    private void attack() {
        if (attackCooldown == 0) {
            Bullet bullet = new Bullet(getPosition(), target, damage);
            map.addBullet(bullet);
            attackCooldown = attackSpeed;
        }
    }

    private double getDistance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private Point getPosition() {
        return new Point(x, y);
    }
}
