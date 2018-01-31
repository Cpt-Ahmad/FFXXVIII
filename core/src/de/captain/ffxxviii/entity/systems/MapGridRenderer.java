package de.captain.ffxxviii.entity.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.Entity;

import java.util.List;

public class MapGridRenderer extends EntityRenderSystem{
    public MapGridRenderer() {
        super(EntitySystemType.MAP_GRID_RENDERER);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, List<Entity> entities) {
        for(float x = 0; x < 50; x++){
            for(float y = 0; y < 50; y++){
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.line((float) 0, y*32,(float) 3200, y*32);
                shapeRenderer.line(x*32,(float)0,x*32, (float)3200);
                shapeRenderer.end();
            }
        }
    }
}
