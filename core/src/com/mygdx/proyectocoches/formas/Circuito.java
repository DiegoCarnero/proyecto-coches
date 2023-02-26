package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP1;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP2;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP3;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_META;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_MUROS;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_IA;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_JUG;
import static com.mygdx.proyectocoches.Constantes.LAYER_CHECKP;
import static com.mygdx.proyectocoches.Constantes.LAYER_META;
import static com.mygdx.proyectocoches.Constantes.LAYER_MUROS;
import static com.mygdx.proyectocoches.Constantes.LAYER_PATH;
import static com.mygdx.proyectocoches.Constantes.TEST_LOOP_NAME;
import static com.mygdx.proyectocoches.Constantes.TEST_LOOP_PATHS;
import static com.mygdx.proyectocoches.Constantes.TILE_SIZE;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_NAME;
import static com.mygdx.proyectocoches.Constantes.TRACK_1_PATHS;
import static com.mygdx.proyectocoches.Constantes.test_loop_ang;
import static com.mygdx.proyectocoches.Constantes.test_loop_vGrid;
import static com.mygdx.proyectocoches.Constantes.track_1_ang;
import static com.mygdx.proyectocoches.Constantes.track_1_vGrid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
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

    /**
     * Conjunto de todos los competidores en el Evento actual
     */
    final private ArrayList<Competidor> competidores;
    /**
     * Nombre del circuito en el sistema de archivos
     */
    final private String nomCircuito;
    /**
     * {@link World} en el que se posicionarán los {@link Body} presentes en el Evento
     */
    final private World mundo;
    /**
     * Rutas internas a los sprites para los vehiculos
     */
    private String[] sprites;
    /**
     * AssetManager donde se encuentran los sprites de los vehiculos
     */
    final private AssetManager am;

    public Sprite getS() {
        return s;
    }

    /**
     * Imagen que representa el circuito
     */
    final private Sprite s;

    /**
     * RenderMode. Establece el tipo de archivos que cargar. 0=Normal, 1=HC (High Contrast)
     */
    private final int rm;

    /**
     * Genera un nuevo objeto Circuito
     *
     * @param mundo       World a utilizar para crear los polígonos tipo Body que conformarán el circuito
     * @param nomCircuito nombre del circuito. Debe coincidir con el nombre del archivo donde se encuentran los polígonos deeste circuito, y éste estar assets/worlds
     * @param am          AssetManager donde se encuentran los sprites de los vehiculos ya cargados
     */
    public Circuito(World mundo, String nomCircuito, AssetManager am) {
        this.mundo = mundo;
        this.competidores = new ArrayList<>();
        this.nomCircuito = nomCircuito;
        this.am = am;

        JsonValue base;
        JsonReader json = new JsonReader();
        base = json.parse(Gdx.files.external("usersettings.json"));
        rm = base.getInt("rendermode");

        Texture t;
        if (rm == 1) {
            am.load("worlds/" + nomCircuito + "_HC.jpg", Texture.class);
            am.finishLoadingAsset("worlds/" + nomCircuito + "_HC.jpg");
            t = (Texture) am.get("worlds/" + nomCircuito + "_HC.jpg");
            sprites = new String[]{"vehicles/cocheia_HC.png", "vehicles/cocheia_HC.png", "vehicles/cochejugador_HC.png"};
        } else {
            am.load("worlds/" + nomCircuito + "_HR.jpg", Texture.class);
            am.finishLoadingAsset("worlds/" + nomCircuito + "_HR.jpg");
            t = (Texture) am.get("worlds/" + nomCircuito + "_HR.jpg");
            sprites = new String[]{"vehicles/citroen_xsara_m.png", "vehicles/ford_escort_rs_m.png", "vehicles/ford_focus_m.png"};
        }
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        s = new Sprite(t);

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
                fDef.filter.maskBits = CAT_COCHE_JUG | CAT_COCHE_IA;
                break;
            case Meta:
                fDef.filter.categoryBits = CAT_CIRCUITO_META;
                fDef.filter.maskBits = CAT_COCHE_JUG | CAT_COCHE_IA;
                fDef.isSensor = true;
                break;
            case Checkpoint1:
                fDef.filter.categoryBits = CAT_CIRCUITO_CHECKP1;
                fDef.filter.maskBits = CAT_COCHE_JUG | CAT_COCHE_IA;
                fDef.isSensor = true;
                break;
            case Checkpoint2:
                fDef.filter.categoryBits = CAT_CIRCUITO_CHECKP2;
                fDef.filter.maskBits = CAT_COCHE_JUG | CAT_COCHE_IA;
                fDef.isSensor = true;
                break;
            case Checkpoint3:
                fDef.filter.categoryBits = CAT_CIRCUITO_CHECKP3;
                fDef.filter.maskBits = CAT_COCHE_JUG | CAT_COCHE_IA;
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

    public void prepararParrilla(int oponentes) {

        Jugador jugador = null;
        int maxOponentes;
        Vector2[] vGrid;
        float angulo;

        switch (nomCircuito) {
            case TEST_LOOP_NAME:
                vGrid = test_loop_vGrid;
                maxOponentes = vGrid.length;
                angulo = test_loop_ang;
                break;
            case TRACK_1_NAME:
                vGrid = track_1_vGrid;
                maxOponentes = vGrid.length;
                angulo = track_1_ang;
                break;
            default:
                vGrid = test_loop_vGrid;
                maxOponentes = vGrid.length;
                angulo = 0;
        }

        if (maxOponentes < oponentes) {
            oponentes = maxOponentes;
        }

        Vector2 tamCoche = new Vector2(10, 20);
        boolean jugInit = false;
        int cont = 0;

        for (Vector2 v : vGrid) {
            if (cont >= oponentes) {
                break;
            }
            CocheIA c = new CocheIA("IA_" + cont, Coche.generaCoche(v, mundo, tamCoche, false), (Texture) am.get(sprites[(int) (Math.random() * 2)]));
            c.getBody().setTransform(v, (float) -(angulo * Math.PI / 180));
            competidores.add(c);
            cont++;
        }
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
        int maxOponentes;
        Vector2[] vGrid;
        float angulo;

        switch (nomCircuito) {
            case TEST_LOOP_NAME:
                vGrid = test_loop_vGrid;
                maxOponentes = vGrid.length;
                angulo = test_loop_ang;
                break;
            case TRACK_1_NAME:
                vGrid = track_1_vGrid;
                maxOponentes = vGrid.length;
                angulo = track_1_ang;
                break;
            default:
                vGrid = test_loop_vGrid;
                maxOponentes = vGrid.length;
                angulo = 0;
        }

        if (maxOponentes < oponentes) {
            oponentes = maxOponentes;
        }

        Vector2 tamCoche = new Vector2(8, 16);
        boolean jugInit = false;
        int cont = 0;

        for (Vector2 v : vGrid) {
            if (cont >= oponentes + 1) {
                break;
            }

            if (cont == posJug - 1) {
                Texture t = (Texture) am.get(sprites[2]);
                t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                jugador = new Jugador(Coche.generaCoche(v, mundo, tamCoche, true), t);
                jugador.getBody().setTransform(v, (float) -(angulo * Math.PI / 180));
                jugador.getBody().getFixtureList().get(0).setUserData(jugador);
                competidores.add(jugador);
                jugInit = true;
            } else {
                Texture t = (Texture) am.get(sprites[(int) (Math.random() * 2)]);
                t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                CocheIA c = new CocheIA("IA_" + cont, Coche.generaCoche(v, mundo, tamCoche, false), t);
                c.getBody().getFixtureList().get(0).setUserData(c);
                c.getBody().setTransform(v, (float) -(angulo * Math.PI / 180));
                competidores.add(c);
            }
            cont++;
        }

        if (!jugInit) {
            Texture t = (Texture) am.get(sprites[2]);
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            jugador = new Jugador(Coche.generaCoche(vGrid[vGrid.length - 1], mundo, tamCoche, true), t);
            jugador.getBody().setTransform(jugador.getPosition(), (float) -(angulo * Math.PI / 180));
            jugador.getBody().getFixtureList().get(0).setUserData(jugador);
            competidores.add(jugador);
        }

        return jugador;
    }

    /**
     * Carga las rutas disponibles en el {@link Circuito} desde archivo. Cada ruta debe estar representada en su propia "Layer" por un solo polígono
     *
     * @return array con las rutas que la Ia podrá tomar
     */
    public CatmullRomSpline<Vector2>[] cargarRutas() {

        TiledMap miTiledMap = new TmxMapLoader().load("worlds/" + nomCircuito + ".tmx");
        Vector2[] coordV;
        int numPaths;

        switch (nomCircuito) {
            case TEST_LOOP_NAME:
                numPaths = TEST_LOOP_PATHS;
                break;
            case TRACK_1_NAME:
                numPaths = TRACK_1_PATHS;
                break;
            default:
                numPaths = 1;
                break;
        }

        CatmullRomSpline<Vector2>[] rutas = new CatmullRomSpline[numPaths];

        for (int ndxRuta = 0; ndxRuta < numPaths; ndxRuta++) {
            MapObjects rutaMapObjects = miTiledMap.getLayers().get(LAYER_PATH + (ndxRuta + 1)).getObjects();

            float[] coordF = ((PolygonMapObject) rutaMapObjects.get(0)).getPolygon().getTransformedVertices();
            coordV = new Vector2[coordF.length / 2];
            int ndxCoordVector = 0;
            for (int ndxCoordFloat = 0; ndxCoordFloat < coordF.length; ndxCoordFloat += 2) {
                coordV[ndxCoordVector] = new Vector2(coordF[ndxCoordFloat] / TILE_SIZE, coordF[ndxCoordFloat + 1] / TILE_SIZE);
                ndxCoordVector++;
            }


            rutas[ndxRuta] = new CatmullRomSpline<>();
            rutas[ndxRuta].set(coordV, true);
        }

        return rutas;
    }

    /**
     * Carga desde un archivo los puntos que se utilizan para determinar la posición de los competidores. Debe estar representada en su propia "Layer" por un solo polígono
     *
     * @return {@link CatmullRomSpline} con los vertices del polígono
     */
    public CatmullRomSpline<Vector2> cargarSplineControl() {
        TiledMap miTiledMap = new TmxMapLoader().load("worlds/" + nomCircuito + ".tmx");
        Vector2[] coordV;

        CatmullRomSpline<Vector2> splineControl = new CatmullRomSpline<>();

        MapObjects rutaMapObjects = miTiledMap.getLayers().get("SplineControl").getObjects();

        float[] coordF = ((PolygonMapObject) rutaMapObjects.get(0)).getPolygon().getTransformedVertices();
        coordV = new Vector2[coordF.length / 2];
        int ndxCoordVector = 0;
        for (int ndxCoordFloat = 0; ndxCoordFloat < coordF.length; ndxCoordFloat += 2) {
            coordV[ndxCoordVector] = new Vector2(coordF[ndxCoordFloat] / TILE_SIZE, coordF[ndxCoordFloat + 1] / TILE_SIZE);
            ndxCoordVector++;
        }
        splineControl.set(coordV, true);

        return splineControl;
    }

    /**
     * Devuelve todos los competidores en el circuito actual
     *
     * @return Lista con los competidores
     */
    public ArrayList<Competidor> getCompetidores() {
        return competidores;
    }

}
