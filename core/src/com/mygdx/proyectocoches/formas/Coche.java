package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP1;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP2;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP3;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_META;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_MUROS;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_IA;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_IA_SENSOR;
import static com.mygdx.proyectocoches.Constantes.DAMPING_DEFAULT;
import static com.mygdx.proyectocoches.Constantes.DENSIDAD_COCHE;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_JUG;
import static com.mygdx.proyectocoches.Constantes.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Coche {

    /**
     * Genera un {@link Body} y lo posiciona en el {@link World} en las coordenadas pasados por parámetro
     * @param pos posición en el mundo
     * @param mundo World en el que se implantará el Body
     * @param tam tamaño del Body
     * @param tipo tipo de {@link com.mygdx.proyectocoches.entidades.Competidor} al que se asociará el Body generado. Determina la densidad de este. 'true' si es {@link com.mygdx.proyectocoches.entidades.Jugador}, false si es {@link com.mygdx.proyectocoches.entidades.CocheIA}
     * @return Body generado
     */
    public static Body generaCoche(Vector2 pos, World mundo, Vector2 tam, boolean tipo) {
        BodyDef bdef;
        Body body;

        // definir body
        bdef = new BodyDef();
        bdef.position.set(pos);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(bdef);

        //definir fixture
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(tam.x / PPM, tam.y / PPM);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = pShape;
        if (tipo) {  //jugador
            fDef.density = DENSIDAD_COCHE;
            fDef.filter.categoryBits = CAT_COCHE_JUG;
            fDef.filter.maskBits = CAT_CIRCUITO_MUROS | CAT_CIRCUITO_META | CAT_CIRCUITO_CHECKP1 | CAT_CIRCUITO_CHECKP2 | CAT_CIRCUITO_CHECKP3 | CAT_COCHE_IA;
        } else { // IA
            fDef.density = 0.5f;
            fDef.filter.categoryBits = CAT_COCHE_IA;
            fDef.filter.maskBits = CAT_CIRCUITO_MUROS | CAT_CIRCUITO_META | CAT_CIRCUITO_CHECKP1 | CAT_CIRCUITO_CHECKP2 | CAT_CIRCUITO_CHECKP3 | CAT_COCHE_IA_SENSOR | CAT_COCHE_IA | CAT_COCHE_JUG;
        }
        body.createFixture(fDef);
        body.setLinearDamping(DAMPING_DEFAULT);
        return body;
    }
}
