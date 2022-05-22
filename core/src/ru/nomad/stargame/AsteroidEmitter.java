package ru.nomad.stargame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class AsteroidEmitter {
    private static final AsteroidEmitter ourInstance = new AsteroidEmitter();

    public static AsteroidEmitter getInstance() {
        return ourInstance;
    }

    ArrayList<Asteroid> asteroids;

    private AsteroidEmitter() {
        asteroids = new ArrayList<>();
    }

    public void addAsteroid(Vector2 position, Vector2 velocity, float scl, int hpMax) {
        asteroids.add(new Asteroid(position, velocity, scl, hpMax));
    }

    public void update(float dt) {
        for (Asteroid asteroid : asteroids) {
            asteroid.update(dt);
        }
    }

    public void render(SpriteBatch batch) {
        for (Asteroid asteroid : asteroids) {
            if (!asteroid.takeDamage(0)) {
                asteroid.render(batch);
            }
        }
    }
}
