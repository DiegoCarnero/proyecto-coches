package com.mygdx.proyectocoches.entidades;

import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_BACK;
import static com.mygdx.proyectocoches.Constantes.MAX_VELOCIDAD_FORW;
import static com.mygdx.proyectocoches.Constantes.MAX_VELO_IA;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.proyectocoches.formas.Sensor;

/**
 * Competidor que serán controlados por la CPU
 */
public class CocheIA extends Competidor implements Steerable<Vector2> {

    /**
     * Sensor que indica a esta IA su proximo destino. En cuanto la IA lo alcanza, se mueve a la sieguiente posicion
     */
    public Sensor destinoSensor;

    private boolean tagged;
    /**
     * Velocidad angular máxima para el {@link Body} de este competidor IA
     */
    private float maxAngularSpeed;
    /**
     * Numero de posiciones para el sensor en la ruta asignada a este  {@link CocheIA}
     */
    private int numDestinosRuta;
    /**
     * Destino actual dentro de la ruta asignada a este  {@link CocheIA}
     */
    private int destinoActualNdx;

    /**
     * Devulve el indice de la ruta asignada a este {@link CocheIA} dentro de un array de {@link com.badlogic.gdx.math.CatmullRomSpline}
     * @return indice de la ruta asignada
     */
    public int getRutaSelect() {
        return rutaSelect;
    }

    /**
     * Asigna el indice de la ruta para este {@link CocheIA} dentro de un array de {@link com.badlogic.gdx.math.CatmullRomSpline}
     * @param rutaSelect  indice de la ruta asiganda
     */
    public void setRutaSelect(int rutaSelect) {
        this.rutaSelect = rutaSelect;
    }

    /**
     * Indice de la ruta asignada a este {@link CocheIA} dentro de un array de {@link com.badlogic.gdx.math.CatmullRomSpline}
     */
    private int rutaSelect;

    /**
     *
     * Velocidad lineal máxima para el {@link Body} de este competidor IA
     */
    private float maxLinearSpeed;
    /**
     * Aceleración angular máxima para el {@link Body} de este competidor IA
     */
    private float maxAngularAcceleration;
    /**
     * Aceleración lineal máxima para el {@link Body} de este competidor IA
     */
    private float maxLinearAcceleration;

    /**
     * A SteeringBehavior calculates the linear and/or angular accelerations to be applied to its owner.
     * El tipo de {@link SteeringBehavior} que se va a utilizar, dependiendo de la distancia al sensor
     */
    SteeringBehavior<Vector2> behavior;
    /**
     *  SteeringAcceleration is a movement requested by the steering system. It is made up of two components, linear and angular acceleration.
     */
    SteeringAcceleration<Vector2> steerOut;

    /**
     * SteeringBehavior de tipo {@link Arrive} se que utiliza si el {@link Sensor} es próximo al {@link CocheIA}
     */
    private final Arrive<Vector2> arriveSB;
    /**
     * SteeringBehavior de tipo {{@link Seek} que utiliza si el {@link CocheIA} está lejos del {@link Sensor} destino
     */
    private final Seek<Vector2> seekSB;

    /**
     * Establece la cantidad de destinos que puede tomar el {@link Sensor}
     * @param numDestinosRuta numero de destino que tiene la ruta asignada
     */
    public void setNumDestinosRuta(int numDestinosRuta) {
        this.numDestinosRuta = numDestinosRuta;
    }

    /**
     * Competidor que será controlado por la CPU
     * @param nom nombre de este competido
     * @param b {@link Body} de este competido
     * @param s textura asociada a este competidor
     */
    public CocheIA(String nom, Body b, Texture s) {
        super(nom,b,s);
        this.numDestinosRuta = 0;
        this.maxAngularAcceleration = 10f;
        this.maxLinearSpeed = MAX_VELO_IA;
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

    /**
     * Actualiza las fuerzas aplicadas al {@link Body} de este CocheIA
     * @param delta deltaTime
     */
    public void update(float delta) {

        if (behavior != null) {
            behavior.calculateSteering(steerOut);
            applySteering(delta);
        }
    }

    /**
     * Aplica las nuevas fuerzas calculadas al cuerpo
     * @param delta tiempo pasado desde el ultimo calculo
     */
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
     * @param value ZeroLinearSpeedThreshold
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
     * @param maxLinearSpeed maximum linear speed.
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
     * @param maxLinearAcceleration maximum linear acceleration.
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
     * @param maxAngularSpeed the maximum angular speed.
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
     * @param maxAngularAcceleration the maximum angular acceleration.
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

    /**
     * Creates a new location.
     * <p>
     * This method is used internally to instantiate locations of the correct type parameter {@code T}. This technique keeps the API
     * simple and makes the API easier to use with the GWT backend because avoids the use of reflection.
     *
     * @return the newly created location.
     */
    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    /**
     * Establece el Steering Behavior que se debe usar con este {@link CocheIA}
     * @param steeringBehavior nuevo Steering Behavior
     */
    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.behavior = steeringBehavior;
    }

    /**
     * Calcula la distancia entre dos puntos
     * @param a punto 1
     * @param b punto 2
     * @return distancia lineal
     */
    private float distanciaEntrePuntos(Vector2 a, Vector2 b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    /**
     * Establece la nueva posición que debe tomar el {@link Sensor} y, dependiendo de la distancia entre este y el {@link CocheIA} actualiza el Steering Behavior utilizado
     * @param posicion posición en el {@link com.badlogic.gdx.physics.box2d.World}
     */
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
            setMaxLinearSpeed(MAX_VELO_IA);
            setSteeringBehavior(this.seekSB);
        }
        this.destinoSensor.setPos(posicion);
    }

    /**
     * Devuelve el Sensor asociado a este {@link CocheIA}
     * @return el Sensor asociado a este {@link CocheIA}
     */
    public Sensor getDestinoSensor() {
        return destinoSensor;
    }

    /**
     * Actualiza el indice en el {@link com.badlogic.gdx.math.CatmullRomSpline}  en el que debe ponerse el {@link Sensor}
     */
    public void nextDestino() {
        destinoActualNdx++;
        if (destinoActualNdx == numDestinosRuta) {
            destinoActualNdx = 0;
        }
    }

    /**
     * Devuelve el indice en el {@link com.badlogic.gdx.math.CatmullRomSpline} en el que se encuentra el {@link Sensor}
     * @return indices del spline de donde esta el siguiente destino
     */
    public int getDestinoActualNdx() {
        return destinoActualNdx;
    }

}
