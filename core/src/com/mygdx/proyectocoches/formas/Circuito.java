package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.LAYER_GRID;
import static com.mygdx.proyectocoches.Constantes.LAYER_MUROS;
import static com.mygdx.proyectocoches.Constantes.TILE_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Circuito {

    private ArrayList<Body> coches;
    private ArrayList<Body> circuitoMuros;
    private String pathArchivoCircuito;
    private World mundo;

    public Circuito(World mundo, String path) {
        this.mundo = mundo;
        this.circuitoMuros = new ArrayList<>();
        this.pathArchivoCircuito = path;
    }

    // tiene que estar formado por poligonos exclusivamente
    // Box2D no permite formas concavas
    // tambien falla con rectangulos con las esquinas redondeadas
    public void Cargar() {

        TiledMap miTiledMap = new TmxMapLoader().load(pathArchivoCircuito);
        MapObjects murosMapObjs = miTiledMap.getLayers().get(LAYER_MUROS).getObjects();

        for (MapObject o : murosMapObjs) {
            float[] vertices;
            if (o instanceof PolygonMapObject) {
                vertices = ((PolygonMapObject) o).getPolygon().getTransformedVertices();
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] = vertices[i] / TILE_SIZE;
                }
            } else {
                continue;
            }

            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            Body body = mundo.createBody(bdef);

            // TODO probar con todos los vertices en un solo body en lugar de un ArrayList

            PolygonShape pShape = new PolygonShape();
            pShape.set(vertices);
            body.createFixture(pShape, 1);
            circuitoMuros.add(body);

            pShape.dispose();
        }

        miTiledMap.dispose();
    }

    public Body prepararParrilla(int oponentes, int posJug) {

        TiledMap miTiledMap = new TmxMapLoader().load(pathArchivoCircuito);
        Body jugador = null;
        MapObjects parrilla = miTiledMap.getLayers().get(LAYER_GRID).getObjects();
        int maxOponentes = 0;

        if (maxOponentes < oponentes) {
            oponentes = maxOponentes;
        }

        Vector2 tamCoche = new Vector2(5, 10);
        Vector2 posCoche = null;
        boolean jugInit = false;
        float angulo;

        for (int i = 0; i < oponentes; i++) {
            try {
                posCoche.x = ((PolygonMapObject) parrilla.get(i)).getPolygon().getTransformedVertices()[0];
                posCoche.y = ((PolygonMapObject) parrilla.get(i)).getPolygon().getTransformedVertices()[1];
                angulo = ((PolygonMapObject) parrilla.get(i)).getPolygon().getRotation();
                if (i == posJug) {
                    jugador = Coche.generaCoche(posCoche, mundo, tamCoche);
                    coches.add(jugador);
                    jugInit = true;
                } else {
                    Body c = Coche.generaCoche(posCoche, mundo, tamCoche);
                    c.setTransform(posCoche, angulo);
                    coches.add(c);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
/*
        if (!jugInit) {
            angulo = ((PolygonMapObject) parrilla.get(parrilla.getCount() - 1)).getPolygon().getRotation();
            jugador = Coche.generaCoche(posCoche, mundo, tamCoche);
            jugador.setTransform(posCoche, (float) (angulo * Math.PI / 180f));
        }
*/
        Vector2 v = new Vector2(0,0);
        jugador = Coche.generaCoche(v, mundo, tamCoche);
        jugador.setTransform(v, (float) (90 * Math.PI/180f));

        return jugador;
    }

}
