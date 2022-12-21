package com.mygdx.proyectocoches;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.proyectocoches.ui.TestOsd;

public class ProyectoCOCHES extends Game {
	SpriteBatch batch;
	Texture img;
	TestOsd osd;
	
	@Override
	public void create () {
		setScreen(new TestOsd(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
