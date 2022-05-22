package ru.nomad.stargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends Game {
    public static boolean isAndroid = false;
	SpriteBatch batch;
    Background background;
    Hero hero;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ScreenManager.getInstance().init(this);
		ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MENU);
        background = new Background();
        hero = new Hero();
        for (int i = 0; i < 4; i++) {
            AsteroidEmitter.getInstance().addAsteroid(new Vector2((float) Math.random() * 1280, (float) Math.random() * 720), new Vector2((float) (Math.random() - 0.5) * 200, (float) (Math.random() - 0.5) * 200), 1.0f, 100);
        }
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0.1f, 0.2f, 0.45f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.render(batch);
		hero.render(batch);
		AsteroidEmitter.getInstance().render(batch);
		BulletEmitter.getInstance().render(batch);
		batch.end();
	}

	public void update(float dt) {
		background.update(hero, dt);
		hero.update(dt);
		AsteroidEmitter.getInstance().update(dt);
		BulletEmitter.getInstance().update(dt);
		checkCollision();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}

	// Метод checkCollision занимется проверко столкновений. Первый цикл отвечает за столкновения игрока с астероидами,
	// второй - пуль с астероидами. Проверка осуществляктся за счёт сравнения окружностей (построенных вокруг объектов),
	// если две окружности пересекаются, значит столкновение есть.
	public void checkCollision() {
		for (Asteroid asteroid : AsteroidEmitter.getInstance().asteroids) {
			if (hero.hitArea.overlaps(asteroid.hitArea)) {
				Vector2 acc = hero.position.cpy().sub(asteroid.position).nor();
				hero.velocity.mulAdd(acc,20);
				asteroid.velocity.mulAdd(acc, -20);
			}
		}
		for (Bullet bullet : BulletEmitter.getInstance().activeBullets) {
			for (Asteroid asteroid : AsteroidEmitter.getInstance().asteroids) {
				if (asteroid.hitArea.contains(bullet.position)) {
					if (asteroid.takeDamage(50)) {
						AsteroidEmitter.getInstance().asteroids.remove(asteroid);
						break;
					}
					bullet.destroy();
				}
			}
		}
	}
}
