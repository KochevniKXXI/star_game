package ru.nomad.stargame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Bullet implements Pool.Poolable {
    Vector2 position;
    Vector2 velocity;
    boolean active;

    public Bullet() {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.active = false;
    }

    public void setup(float x, float y, float vx, float vy) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
    }

    public void destroy() {
        active = false;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < -20 || position.x > 1300 || position.y < -20 || position.y > 740) {
            destroy();
        }
    }

    @Override
    public void reset() {
        active = false;
        position.set(0, 0);
    }
}
