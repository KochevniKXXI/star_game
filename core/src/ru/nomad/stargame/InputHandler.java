package ru.nomad.stargame;

import com.badlogic.gdx.Gdx;

public class InputHandler {
    public static boolean isTouched() {
        return Gdx.input.isTouched();
    }

    public static boolean isJustTouched() {
        return Gdx.input.justTouched();
    }

    public static float getX() {
        return Gdx.input.getX();
    }

    public static float getY() {
        return Gdx.graphics.getHeight() - Gdx.input.getY();
    }
}
