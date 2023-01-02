package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.LAYER_MUROS;
import static com.mygdx.proyectocoches.Constantes.TILE_SIZE;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class GeneradorCircuito {

    // tiene que estar formado por poligonos exclusivamente
    // Box2D no permite formas concavas
    // tambien falla con rectangulos con las esquinas redondeadas
    public static ArrayList<Body> Cargar(World mundo, String path){

        TiledMap miTiledMap = new TmxMapLoader().load(path);

        ArrayList<Body> circuitoBodies = new ArrayList<>();

        MapObjects muros = miTiledMap.getLayers().get(LAYER_MUROS).getObjects();

        for (MapObject o : muros) {
            float[] vertices;
            if (o instanceof PolygonMapObject){
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
            body.createFixture(pShape,1);
            circuitoBodies.add(body);

            pShape.dispose();
        }

        miTiledMap.dispose();
        return circuitoBodies;
    }

}
