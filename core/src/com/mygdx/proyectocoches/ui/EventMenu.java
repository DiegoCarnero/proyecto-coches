package com.mygdx.proyectocoches.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.proyectocoches.screens.LoadingScreen;
import com.mygdx.proyectocoches.screens.MainMenu;
import com.mygdx.proyectocoches.utils.GameSettings;

import java.util.ArrayList;

/**
 * Componentes de la pantalla selector de eventos
 */
public class EventMenu {

    /**
     * Conjunto de todos los elementos {@link Actor} de la interfaz en esta pantalla
     */
    private final ArrayList<Actor> compEvento = new ArrayList<>();
    /**
     * Texto denotando el selector de oponentes
     */
    private final Label lblOpos;
    /**
     * Texto denotando el selector de vueltas
     */
    private final Label lblVueltas;
    /**
     * Texto denotando el selector de modo de juego
     */
    private final Label lblModo;
    /**
     * Boton selector de circuito
     */
    private final Button btnCircuito;
    /**
     * Texto con el nombre del circuito
     */
    private final Label lblCircuito;
    /**
     * Boton selector de modo de juego
     */
    private final Button btnModo;
    /**
     * Texto mostrando el nombre del modo de juego
     */
    private final Label lblModoName;
    /**
     * Boton para retroceder al menu anterior
     */
    private final Button btnAtras;
    /**
     * Boton para comenzar la partida
     */
    private final Button btnJugar;
    /**
     * Texto denotando el boton para comenzar la partida
     */
    private final Label lblJugar;
    /**
     * Texto mostrando el numero de oponentes seleccionado
     */
    private final Label lblNumOpos;
    /**
     * Boton que incrementa el numero de oponentes
     */
    private final Button opoUp;
    /**
     * Boton que decrementa el numero de oponentes
     */
    private final Button opoDown;
    /**
     * Texto mostrando el numero de vueltas seleccionado
     */
    private final Label lblNumVueltas;
    /**
     * Boton que incrementa el numero de vueltas
     */
    private final Button vueltaUp;
    /**
     * Boton que decrementa el numero de vueltas
     */
    private final Button vueltaDown;
    /**
     * Nombre de los modos de juego localizados
     */
    private final String[] modos = new String[]{"carrera", "contrarreloj"};
    /**
     * Nombres de los circuitos para uso interno
     */
    private final String[] circuitos = new String[]{"test_loop", "track_1"};
    /**
     * Nombre de los circuitos localizados
     */
    private final String[] circuitoNames = new String[]{"Dirtona 500", "track_1"};
    /**
     * Si este menu se esta mostrando o no
     */
    private boolean showing;
    /**
     * Contador selector modo de juego
     */
    private int contModo = 0;
    /**
     * Contador selector de circuito
     */
    private int contCircuito = 0;
    /**
     * Numero de oponentes
     */
    private int numOpos = 4;
    /**
     * Numero de vueltas
     */
    private int numVueltas = 3;
    /**
     * Sprite del circuito seleccionado actualmente
     */
    private final Sprite s;
    /**
     * AssetManager con los archivos relevantas ya cargados
     */
    private final AssetManager am;
    /**
     * Alto de pantalla en pixeles
     */
    final int screenH = Gdx.graphics.getHeight();
    /**
     * Ancho de pantalla en pixeles
     */
    final int screenW = Gdx.graphics.getWidth();

    /**
     * Devuelve el sprite del circuito seleccionado actualmente
     *
     * @return sprite del circuito seleccionado
     */
    public Sprite getS() {
        return s;
    }

    /**
     * Componentes de la pantalla selector de eventos
     * <br>Este objeto tiene un btnAtras, pero la implementacion del InputListener depende de la pantalla donde se implementa.
     * Invocar getBackBtn() para añadirle un Listener
     *
     * @param skin   skin para los botones
     * @param g      basedel proyecto
     * @param am     AssetManager con los archivos relevantas ya cargados
     * @param locale bundle con los textos localizados
     * @param mm     menu principal donde se incorporan los componetes de este EventMenu
     */
    public EventMenu(final Skin skin, final Game g, final AssetManager am, final I18NBundle locale, final MainMenu mm) {
        this.am = am;
        s = new Sprite((Texture) am.get("worlds/test_loop_mini.png"));
        s.setPosition(screenW / 2f, 2 * screenH / 5f);
        s.setSize(screenH / 17f, screenH / 17f);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get("fonts/Cabin-Regular.ttf");

        modos[0] = locale.get("modos.carrera");
        modos[1] = locale.get("modos.contrarreloj");
        circuitoNames[0] = locale.get("circuitos.test_loop");
        circuitoNames[1] = locale.get("circuitos.track_1");

        btnCircuito = new TextButton("", skin);
        btnCircuito.setSize(screenW / 5f, screenH / 9f);
        btnCircuito.setPosition(screenW / 2f, -3 * screenH / 10f);
        btnCircuito.addListener(new ClickListener() {
            /**
             * Pasa a seleccionar el siguiente circuito de la lista
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                contCircuito = contCircuito == circuitos.length - 1 ? 0 : contCircuito + 1;
                s.set(new Sprite((Texture) am.get("worlds/" + circuitos[contCircuito] + "_mini.png")));
                s.setX(screenW / 2f);
                s.setY(2 * screenH / 5f);
                s.setSize(screenH / 7f, screenH / 7f);
                lblCircuito.setText(circuitoNames[contCircuito]);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnCircuito.setVisible(false);

        lblCircuito = new Label(circuitoNames[0], labelStyle);
        lblCircuito.setSize(screenW / 5f, screenH / 9f);
        lblCircuito.setAlignment(1);
        lblCircuito.setTouchable(Touchable.disabled);
        lblCircuito.setWrap(true);
        lblCircuito.setPosition(screenW / 2f, -3 * screenH / 10f);
        lblCircuito.setVisible(false);

        lblModo = new Label(locale.get("event.modo"), labelStyle);
        lblModo.setSize(screenH / 10f, screenH / 10f);
        lblModo.setPosition(screenW / 12f, screenH / 2f - 5 * screenH / 10f);
        lblModo.setAlignment(1);
        lblModo.setVisible(false);

        lblNumOpos = new Label(numOpos + "", labelStyle);
        lblNumOpos.setSize(screenH / 10f, screenH / 10f);
        lblNumOpos.setPosition(screenW / 12f + screenH / 10f, screenH / 2f - 2 * screenH / 10f);
        lblNumOpos.setAlignment(1);
        lblNumOpos.setVisible(false);

        opoUp = new TextButton(">", skin);
        opoUp.setSize(screenH / 10f, screenH / 10f);
        opoUp.setPosition(screenW / 12f + 2 * screenH / 10f, screenH / 2f - 2 * screenH / 10f);
        opoUp.addListener(new ClickListener() {
            /**
             * Incrementa el numero de oponentes
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numOpos = numOpos == 17 ? 0 : numOpos + 1;
                lblNumOpos.setText(numOpos);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        opoUp.setVisible(false);

        opoDown = new TextButton("<", skin);
        opoDown.setSize(screenH / 10f, screenH / 10f);
        opoDown.setPosition(screenW / 12f, screenH / 2f - 2 * screenH / 10f);
        opoDown.addListener(new ClickListener() {
            /**
             * Decrementa el numero de oponentes
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numOpos = numOpos == 1 ? 17 : numOpos - 1;
                lblNumOpos.setText(numOpos);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        opoDown.setVisible(false);

        lblNumVueltas = new Label(numVueltas + "", labelStyle);
        lblNumVueltas.setSize(screenH / 10f, screenH / 10f);
        lblNumVueltas.setPosition(screenW / 12f + screenH / 10f, screenH / 2f - 4 * screenH / 10f);
        lblNumVueltas.setVisible(false);
        lblNumVueltas.setAlignment(1);

        lblVueltas = new Label(locale.get("event.vueltas"), labelStyle);
        lblVueltas.setPosition(screenW / 12f, screenH / 2f - 3 * screenH / 10f);
        lblVueltas.setTouchable(Touchable.disabled);
        lblVueltas.setVisible(false);

        lblOpos = new Label(locale.get("event.opos"), labelStyle);
        lblOpos.setSize(screenH / 10f, screenH / 10f);
        lblOpos.setPosition(screenW / 12f, screenH / 2f - screenH / 10f);
        lblOpos.setTouchable(Touchable.disabled);
        lblOpos.setVisible(false);

        vueltaUp = new TextButton(">", skin);
        vueltaUp.setSize(screenH / 10f, screenH / 10f);
        vueltaUp.setPosition(screenW / 12f + 2 * screenH / 10f, screenH / 2f - 4 * screenH / 10f);
        vueltaUp.addListener(new ClickListener() {
            /**
             * Incrementa el numero de vueltas
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numVueltas = numVueltas == 9 ? 1 : numVueltas + 1;
                lblNumVueltas.setText(numVueltas);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        vueltaUp.setVisible(false);

        vueltaDown = new TextButton("<", skin);
        vueltaDown.setSize(screenH / 10f, screenH / 10f);
        vueltaDown.setPosition(screenW / 12f, screenH / 2f - 4 * screenH / 10f);
        vueltaDown.addListener(new ClickListener() {
            /**
             * Decrementa el numero de vueltas
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numVueltas = numVueltas == 1 ? 9 : numVueltas - 1;
                lblNumVueltas.setText(numVueltas);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        vueltaDown.setVisible(false);

        btnAtras = new TextButton("<", skin);
        btnAtras.setSize(screenH / 10f, screenH / 10f);
        btnAtras.setPosition(0, -screenH / 2f + screenH / 10f);
        btnAtras.setVisible(false);

        lblModoName = new Label(modos[contModo], labelStyle);
        lblModoName.setSize(screenW / 7f, screenH / 10f);
        lblModoName.setPosition(screenW / 12f, screenH / 2f - 6 * screenH / 10f);
        lblModoName.setTouchable(Touchable.disabled);
        lblModoName.setAlignment(1);
        lblModoName.setVisible(false);

        btnModo = new Button(skin);
        btnModo.setSize(screenW / 7f, screenH / 10f);
        btnModo.setPosition(screenW / 12f, screenH / 2f - 6 * screenH / 10f);
        btnModo.addListener(new ClickListener() {
            /**
             * Pasa a seleccionar el siguiente modo de juego de la lista
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                contModo = contModo == modos.length - 1 ? 0 : contModo + 1;
                lblModoName.setText(modos[contModo]);

                // deshabilitar si contrarreloj
                opoDown.setVisible(contModo != 1);
                opoUp.setVisible(contModo != 1);
                lblNumOpos.setVisible(contModo != 1);
                vueltaDown.setVisible(contModo != 1);
                vueltaUp.setVisible(contModo != 1);
                lblNumVueltas.setVisible(contModo != 1);
                lblOpos.setVisible(contModo != 1);
                lblVueltas.setVisible(contModo != 1);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnModo.setVisible(false);

        lblJugar = new Label(locale.get("event.empezar"), labelStyle);
        lblJugar.setAlignment(1);
        lblJugar.setSize(screenW / 10f, screenH / 10f);
        lblJugar.setPosition(screenW / 2f - screenW / 10f, -screenH / 2f);
        lblJugar.setVisible(false);
        lblJugar.setTouchable(Touchable.disabled);

        btnJugar = new TextButton("", skin);
        btnJugar.setSize(screenW / 10f, screenH / 10f);
        btnJugar.setPosition(screenW / 2f - screenW / 10f, -screenH / 2f);
        btnJugar.addListener(new ClickListener() {
            /**
             * Recoje los valores establecidos y lanza la pantalla de carga del evento
             *
             * @param event event
             * @param x x
             * @param y y
             * @param pointer pointer
             * @param button button
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameSettings gs = new GameSettings(numOpos, circuitos[contCircuito], contModo, numVueltas);
                setShowing(false);
                mm.paraMusica();
                g.setScreen(new LoadingScreen(am, g, skin, gs));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btnJugar.setVisible(false);

        compEvento.add(btnModo);
        compEvento.add(lblModoName);
        compEvento.add(opoUp);
        compEvento.add(opoDown);
        compEvento.add(btnAtras);
        compEvento.add(btnJugar);
        compEvento.add(lblJugar);
        compEvento.add(lblVueltas);
        compEvento.add(lblOpos);
        compEvento.add(btnCircuito);
        compEvento.add(lblCircuito);
        compEvento.add(lblNumOpos);
        compEvento.add(vueltaDown);
        compEvento.add(vueltaUp);
        compEvento.add(lblNumVueltas);
        compEvento.add(lblModo);

    }

    /**
     * Establece si esta pantalla ha de mostrarse o no
     *
     * @param showing 'true' si la pantalla se muestra, 'false' si no
     */
    public void setShowing(boolean showing) {
        this.showing = showing;
        contCircuito = 0;
        contModo = 0;
        s.set(new Sprite((Texture) am.get("worlds/" + circuitos[contCircuito] + "_mini.png")));
        s.setX(screenW / 2f);
        s.setY(2 * screenH / 5f);
        lblCircuito.setText(circuitoNames[contCircuito]);
        lblModoName.setText(modos[contModo]);
        for (Actor a : compEvento) {
            a.setVisible(showing);
        }

    }

    /**
     * Devuelve los componentes de este submenu
     *
     * @return lista con los componentes
     */
    public ArrayList<Actor> getCompEvento() {
        return compEvento;
    }

    /**
     * Devuelve el boton para retroceder al menu anterior
     *
     * @return btnAtras
     */
    public Actor getBackBtn() {
        return btnAtras;
    }

    /**
     * Si este menu se esta mostrando o no
     *
     * @return 'true' si la pantalla se muestra, 'false' si no
     */
    public boolean isShowing() {
        return showing;
    }
}
