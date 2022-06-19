package at.pmzcraft.game.program.engine;

import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4;

import static at.pmzcraft.game.program.engine.render.mathematical.utils.AngleUtils.*;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4.*;


public class Camera {

    private Vector4 position;
    private Vector4 rotation;

    public Camera() {
        position = new Vector4();
        rotation = new Vector4();
    }

    public Camera(Vector4 position, Vector4 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector4 getPosition() {
        return position;
    }

    public void setPosition(Vector4 position) {
        this.position = (Vector4) position.clone();
    }

    public void movePosition(Vector4 offset) {
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

    public Vector4 getRotation() {
        return rotation;
    }

    public void setRotation(Vector4 rotation) {
        this.rotation = rotation;
    }

    public void moveRotation(Vector4 offset) {
        rotation = (Vector4) rotation.add(offset);
    }

    public String toString() {
        return "Camera Position: " + getPosition();
    }
}
