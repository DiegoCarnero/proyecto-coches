package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP1;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP2;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP3;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_META;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_MUROS;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_JUG;
import static com.mygdx.proyectocoches.Constantes.LAYER_CHECKP;
import static com.mygdx.proyectocoches.Constantes.LAYER_META;
import static com.mygdx.proyectocoches.Constantes.LAYER_MUROS;
import static com.mygdx.proyectocoches.Constantes.LAYER_PATH;
import static com.mygdx.proyectocoches.Constantes.TEST_LOOP_PATHS;
import static com.mygdx.proyectocoches.Constantes.TILE_SIZE;
import static com.mygdx.proyectocoches.Constantes.test_loop_ang;
import static com.mygdx.proyectocoches.Constantes.test_loop_vGrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.proyectocoches.entidades.CocheIA;
import com.mygdx.proyectocoches.entidades.Competidor;
import com.mygdx.proyectocoches.entidades.Jugador;

import java.util.ArrayList;

/**
 * Tipos de objetos Body que pueden existir en el circuito
 */
enum TipoPoly {
    Muros,
    Meta,
    Checkpoint1,
    Checkpoint2,
    Checkpoint3
}

/**
 * Objeto que contiene todas las entidades en el mundo:
 * <ul>
 *   <li>Muros</li>
 *   <li>Puntos de control</li>
 *   <li>Linea de meta</li>
 *   <li>Competidores IA</li>
 *   <li>Jugador</li>
 * </ul>
 */
public class Circuito {

    final private ArrayList<Competidor> competidores;
    final private String nomCircuito;
    final private World mundo;

    /**
     * Genera un nuevo objeto Circuito
     *
     * @param mundo       World a utilizar para crear los polígonos tipo Body que conformarán el circuito
     * @param nomCircuito nombre del circuito. Debe coincidir con el nombre del archivo donde se encuentran los polígonos deeste circuito, y éste estar assets/worlds
     */
    public Circuito(World mundo, String nomCircuito) {
        this.mundo = mundo;
        this.competidores = new ArrayList<>();
        this.nomCircuito = nomCircuito;
    }

    /**
     * Extrae el polígono desde un PolygonMapObject y lo añade al World de este Circuito
     *
     * @param p polígono que se va a añadir al World de este Circuito
     * @param t tipo de polígono. Determina el filtro de colisiones
     */
    private void getPolygon(PolygonMapObject p, TipoPoly t) {
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

        switch (t) {
            case Muros:
                fDef.filter.categoryBits = CAT_CIRCUITO_MUROS;
                fDef.filter.maskBits = CAT_COCHE_JUG;
                break;
            case Meta:
                fDef.filter.categoryBits = CAT_CIRCUITO_META;
                fDef.filter.maskBits = CAT_COCHE_JUG;
                fDef.isSensor = true;
                break;
            case Checkpoint1:
                fDef.filter.categoryBits = CAT_CIRCUITO_CHECKP1;
                fDef.filter.maskBits = CAT_COCHE_JUG;
                fDef.isSensor = true;
                break;
            case Checkpoint2:
                fDef.filter.categoryBits = CAT_CIRCUITO_CHECKP2;
                fDef.filter.maskBits = CAT_COCHE_JUG;
                fDef.isSensor = true;
                break;
            case Checkpoint3:
                fDef.filter.categoryBits = CAT_CIRCUITO_CHECKP3;
                fDef.filter.maskBits = CAT_COCHE_JUG;
                fDef.isSensor = true;
                break;

        }
        body.createFixture(fDef);
        pShape.dispose();
    }

    /**
     * Añade los muros límite del circuito desde un archivo, dado por nomCircuito.
     * Los muros deben estar en una capa con el nombre 'Muros'.
     * Todos los elementos relevantes en esta capa deben ser polígonos no cóncavos.
     */
    public void cargarMuros() {

        TiledMap miTiledMap = new TmxMapLoader().load("worlds/" + nomCircuito + ".tmx");
        MapObjects murosMapObjs = miTiledMap.getLayers().get(LAYER_MUROS).getObjects();

        for (MapObject o : murosMapObjs) {
            if (o instanceof PolygonMapObject) {
                getPolygon((PolygonMapObject) o, TipoPoly.Muros);
            }
        }

        miTiledMap.dispose();
    }

    /**
     * Añade la meta del circuito desde un archivo, dado por nomCircuito.
     * La meta debe estar en una capa con el nombre 'Meta'.
     * Todos los elementos relevantes en esta capa deben ser polígonos no cóncavos.
     */
    public void cargarMeta() {

        TiledMap miTiledMap = new TmxMapLoader().load("worlds/" + nomCircuito + ".tmx");
        MapObjects metaMapObjs = miTiledMap.getLayers().get(LAYER_META).getObjects();

        for (MapObject o : metaMapObjs) {
            if (o instanceof PolygonMapObject) {
                getPolygon((PolygonMapObject) o, TipoPoly.Meta);
            }
        }

        miTiledMap.dispose();
    }

    /**
     * Añade los puntos de control del circuito desde un archivo, dado por nomCircuito.
     * Los puntos de control deben estar en una capa con el nombre 'Checkpoint'. Dicha capa debe haber 3 puntos de control.
     * Todos los elementos relevantes en esta capa deben ser polígonos no cóncavos.
     */
    public void cargarCheckpoints() {

        TiledMap miTiledMap = new TmxMapLoader().load("worlds/" + nomCircuito + ".tmx");
        for (int i = 1; i < 4; i++) {

            MapObjects metaMapObjs = miTiledMap.getLayers().get(LAYER_CHECKP + i).getObjects();

            for (MapObject o : metaMapObjs) {
                if (o instanceof PolygonMapObject) {
                    if (i == 1) {
                        getPolygon((PolygonMapObject) o, TipoPoly.Checkpoint1);
                    } else if (i == 2) {
                        getPolygon((PolygonMapObject) o, TipoPoly.Checkpoint2);
                    } else if (i == 3) {
                        getPolygon((PolygonMapObject) o, TipoPoly.Checkpoint3);
                    }
                }
            }
        }
        miTiledMap.dispose();
    }

    /**
     * Crea y posiciona en el World de este Circuito los objetos tipo Body que representan a los competidores.
     * Las posiciones son determinadas por nomCircuito.
     *
     * @param oponentes numero de competidores IA
     * @param posJug    posición en parrila donde irá el jugador
     * @return Body del jugador
     */
    public Jugador prepararParrilla(int oponentes, int posJug) {

        Jugador jugador = null;
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
                jugador = new Jugador(Coche.generaCoche(v, mundo, tamCoche));
                jugador.getBody().setTransform(v, (float) -(angulo * Math.PI / 180));
                competidores.add(jugador);
                jugInit = true;
            } else {
                CocheIA c = new CocheIA(Coche.generaCoche(v, mundo, tamCoche));
                c.getBody().setTransform(v, (float) -(angulo * Math.PI / 180));
                competidores.add(c);
            }
            cont++;
        }

        if (!jugInit) {
            jugador = new Jugador(Coche.generaCoche(vGrid[vGrid.length - 1], mundo, tamCoche));
            jugador.getBody().setTransform(jugador.getWorldPosition(), (float) -(angulo * Math.PI / 180));
        }

        return jugador;
    }

    public CatmullRomSpline<Vector2>[] cargarRutas() {

        TiledMap miTiledMap = new TmxMapLoader().load("worlds/" + nomCircuito + ".tmx");
        Vector2[] coordV;
        CatmullRomSpline<Vector2>[] rutas = new CatmullRomSpline[TEST_LOOP_PATHS];

        for (int ndxRuta = 0; ndxRuta < TEST_LOOP_PATHS; ndxRuta++) {
            MapObjects rutaMapObjects = miTiledMap.getLayers().get(LAYER_PATH + (ndxRuta + 1)).getObjects();

            float[] coordF = ((PolygonMapObject) rutaMapObjects.get(0)).getPolygon().getTransformedVertices();
            coordV = new Vector2[coordF.length / 2];
            int ndxCoordVector = 0;
            for (int ndxCoordFloat = 0; ndxCoordFloat < coordF.length; ndxCoordFloat += 2) {
                coordV[ndxCoordVector] = new Vector2(coordF[ndxCoordFloat] / TILE_SIZE, coordF[ndxCoordFloat + 1] / TILE_SIZE);
                ndxCoordVector++;
            }

            Gdx.app.log("ndxRuta", coordV.length+"");
            rutas[ndxRuta] = new CatmullRomSpline<>();
            rutas[ndxRuta].set(coordV, true);
        }

        return rutas;
    }

    public ArrayList<Competidor> getCompetidores(){
        return competidores;
    }

}
