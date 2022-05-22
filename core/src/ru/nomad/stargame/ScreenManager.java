package ru.nomad.stargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class ScreenManager {
    enum ScreenType {
        MENU, GAME
    }

    private static final ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private Game game;

    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    public void init(Game game) {
        this.game = game;
        this.menuScreen = new MenuScreen(((StarGame) game).batch);
        this.gameScreen = new GameScreen(((StarGame) game).batch);
    }

    public void switchScreen(ScreenType type) {
        Screen screen = game.getScreen();
        Assets.getInstance().assetManager.clear();
        Assets.getInstance().assetManager.dispose();
        Assets.getInstance().assetManager = new AssetManager();
        if (screen != null) {
            screen.dispose();
        }
        switch (type) {
            case MENU:
                Assets.getInstance().loadAssets(ScreenType.MENU);
//                game.setScreen(menuScreen);
                break;
            case GAME:
                Assets.getInstance().loadAssets(ScreenType.GAME);
//                game.setScreen(gameScreen);
                break;
        }
    }

    private ScreenManager() {

    }

    public void dispose() {

    }
}
