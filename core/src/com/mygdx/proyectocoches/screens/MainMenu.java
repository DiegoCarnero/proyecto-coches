package com.mygdx.proyectocoches.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.proyectocoches.ui.CreditsScreen;
import com.mygdx.proyectocoches.ui.EventMenu;
import com.mygdx.proyectocoches.ui.RecordsMenu;
import com.mygdx.proyectocoches.ui.SettingsMenu;
import com.mygdx.proyectocoches.ui.TutorialMenu;

import java.util.ArrayList;

public class MainMenu implements Screen {

    private final ArrayList<Actor> compMain = new ArrayList<>();
    private final Stage stage;
    private final InputMultiplexer multiplexer;
    private final AssetManager am;
    private final RecordsMenu mRecords;
    private final EventMenu mEvento;
    private final SettingsMenu mSettings;
    private final TutorialMenu mTutorial;
    private final CreditsScreen credits;
    private final SpriteBatch batch;
    private final Sprite bg;
    private final int screenW, screenH;

    public MainMenu(final Game miGame) {

        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        am = new AssetManager();
        am.load("worlds/track_1_mini.png", Texture.class);
        am.load("worlds/test_loop_mini.png", Texture.class);
        am.load("badlogic.jpg", Texture.class);
        am.load("locale/locale", I18NBundle.class);
        am.load("data/carbon_fiber_bg.png",Texture.class);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        am.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        am.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));
        am.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        String[] fuentes = new String[]{"fonts/Designer.otf", "fonts/Cabin-Regular.ttf"};
        int[] fontSizes = new int[]{(int) (0.041667 * screenH), (int) (0.041667 * screenH)};
        for (int i = 0; i < fuentes.length; i++) {

            FreetypeFontLoader.FreeTypeFontLoaderParameter param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
            param.fontFileName = fuentes[i];
            param.fontParameters.characters = "\u0000AÁBCDEÉFGHIÍJKLMNÑOÓÖŐPQRSTUÚÜŰVWXYZaábcdeéfghiíjklmnñoóöőpqrstuúüűvwxyz1234567890\\\"¡!`¿?\\'.,;:()[]{}<>|/@\\\\^$\\u20ac-%+=º#_&~*\\u007f\\u0080\\u0081\\u0082\\u0083\\u0084\\u0085\\u0086\\u0087\\u0088\\u0089\\u008a\\u008b\\u008c\\u008d\\u008e\\u008f\\u0090\\u0091\\u0092\\u0093\\u0094\\u0095\\u0096\\u0097\\u0098\\u0099\\u009a\\u009b\\u009c\\u009d\\u009e\\u009f\\u00a0\\u00a1\\u00a2\\u00a3\\u00a4\\u00a5\\u00a6\\u00a7\\u00a8\\u00a9\\u00aa\\u00ab\\u00ac\\u00ad\\u00ae\\u00af\\u00b0\\u00b1\\u00b2\\u00b3\\u00b4\\u00b5\\u00b6\\u00b7\\u00b8\\u00b9\\u00ba\\u00bb\\u00bc\\u00bd\\u00be\\u00bf\\u00c0\\u00c1\\u00c2\\u00c3\\u00c4\\u00c5\\u00c6\\u00c7\\u00c8\\u00c9\\u00ca\\u00cb\\u00cc\\u00cd\\u00ce\\u00cf\\u00d0\\u00d1\\u00d2\\u00d3\\u00d4\\u00d5\\u00d6\\u00d7\\u00d8\\u00d9\\u00da\\u00db\\u00dc\\u00dd\\u00de\\u00df\\u00e0\\u00e1\\u00e2\\u00e3\\u00e4\\u00e5\\u00e6\\u00e7\\u00e8\\u00e9\\u00ea\\u00eb\\u00ec\\u00ed\\u00ee\\u00ef\\u00f0\\u00f1\\u00f2\\u00f3\\u00f4\\u00f5\\u00f6\\u00f7\\u00f8\\u00f9\\u00fa\\u00fb\\u00fc\\u00fd\\u00fe\\u00ff\"";
            param.fontParameters.size = fontSizes[i];
            param.fontParameters.color = Color.WHITE;
            param.fontParameters.borderColor = Color.BLACK;
            param.fontParameters.borderWidth = 2.0f;

            am.load(fuentes[i], BitmapFont.class, param);

        }

        am.finishLoading();
        bg = new Sprite((Texture) am.get("data/carbon_fiber_bg.png"));
        I18NBundle locale = am.get("locale/locale");

        stage = new Stage(new ScreenViewport());

        int vertOffset = 0;

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        stage.getViewport().getCamera().position.set(screenW / 2f, 0, 0);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = am.get(fuentes[1]);

        this.mEvento = new EventMenu(skin, miGame, am, locale);
        Label lbl1 = new Label(locale.get("mainmenu.evento"), labelStyle);
        lbl1.setTouchable(Touchable.disabled);
        lbl1.setAlignment(1);
        lbl1.setSize(screenW / 10f, screenH / 10f);
        lbl1.setPosition(screenW / 4f - screenW / 20f, -screenH / 10f);
        Button btn1 = new Button(skin);
        btn1.setSize(screenW / 10f, screenH / 10f);
        btn1.setPosition(screenW / 4f - screenW / 20f, -screenH / 10f);
        btn1.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                for (Actor a : compMain) {
                    a.setVisible(false);
                }
                mEvento.setShowing(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        vertOffset++;

        this.mRecords = new RecordsMenu("", skin);
        Label lbl2 = new Label(locale.get("mainmenu.records"), labelStyle);
        lbl2.setTouchable(Touchable.disabled);
        lbl2.setAlignment(1);
        lbl2.setSize(screenW / 10f, screenH / 10f);
        lbl2.setPosition(3 * screenW / 4f - screenW / 20f, -screenH / 10f);
        Button btn2 = new Button(skin);
        btn2.setSize(screenW / 10f, screenH / 10f);
        btn2.setPosition(3 * screenW / 4f - screenW / 20f, -screenH / 10f);
        btn2.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compMain) {
                    a.setVisible(false);
                }
                mRecords.setShowing(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        vertOffset += 2;

        this.mSettings = new SettingsMenu(skin, am, locale);
        Label lbl3 = new Label(locale.get("mainmenu.ajustes"), labelStyle);
        lbl3.setTouchable(Touchable.disabled);
        lbl3.setAlignment(1);
        lbl3.setSize(screenW / 10f, screenH / 10f);
        lbl3.setPosition(screenW / 4f - screenW / 20f, -vertOffset * screenH / 10f);
        Button btn3 = new Button(skin);
        btn3.setSize(screenW / 10f, screenH / 10f);
        btn3.setPosition(screenW / 4f - screenW / 20f, -vertOffset * screenH / 10f);
        btn3.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                for (Actor a : compMain) {
                    a.setVisible(false);
                }
                mSettings.setShowing(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        this.mTutorial = new TutorialMenu(skin, am);
        Label lbl4 = new Label(locale.get("mainmenu.tutoriales"), labelStyle);
        lbl4.setTouchable(Touchable.disabled);
        lbl4.setAlignment(1);
        lbl4.setSize(screenW / 10f, screenH / 10f);
        lbl4.setPosition(3 * screenW / 4f - screenW / 20f, -vertOffset * screenH / 10f);
        Button btn4 = new Button(skin);
        btn4.setSize(screenW / 10f, screenH / 10f);
        btn4.setPosition(3 * screenW / 4f - screenW / 20f, -vertOffset * screenH / 10f);
        btn4.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compMain) {
                    a.setVisible(false);
                }
                mTutorial.setShowing(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        vertOffset++;

        this.credits = new CreditsScreen(skin);
        Label lbl5 = new Label(locale.get("mainmenu.creditos"), labelStyle);
        lbl5.setTouchable(Touchable.disabled);
        lbl5.setAlignment(1);
        lbl5.setSize(screenW / 10f, screenH / 10f);
        lbl5.setPosition(screenW / 2f - screenW / 20f, -vertOffset * screenH / 10f);
        Button btn5 = new Button(skin);
        btn5.setSize(screenW / 10f, screenH / 10f);
        btn5.setPosition(screenW / 2f - screenW / 20f, -vertOffset * screenH / 10f);
        btn5.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compMain) {
                    a.setVisible(false);
                }
                credits.setShowing(true);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        compMain.add(btn1);
        compMain.add(btn2);
        compMain.add(btn3);
        compMain.add(btn4);
        compMain.add(btn5);
        compMain.add(lbl1);
        compMain.add(lbl2);
        compMain.add(lbl3);
        compMain.add(lbl4);
        compMain.add(lbl5);
        compMain.addAll(mRecords.getCompRecords());
        compMain.addAll(mEvento.getCompEvento());
        compMain.addAll(mTutorial.getCompTutorial());
        compMain.addAll(mSettings.getCompSettings());
        compMain.addAll(credits.getCompCredits());

        ClickListener backBtnList = new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a : compMain) {
                    a.setVisible(true);
                }
                mEvento.setShowing(false);
                mTutorial.setShowing(false);
                mRecords.setShowing(false);
                mSettings.setShowing(false);
                credits.setShowing(false);
                super.touchUp(event, x, y, pointer, button);
            }
        };

        mRecords.getBackBtn().addListener(backBtnList);
        mTutorial.getBackBtn().addListener(backBtnList);
        mEvento.getBackBtn().addListener(backBtnList);
        mSettings.getBackBtn().addListener(backBtnList);
        credits.getBackBtn().addListener(backBtnList);

        for (Actor a : compMain) {
            stage.addActor(a);
        }
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (am.update()) {
            batch.begin();
            batch.draw(bg,0,0,screenW,screenH);
            if (mEvento.isShowing()) {
                Sprite s = mEvento.getS();
                batch.draw(s, s.getX(), s.getY(),screenH / 7f, screenH / 7f);
            } else if (mTutorial.isShowing()) {
                Sprite s = mTutorial.getS();
                batch.draw(s, s.getX(), s.getY(), screenH / 1.5f, screenH / 1.5f);
            }
            batch.end();

            stage.act();
            stage.draw();
        }
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     *
     */
    @Override
    public void pause() {

    }

    /**
     *
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
        am.dispose();
    }
}

