package ru.nomad.stargame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Background {
    class Star {
        Vector2 position;
        Vector2 velocity;
        float scl;
        Texture textureStar;

        public Star() {
            position = new Vector2((float) Math.random() * 1280, (float) Math.random() * 720);
            velocity = new Vector2((float) (Math.random() - 0.5) * 5f, (float) (Math.random() - 0.5) * 5f);
            scl = 0.5f + (float) Math.random() / 4.0f;
            this.textureStar = Background.this.texturesStars.get((int) (Math.random() * 15));
        }

        public void update(Hero hero, float dt) {
            position.mulAdd(velocity, dt);
            position.mulAdd(hero.velocity, -0.001f);
            float half = textureStar.getWidth() * scl;
            if (position.x < -half) position.x = 1280 + half;
            if (position.x > 1280 + half) position.x = -half;
            if (position.y < -half) position.y = 720 + half;
            if (position.y > 720 + half) position.y = -half;
        }
    }

    Texture texture;
    ArrayList<Texture> texturesStars = new ArrayList<>();
    Star[] stars;

    public Background() {
//        texture = new Texture("bg.png");
        for (int i = 1; i <= 16; i++) {
            texturesStars.add(new Texture("star" + i + ".png"));
        }
//        texturesStars = new Texture("star.png");
        stars = new Star[250];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
    }

    public void render(SpriteBatch batch) {
//        batch.draw(texture, 0, 0);
        for (Star star : stars) {
            batch.draw(star.textureStar, star.position.x - 8, star.position.y - 8, 8, 8, 16, 16, star.scl, star.scl, 0, 0, 0, 16, 16, false, false);
        }
    }

    public void update(Hero hero, float dt) {
        for (Star star : stars) {
            star.update(hero, dt);
        }
    }

    public void dispose() {
//        texture.dispose();
        for (Texture textureSt : texturesStars) {
            textureSt.dispose();
        }
//        texturesStars.dispose();
    }
}
