package com.mygdx.proyectocoches.test;

/**
 * AI code by Conner Anderson https://youtu.be/pnKcuJQT31A
 */

import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AiSteeringState implements Screen {

    World world;
    Box2DDebugRenderer b2dr;
    B2dSteeringEntity entity, target;
    private final OrthographicCamera camera;
    private final Viewport miViewport;
    private final AiTestOsd osd;
    private final SpriteBatch miBatch;

    public AiSteeringState(Game juego, Skin skin) {
        this.miBatch = new SpriteBatch();
        // Init b2d world
        b2dr = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), false);

        osd = new AiTestOsd(juego, skin);
        // Create following entity
        Body body = createCircle(world, 0, 50, 10, false, true);
        entity = new B2dSteeringEntity(body, 30);
        entity.setMaxLinearSpeed(500);
        entity.setMaxLinearAcceleration(5000);
        entity.setMaxAngularAcceleration(5);
        entity.setMaxAngularSpeed(5000);

        this.camera = new OrthographicCamera();
        this.camera.zoom = 30f;

        this.miViewport = new FitViewport(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM, camera);
        // Create "player" entity
        body = createCircle(world, 0, 0, 10, false, true);
        target = new B2dSteeringEntity(body, 30);
        // Make walls
        createBox(world, -200, 200, 60, 20, true, false);
        createBox(world, 140, 200, 60, 20, true, false);
        createBox(world, -200, -200, 60, 20, true, false);
        createBox(world, 140, -200, 60, 20, true, false);

        // Create temporary behavior
        Arrive<Vector2> arriveSB = new Arrive<Vector2>(entity, target)
                .setTimeToTarget(0.010f)
                .setArrivalTolerance(2f)
                .setDecelerationRadius(10);
        entity.setBehavior(arriveSB);
        entity.getBody().setAwake(true);
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        // Poll input
        int x = 0, y = 0;
        if (osd.isAcelerando()) {
            x += 1;
        } else {
            x -= 1;
        }
        if (osd.isAdelante()) {
            y += 1;
        } else {
            y -= 1;
        }

        // Check if a key was pressed for movement
        if (x != 0) {
            Vector2 vel = target.getBody().getLinearVelocity();
            target.getBody().setLinearVelocity((x * 1), vel.y);
        }
        if (y != 0) {
            Vector2 vel = target.getBody().getLinearVelocity();
            target.getBody().setLinearVelocity(vel.x, (y * 1));
        }

        entity.update(delta);

        lerpToTarget(camera, target.getPosition().scl(PPM));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(osd.getMultiplexer());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        draw();
        osd.render(delta);
        b2dr.render(world, camera.combined.cpy().scl(PPM));
        update(delta);
    }

    private void draw() {
        miBatch.setProjectionMatrix(camera.combined);
        b2dr.render(world, camera.combined);
    }


    @Override
    public void resize(int width, int height) {
        miViewport.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
    }

    public Body createCircle(final World world, float x, float y, float r,
                             boolean isStatic, boolean canRotate) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.linearDamping = 10f;
        bodyDef.position.set(x / PPM, y / PPM);

        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(r / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .95f;
        fixtureDef.density = 1.0f;

        return world.createBody(bodyDef).

                createFixture(fixtureDef).

                getBody();

    }

    public Body createBox(final World world, float x, float y, float w, float h,
                          boolean isStatic, boolean canRotate) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = canRotate;
        bodyDef.linearDamping = 10f;
        bodyDef.position.set(x / PPM, y / PPM);

        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / PPM, h / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

        return world.createBody(bodyDef).createFixture(fixtureDef).getBody();
    }


    public void lerpToTarget(Camera camera, Vector2 target) {

        // a + (b - a) * lesp factor
        Vector3 position = camera.position;
        position.x = camera.position.x + (target.x - camera.position.x) * .1f;
        position.y = camera.position.y + (target.y - camera.position.y) * .1f;
        camera.position.set(position);
        camera.update();
    }

    public void markAsSensor(B2dSteeringEntity steerableBody) {
        Array<Fixture> fixtures = steerableBody.getBody().getFixtureList();
        for (int i = 0; i < fixtures.size; i++) {
            fixtures.get(i).setSensor(true);
        }
    }
}