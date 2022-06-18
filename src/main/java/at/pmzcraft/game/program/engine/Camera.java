package at.pmzcraft.game.program.engine;

import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;

import static at.pmzcraft.game.program.engine.render.mathematical.utils.AngleUtils.*;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.Vector.*;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.VectorUtils.*;


public class Camera {

    private Vector position;
    private Vector rotation;

    public Camera() {
        position = new Vector();
        rotation = new Vector();
    }

    public Camera(Vector position, Vector rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = (Vector) position.clone();
    }

    public void movePosition(Vector offset) {
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

    public Vector getRotation() {
        return rotation;
    }

    public void setRotation(Vector rotation) {
        this.rotation = rotation;
    }

    public void moveRotation(Vector offset) {
        rotation = add(rotation, offset);
    }

    public String toString() {
        return "Camera Position: " + getPosition();
    }
}
