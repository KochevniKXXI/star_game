package ru.nomad.stargame;

import com.badlogic.gdx.assets.AssetManager;

public class Glob {
    private static final Glob ourInstance = new Glob();

    public static Glob getInstance() {
        return ourInstance;
    }

    public AssetManager assetManager() {
        return new AssetManager();
    }
}
