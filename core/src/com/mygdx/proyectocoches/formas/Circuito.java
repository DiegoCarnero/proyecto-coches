package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_MUROS;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_JUG;
import static com.mygdx.proyectocoches.Constantes.LAYER_MUROS;
import static com.mygdx.proyectocoches.Constantes.TILE_SIZE;
import static com.mygdx.proyectocoches.Constantes.test_loop_ang;
import static com.mygdx.proyectocoches.Constantes.test_loop_vGrid;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Circuito {

    private ArrayList<Body> competidores;
    private ArrayList<Body> circuitoMuros;
    private String nomCircuito;
    private World mundo;

    public Circuito(World mundo, String nomCircuito) {
        this.mundo = mundo;
        this.circuitoMuros = new ArrayList<>();
        this.competidores = new ArrayList<>();
        this.nomCircuito = nomCircuito;
    }

    private Body getPolygonCircuito(PolygonMapObject p) {
        float[] vertices;
        vertices = p.getPolygon().getTransformedVertices();
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = vertices[i] / TILE_SIZE;
        }

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = mundo.createBody(bdef);

        // TODO probar con todos los vertices en un solo body en lugar de un ArrayList

        PolygonShape pShape = new PolygonShape();
        pShape.set(vertices);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = pShape;
        fDef.density = 1;
        fDef.filter.categoryBits = CAT_CIRCUITO_MUROS;
        fDef.filter.maskBits = CAT_COCHE_JUG;
        body.createFixture(fDef);
        pShape.dispose();
        return body;
    }

    // tiene que estar formado por poligonos exclusivamente
    // Box2D no permite formas concavas
    // tambien falla con rectangulos con las esquinas redondeadas
    public void Cargar() {

        TiledMap miTiledMap = new TmxMapLoader().load("worlds/" + nomCircuito + ".tmx");
        MapObjects murosMapObjs = miTiledMap.getLayers().get(LAYER_MUROS).getObjects();

        for (MapObject o : murosMapObjs) {
            if (o instanceof PolygonMapObject) {
                circuitoMuros.add(getPolygonCircuito((PolygonMapObject) o));
            }
        }

        miTiledMap.dispose();
    }

    public Body prepararParrilla(int oponentes, int posJug) {

        Body jugador = null;
        int maxOponentes = 0;
        Vector2[] vGrid;
        float angulo;

        switch (nomCircuito) {
            case "test_loop":
                vGrid = test_loop_vGrid;
                angulo = test_loop_ang;
                break;
            default:
                vGrid = test_loop_vGrid;
                angulo = 0;
        }

        if (maxOponentes < oponentes) {
            oponentes = maxOponentes;
        }

        Vector2 tamCoche = new Vector2(5, 10);
        boolean jugInit = false;
        int cont = 0;

        for (Vector2 v : vGrid) {
            if (cont > oponentes) {
                break;
            }

            if (cont == posJug) {
                jugador = Coche.generaCoche(v, mundo, tamCoche);
                jugador.setTransform(v, (float) angulo);
                competidores.add(jugador);
                jugInit = true;
            } else {
                Body c = Coche.generaCoche(v, mundo, tamCoche);
                c.setTransform(v, (float) -(angulo * Math.PI / 180));
                competidores.add(c);
            }
            cont++;
        }

        if (!jugInit) {
            jugador = Coche.generaCoche(vGrid[vGrid.length-1], mundo, tamCoche);
            jugador.setTransform(jugador.getPosition(), (float) -(angulo * Math.PI / 180));
        }

        return jugador;
    }

}
