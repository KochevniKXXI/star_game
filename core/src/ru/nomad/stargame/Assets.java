package ru.nomad.stargame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
    private static final Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    AssetManager assetManager;

    TextureAtlas mainAtlas;

    private Assets() {
        assetManager = new AssetManager();
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type) {
            case MENU:
            case GAME:
                assetManager.load("stargame.pack", TextureAtlas.class);
                assetManager.finishLoading();
                mainAtlas = assetManager.get("stargame.pack", TextureAtlas.class);
                break;
        }
    }
}
