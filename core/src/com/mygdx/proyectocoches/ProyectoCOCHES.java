package com.mygdx.proyectocoches;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.proyectocoches.screens.MainMenu;

public class ProyectoCOCHES extends Game {

	/**
	 * Called when the {@link Application} is first created.
	 */
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
