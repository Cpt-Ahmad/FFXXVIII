package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import de.captain.ffxxviii.entity.components.Inventory;
import de.captain.ffxxviii.item.ItemStack;
import de.captain.ffxxviii.main.Assets;
import de.captain.ffxxviii.main.StateStacker;

public class InventoryMenu extends State
{
    //private final Inventory m_inventory;

    InventoryMenu(SpriteBatch batch, ShapeRenderer shapeRenderer, StateStacker stateStacker,
                  Inventory inventory)
    {
        super(batch, shapeRenderer, stateStacker);
        //m_inventory = inventory;

        final com.badlogic.gdx.scenes.scene2d.ui.List<ItemStack> list =
                new com.badlogic.gdx.scenes.scene2d.ui.List<>(Assets.getSkin());
        Array<ItemStack> invItems = new Array<>();
        for(ItemStack stack : inventory.getItemStacks())
        {
            invItems.add(stack);
        }
        list.setItems(invItems);

        final Label typeLabel = new Label("null", Assets.getSkin());
        final Label valueLabel = new Label("null", Assets.getSkin());
        m_guiHandler.add(list, new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                com.badlogic.gdx.scenes.scene2d.ui.List<ItemStack> listActor = (com.badlogic.gdx.scenes.scene2d.ui.List<ItemStack>) actor;
                ItemStack selected = listActor.getSelected();

                typeLabel.setText("Type: " + selected.item.type);
                valueLabel.setText("Value: " + selected.item.value);
            }
        }).pad(50f);
        m_guiHandler.add(typeLabel).pad(10f);
        m_guiHandler.add(valueLabel).pad(10f);
    }

    @Override
    public void update(float delta)
    {
        m_guiHandler.update(delta);
    }

    @Override
    public void render()
    {
        m_guiHandler.render();
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(new InputMultiplexer(m_guiHandler.getStage(), new InventoryMenuInputProcessor()));
    }

    @Override
    public void dispose()
    {

    }

    private class InventoryMenuInputProcessor extends InputAdapter
    {
        @Override
        public boolean keyDown(int keycode)
        {
            if(keycode == Input.Keys.ESCAPE)
            {
                m_stateStacker.pop();
                return true;
            }
            return false;
        }
    }
}
