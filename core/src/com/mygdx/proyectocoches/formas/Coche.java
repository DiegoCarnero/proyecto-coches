package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP1;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP2;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_CHECKP3;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_META;
import static com.mygdx.proyectocoches.Constantes.CAT_CIRCUITO_MUROS;
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

public class Coche{

    public static Body generaCoche(Vector2 pos, World mundo, Vector2 tam){
        BodyDef bdef;
        Body body;

        // definir body
        bdef = new BodyDef();
        bdef.position.set(pos);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(bdef);

        //definir fixture
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(tam.x/PPM,tam.y/PPM);
        FixtureDef fDef= new FixtureDef();
        fDef.shape = pShape;
        fDef.density = DENSIDAD_COCHE;
        fDef.filter.categoryBits = CAT_COCHE_JUG;
        fDef.filter.maskBits = CAT_CIRCUITO_MUROS | CAT_CIRCUITO_META | CAT_CIRCUITO_CHECKP1 | CAT_CIRCUITO_CHECKP2 | CAT_CIRCUITO_CHECKP3;

        body.createFixture(fDef);
        body.setLinearDamping(DAMPING_DEFAULT);
        return body;
    }
}
