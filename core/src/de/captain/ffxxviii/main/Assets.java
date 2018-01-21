package de.captain.ffxxviii.main;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

    public class Assets {

        private static Assets assets = new Assets();
        private AssetManager assetManager = new AssetManager();

        private Assets(){
        }

        public void init()
        {
            for(Asset asset : Asset.values())
            {
                assetManager.load(asset.path, asset.assetClass);
            }
            assetManager.finishLoading();
        }

        public Texture getTexture(Asset asset){
            return assetManager.get(asset.path);
        }

        public static Assets getAssets(){
            return assets;
        }
    }

