package com.mygdx.proyectocoches.formas;

import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_IA;
import static com.mygdx.proyectocoches.Constantes.CAT_COCHE_IA_SENSOR;
import static com.mygdx.proyectocoches.Constantes.PPM;
import static com.mygdx.proyectocoches.Constantes.SENSOR_SIZE;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.proyectocoches.entidades.CocheIA;

/**
 * Objeto que controla el destino de un {@link CocheIA} al que está asociado
 */
public class Sensor implements Steerable {

    /**
     * {@link Body} representado el sensor
     */
    private final Body b;
    /**
     * Posición del sensor
     */
    private Vector2 pos;

    /**
     * Objeto que controla el destino de un {@link CocheIA} al que está asociado
     * @param mundo donde se implantará el {@link Body} representando el sensor
     * @param owner CocheIA al que está asociado el Sensor
     */
    public Sensor(World mundo, CocheIA owner){
        BodyDef bdef;

        // definir body
        bdef = new BodyDef();
        bdef.position.set(new Vector2(0,0));
        bdef.type = BodyDef.BodyType.StaticBody;
        this.b = mundo.createBody(bdef);

        //definir fixture
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(SENSOR_SIZE/PPM,SENSOR_SIZE/PPM);
        FixtureDef fDef= new FixtureDef();
        fDef.isSensor = true;
        fDef.shape = pShape;

        fDef.filter.categoryBits = CAT_COCHE_IA_SENSOR;
        fDef.filter.maskBits = CAT_COCHE_IA;
        this.b.createFixture(fDef);
        this.b.getFixtureList().get(0).setUserData(owner);
        this.pos = new Vector2(0,0);
        pShape.dispose();
    }

    /**
     * Returns the vector indicating the linear velocity of this Steerable.
     */
    @Override
    public Vector getLinearVelocity() {
        return new Vector2(0,0);
    }

    /**
     * Returns the float value indicating the the angular velocity in radians of this Steerable.
     */
    @Override
    public float getAngularVelocity() {
        return 0;
    }

    /**
     * Returns the bounding radius of this Steerable.
     */
    @Override
    public float getBoundingRadius() {
        return 0.5f;
    }

    /**
     * Returns {@code true} if this Steerable is tagged; {@code false} otherwise.
     */
    @Override
    public boolean isTagged() {
        return false;
    }

    /**
     * Tag/untag this Steerable. This is a generic flag utilized in a variety of ways.
     *
     * @param tagged the boolean value to set
     */
    @Override
    public void setTagged(boolean tagged) {

    }

    /**
     * Returns the threshold below which the linear speed can be considered zero. It must be a small positive value near to zero.
     * Usually it is used to avoid updating the orientation when the velocity vector has a negligible length.
     */
    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    /**
     * Sets the threshold below which the linear speed can be considered zero. It must be a small positive value near to zero.
     * Usually it is used to avoid updating the orientation when the velocity vector has a negligible length.
     *
     * @param value
     */
    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    /**
     * Returns the maximum linear speed.
     */
    @Override
    public float getMaxLinearSpeed() {
        return 0;
    }

    /**
     * Sets the maximum linear speed.
     *
     * @param maxLinearSpeed
     */
    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    /**
     * Returns the maximum linear acceleration.
     */
    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    /**
     * Sets the maximum linear acceleration.
     *
     * @param maxLinearAcceleration
     */
    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {

    }

    /**
     * Returns the maximum angular speed.
     */
    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    /**
     * Sets the maximum angular speed.
     *
     * @param maxAngularSpeed
     */
    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {

    }

    /**
     * Returns the maximum angular acceleration.
     */
    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    /**
     * Sets the maximum angular acceleration.
     *
     * @param maxAngularAcceleration
     */
    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {

    }

    /**
     * Returns the vector indicating the position of this location.
     */
    @Override
    public Vector getPosition() {
        return pos;
    }

    /**
     * Returns the float value indicating the orientation of this location. The orientation is the angle in radians representing
     * the direction that this location is facing.
     */
    @Override
    public float getOrientation() {
        return 0;
    }

    /**
     * Sets the orientation of this location, i.e. the angle in radians representing the direction that this location is facing.
     *
     * @param orientation the orientation in radians
     */
    @Override
    public void setOrientation(float orientation) {

    }

    /**
     * Returns the angle in radians pointing along the specified vector.
     *
     * @param vector the vector
     */
    @Override
    public float vectorToAngle(Vector vector) {
        return 0;
    }

    /**
     * Returns the unit vector in the direction of the specified angle expressed in radians.
     *
     * @param outVector the output vector.
     * @param angle     the angle in radians.
     * @return the output vector for chaining.
     */
    @Override
    public Vector angleToVector(Vector outVector, float angle) {
        return null;
    }

    /**
     * Creates a new location.
     * <p>
     * This method is used internally to instantiate locations of the correct type parameter {@code T}. This technique keeps the API
     * simple and makes the API easier to use with the GWT backend because avoids the use of reflection.
     *
     * @return the newly created location.
     */
    @Override
    public Location newLocation() {
        return null;
    }

    public void setPos(Vector2 pos) {
        this.b.setTransform(pos,0);
        this.pos = pos;
    }
}
