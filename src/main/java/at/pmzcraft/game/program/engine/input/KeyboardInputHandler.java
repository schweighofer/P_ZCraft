package at.pmzcraft.game.program.engine.input;

import at.pmzcraft.game.program.engine.Window;
import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;
import at.pmzcraft.game.program.game.PMZGame;

import static at.pmzcraft.game.program.engine.render.mathematical.vector.Vector.*;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInputHandler {

    private final PMZGame parent;

    public KeyboardInputHandler(PMZGame parent) {
        this.parent = parent;
    }

    // TODO: Locales and Keyboards
    public Vector inputMovement(Window window) {
        Vector v = new Vector();

        // forward - backward
        if (window.isKeyPressed(GLFW_KEY_W) && window.isKeyPressed(GLFW_KEY_S)) {

        } else if (window.isKeyPressed(GLFW_KEY_W)) {
            v.set(Z, 1);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            v.set(Z, -1);
        }

        // left - right
        if (window.isKeyPressed(GLFW_KEY_A) && window.isKeyPressed(GLFW_KEY_D)) {

        } else if (window.isKeyPressed(GLFW_KEY_A)) {
            v.set(X, 1);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            v.set(X, -1);
        }

        // up - down
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT) && window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {

        } else if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            v.set(Y, -1);
        } else if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            v.set(Y, 1);
        }

        return v;
    }

    public Vector inputRotation(Window window) {
        Vector v = new Vector();

        // up - down
        if (window.isKeyPressed(GLFW_KEY_UP) && window.isKeyPressed(GLFW_KEY_DOWN)) {

        } else if (window.isKeyPressed(GLFW_KEY_UP)) {
            v.set(Y, 10);
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            v.set(Y, -10);
        }

        // left - right
        if (window.isKeyPressed(GLFW_KEY_LEFT) && window.isKeyPressed(GLFW_KEY_RIGHT)) {

        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            v.set(X, 10);
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            v.set(X, -10);
        }

        return v;
    }
}
