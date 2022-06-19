package at.pmzcraft.game.program.engine;

import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3;

import static at.pmzcraft.game.program.engine.render.mathematical.utils.AngleUtils.*;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3.*;


public class Camera {

    private Vector3 position;
    private Vector3 rotation;

    public Camera() {
        position = new Vector3();
        rotation = new Vector3();
    }

    public Camera(Vector3 position, Vector3 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = (Vector3) position.clone();
    }

    public void movePosition(Vector3 offset) {
        if (offset.get(Z) != 0) {
            position.set(X, position.get(X) + (sin(toRadians(rotation.get(Y))) * -1 * offset.get(Z)));
            position.set(Z, position.get(Z) + (cos(toRadians(rotation.get(Y))) * offset.get(Z)));
        }
        if (offset.get(X) != 0) {
            position.set(X, position.get(X) + (sin(toRadians(rotation.get(Y) - 90)) * -1 * offset.get(X)));
            position.set(Z, position.get(Z) + (cos(toRadians(rotation.get(Y) - 90)) * offset.get(X)));
        }
        position.set(Y, position.get(Y) + offset.get(Y));
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public void moveRotation(Vector3 offset) {
        rotation = (Vector3) rotation.add(offset);
    }

    public String toString() {
        return "Camera Position: " + getPosition();
    }
}
