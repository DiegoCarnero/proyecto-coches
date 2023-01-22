package com.mygdx.proyectocoches.test;

/**
 * AI code by Conner Anderson https://youtu.be/pnKcuJQT31A
 */

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2dSteeringEntity implements Steerable<Vector2> {
    private final Body body;
    private final float boundingRadius;
    private final float maxlinearAcceleration;
    private boolean tagged;
    private float maxAngularSpeed, maxLinearSpeed, maxAngularAcceleration, maxLinearAcceleration;
    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steerOutput;

    public B2dSteeringEntity(Body body, float boundingRadius) {
        this.body = body;
        this.boundingRadius = boundingRadius;

        this.maxLinearSpeed = 500;
        this.maxlinearAcceleration = 5000;
        this.maxAngularSpeed = 30;
        this.maxAngularAcceleration = 5;

        this.tagged = false;

        this.steerOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
    }

    public void update(float delta) {
        if (behavior != null) {
            behavior.calculateSteering(steerOutput);
            applySteering(delta);
        }
    }


    private void applySteering(float delta) {

        boolean anyAccelerations = false;

        if (!steerOutput.linear.isZero()) {
            Vector2 force = steerOutput.linear.scl(delta);
            body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }

        if (anyAccelerations) {
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }
        }

    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override

    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float v) {

    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override

    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return new Vector2();
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float v) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }


    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override

    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }


    public Body getBody() {
        return body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior() {
        return behavior;
    }
}
