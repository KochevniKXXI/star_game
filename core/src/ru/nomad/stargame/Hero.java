package ru.nomad.stargame;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    Texture texture;
    Vector2 position;
    Vector2 velocity;
    float angle;

    int hp;
    int hpMax;

    float lowEnginePower;
    float currentEnginePower;
    float maxEnginePower;

    float rotationSpeed;

    float fireRate;
    float fireCounter;

    Circle hitArea;

    public Hero() {
        this.texture = new Texture("ship.png");
        this.position = new Vector2(640, 360);
        this.velocity = new Vector2(0, 0);
        this.maxEnginePower = 400.0f;
        this.lowEnginePower = 200.0f;
        this.rotationSpeed = 3.14f;
        this.hpMax = 100;
        this.hp = hpMax;
        this.hitArea = new Circle(position.x, position.y, 25);
        this.fireCounter = 0;
        this.fireRate = 0.25f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64, 1, 1, (float) toDegrees(angle), 0, 0, 64, 64, false, false);
    }

    public void update(float dt) {
        // На каждом кадре к кооринатам добавляется скорость и потихоньку гасится скорость
        position.mulAdd(velocity, dt);
        velocity.scl(0.97f);

        // Если игра работает на Android устройстве
        if (StarGame.isAndroid) {
            // При прикосновении к экрану включается двигатель
            if (InputHandler.isJustTouched()) {
                currentEnginePower = lowEnginePower;
            }
            // При дальнейшем нажатии
            if (InputHandler.isTouched()) {
                // Определяются координаты нажатия
                float tx = InputHandler.getX();
                float ty = InputHandler.getY();
                // Рассчитывается угол между кораблём и точкой нажатия
                float ang = (float) atan2(ty - position.y, tx - position.x);
                // Если угол до точки отличается от текущего угла корабля, старается развернуться в нужную сторону
                if (angle > ang) {
                    if (angle - ang < PI) {
                        angle -= rotationSpeed * dt;
                    } else {
                        angle += rotationSpeed * dt;
                    }
                }
                if (angle < ang) {
                    if (ang - angle < PI) {
                        angle += rotationSpeed * dt;
                    } else {
                        angle -= rotationSpeed * dt;
                    }
                }
                // Потихоньку увеличивается мощность двигателя
                currentEnginePower += 100 * dt;
                if (currentEnginePower > maxEnginePower) currentEnginePower = maxEnginePower;
                velocity.add((float) (currentEnginePower * cos(angle) * dt), (float) (currentEnginePower * sin(angle) * dt));
            }
        }

        // Если игра запущена на Desktop
        if (!StarGame.isAndroid) {
            // Всё управление реализуется на клавиатуре
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                currentEnginePower = lowEnginePower;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                currentEnginePower += 100 * dt;
                if (currentEnginePower > maxEnginePower) currentEnginePower = maxEnginePower;
                velocity.add((float) (currentEnginePower * cos(angle) * dt), (float) (currentEnginePower * sin(angle) * dt));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                angle += rotationSpeed * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                angle -= rotationSpeed * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                fireCounter += dt;
                if (fireCounter > fireRate) {
                    fireCounter = 0;
                    fire();
                }
            }
        }
        // Угол корабля держится в пределах от -PI до PI
        if (angle < -PI) angle += 2 * PI;
        if (angle > PI) angle -= 2 * PI;
        // Если корабль улетел за экран, то он перебрасыватся на другую сторону
        if (position.y > 752) position.y = -32;
        if (position.y < -32) position.y = 752;
        if (position.x > 1312) position.x = -32;
        if (position.x < -32) position.x = 1312;
        // Хитбокс перемещается за кораблём
        hitArea.x = position.x;
        hitArea.y = position.y;
    }

    // Метод, который выстреливает пулю
    public void fire() {
        for (Bullet bullet : BulletEmitter.getInstance().bullets) {
            if (!bullet.active) {
                bullet.setup(position.x, position.y, 400 * (float) cos(angle), 400 * (float) sin(angle));
                break;
            }
        }
    }
}
