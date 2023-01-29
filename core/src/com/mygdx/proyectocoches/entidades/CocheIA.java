package com.mygdx.proyectocoches.entidades;
/**
 * https://github.com/libgdx/gdx-ai/blob/master/tests/src/com/badlogic/gdx/ai/tests/steer/box2d/Box2dSteeringEntity.java
 */

import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_BACK;
import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;
import static com.mygdx.proyectocoches.Constantes.MAX_VELO_IA;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.proyectocoches.formas.Sensor;

public class CocheIA extends Competidor implements Steerable<Vector2> {

    public Sensor destinoSensor;
    private boolean tagged;
    private float maxAngularSpeed;
    private int numDestinosRuta;
    private int destinoActualNdx;

    public int getRutaSelect() {
        return rutaSelect;
    }

    public void setRutaSelect(int rutaSelect) {
        this.rutaSelect = rutaSelect;
    }

    private int rutaSelect;

    private float maxLinearSpeed;
    private float maxAngularAcceleration;
    private float maxLinearAcceleration;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steerOut;

    private Arrive<Vector2> arriveSB;
    private Seek<Vector2> seekSB;

    public void setNumDestinosRuta(int numDestinosRuta) {
        this.numDestinosRuta = numDestinosRuta;
    }

    public CocheIA(Body b) {
        super(b);
        this.numDestinosRuta = 0;
        this.maxAngularAcceleration = 10f;
        this.maxLinearSpeed = MAX_VELOCIDAD_FORW;
        this.maxLinearAcceleration = 20f;
        this.maxAngularSpeed = 20.5f;
        this.tagged = false;
        this.steerOut = new SteeringAcceleration<Vector2>(new Vector2());
        this.destinoActualNdx = 0;
        super.getBody().getFixtureList().get(0).setUserData(this);
        this.destinoSensor = new Sensor(b.getWorld(), this);

        this.arriveSB = new Arrive<>(this, this.destinoSensor)
                .setTimeToTarget(0.0001f)
                .setArrivalTolerance(2f)
                .setDecelerationRadius(4f);
        this.seekSB = new Seek<>(this, this.destinoSensor);
    }

    public void update(float delta) {

        if (behavior != null) {
            behavior.calculateSteering(steerOut);
            applySteering(delta);
        }
    }

    private void applySteering(float delta) {

        boolean anyAccelerations = false;

        if (!steerOut.linear.isZero()) {
            Vector2 fuerza = steerOut.linear.scl(delta);
            getBody().applyForceToCenter(fuerza, true);
            anyAccelerations = true;
        }

        if (steerOut.angular != 0) {
            getBody().applyTorque(steerOut.angular * delta, true);
            anyAccelerations = true;
        } else {
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero()) {
                float newOrientation = vectorToAngle(linVel);
                getBody().setAngularVelocity((newOrientation - getAngularVelocity()) * delta);
                getBody().setTransform(getBody().getPosition(), newOrientation);
            }
        }

        if (anyAccelerations) {
            // lineal
            Vector2 velocity = getBody().getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                getBody().setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }
            //angular
            if (getBody().getAngularVelocity() > maxAngularSpeed) {
                getBody().setAngularVelocity(maxAngularSpeed);
            }
        }
    }

    /**
     * Returns the vector indicating the linear velocity of this Steerable.
     */
    @Override
    public Vector2 getLinearVelocity() {
        return getBody().getLinearVelocity();
    }

    /**
     * Returns the float value indicating the the angular velocity in radians of this Steerable.
     */
    @Override
    public float getAngularVelocity() {
        return getBody().getAngularVelocity();
    }

    /**
     * Returns the bounding radius of this Steerable.
     */
    @Override
    public float getBoundingRadius() {
        return 1f;
    }

    /**
     * Returns {@code true} if this Steerable is tagged; {@code false} otherwise.
     */
    @Override
    public boolean isTagged() {
        return tagged;
    }

    /**
     * Tag/untag this Steerable. This is a generic flag utilized in a variety of ways.
     *
     * @param tagged the boolean value to set
     */
    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
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
        return this.maxLinearSpeed;
    }

    /**
     * Sets the maximum linear speed.
     *
     * @param maxLinearSpeed
     */
    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    /**
     * Returns the maximum linear acceleration.
     */
    @Override
    public float getMaxLinearAcceleration() {
        return this.maxLinearAcceleration;
    }

    /**
     * Sets the maximum linear acceleration.
     *
     * @param maxLinearAcceleration
     */
    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    /**
     * Returns the maximum angular speed.
     */
    @Override
    public float getMaxAngularSpeed() {
        return this.maxAngularSpeed;
    }

    /**
     * Sets the maximum angular speed.
     *
     * @param maxAngularSpeed
     */
    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    /**
     * Returns the maximum angular acceleration.
     */
    @Override
    public float getMaxAngularAcceleration() {
        return this.maxAngularAcceleration;
    }

    /**
     * Sets the maximum angular acceleration.
     *
     * @param maxAngularAcceleration
     */
    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    /**
     * Returns the float value indicating the orientation of this location. The orientation is the angle in radians representing
     * the direction that this location is facing.
     */
    @Override
    public float getOrientation() {
        return getBody().getAngle();
    }

    /**
     * Sets the orientation of this location, i.e. the angle in radians representing the direction that this location is facing.
     *
     * @param orientation the orientation in radians
     */
    @Override
    public void setOrientation(float orientation) {

        getBody().setTransform(getPosition(), orientation);
    }

    /**
     * Returns the angle in radians pointing along the specified vector.
     *
     * @param vector the vector
     */
    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    /**
     * Returns the unit vector in the direction of the specified angle expressed in radians.
     *
     * @param outVector the output vector.
     * @param angle     the angle in radians.
     * @return the output vector for chaining.
     */
    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior() {
        return behavior;
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.behavior = steeringBehavior;
    }

    private float distanciaEntrePuntos(Vector2 a, Vector2 b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public void setDestinoSensorPosition(Vector2 posicion) {

        float dist = distanciaEntrePuntos(posicion, getBody().getPosition());
        if (dist > 100f) {
            setMaxLinearSpeed(MAX_VELO_IA);
            setSteeringBehavior(this.seekSB);
        }
        if (dist < 20f) {
            setMaxLinearSpeed(MAX_VELOCIDAD_BACK);
            setSteeringBehavior(this.arriveSB);
        }
        if (dist < 2f) {
            setSteeringBehavior(this.seekSB);
        }
        this.destinoSensor.setPos(posicion);
    }

    public Sensor getDestinoSensor() {
        return destinoSensor;
    }

    public void nextDestino() {
        destinoActualNdx++;
        if (destinoActualNdx == numDestinosRuta) {
            destinoActualNdx = 0;
        }
    }

    public int getDestinoActualNdx() {
        return destinoActualNdx;
    }

}
