package de.captain.ffxxviii.entity.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.components.TextureContainer;
import de.captain.ffxxviii.main.WorldMap;

import java.util.List;

public class TextureRenderer extends EntityRenderSystem{
    public TextureRenderer() {
        super(EntitySystemType.TEXTURE_RENDERER);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer, List<Entity> entities) {
        TextureContainer textureContainer;
        RenderPosition renderPosition;
        GridPosition gridPosition;

        batch.begin();

        for(Entity entity:entities){
            textureContainer = entity.getComponent(TextureContainer.class);
            renderPosition = entity.getComponent(RenderPosition.class);
            gridPosition = entity.getComponent(GridPosition.class);

            if(textureContainer != null && renderPosition != null){
                batch.draw(textureContainer.texture, renderPosition.x,renderPosition.y);
            } else if(textureContainer != null && gridPosition != null){
                batch.draw(textureContainer.texture, gridPosition.x * WorldMap.getTileSize(), gridPosition.y * WorldMap.getTileSize());
            }

        }
        batch.end();
    }
}
