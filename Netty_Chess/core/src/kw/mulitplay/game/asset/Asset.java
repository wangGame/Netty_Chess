package kw.mulitplay.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;

import kw.mulitplay.game.ChessGame;

public class Asset {
    public static Batch batch;
    public static Viewport viewport;
    public static AssetManager assetManager;
    public static ChessGame ChessGame;
    public int i = 0;
    private static Asset asset;


    public static Asset getInstance(){
        if (asset == null){
            asset = new Asset();
        }
        return asset;
    }

    private Asset(){
        if (i!=0)throw new RuntimeException("gun");
        i++;
        assetManager = new AssetManager();
    }

    public static void dispose() {
        batch = null;
        viewport = null;
        asset = null;
    }

    public Texture getTexture(String path) {
        assetManager.load(path,Texture.class);
        assetManager.finishLoading();
        return assetManager.get(path);
    }
}
