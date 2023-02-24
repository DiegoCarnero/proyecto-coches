package com.mygdx.proyectocoches;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.proyectocoches.screens.MainMenu;
import com.mygdx.proyectocoches.screens.ScreenSelector;
import com.mygdx.proyectocoches.screens.TestDrive;
import com.mygdx.proyectocoches.screens.TestIA;
import com.mygdx.proyectocoches.ui.TestOsd;

public class ProyectoCOCHES extends Game {
	
	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
