package at.pmzcraft.program.game;

import at.pmzcraft.exception.ShaderException;
import at.pmzcraft.program.engine.Window;
import at.pmzcraft.program.engine.render.Mesh;
import at.pmzcraft.program.engine.render.Renderer;
import at.pmzcraft.program.engine.render.mathematical.vector.Vector;
import at.pmzcraft.program.game.world.gameitem.blocks.Block;
import at.pmzcraft.program.game.world.gameitem.blocks.blocktypes.*;

import java.io.IOException;

import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class PMZGameHandler {

    private int displayXInc = 0;
    private int displayYInc = 0;
    private int displayZInc = 0;
    private int scaleInc = 0;
    private int rotationFactor = 100;

    private final Renderer renderer;
    private Block[] blocks;

    public PMZGameHandler() {
        renderer = new Renderer();
    }

    public void init() throws ShaderException, IOException {
        renderer.init();
        float[] positions = new float[] {
                // VO
                -0.5f,  0.5f,  0.5f,
                // V1
                -0.5f, -0.5f,  0.5f,
                // V2
                0.5f, -0.5f,  0.5f,
                // V3
                0.5f,  0.5f,  0.5f,
                // V4
                -0.5f,  0.5f, -0.5f,
                // V5
                0.5f,  0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[] {
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };
        Mesh mesh = new Mesh(positions, colors, indices);
        Block block = new GrassBlock(mesh);
        block.setPosition(0, 0, -2);
        blocks = new Block[] {block};
    }

    public void input(Window window) {
        displayYInc = 0;
        displayXInc = 0;
        displayZInc = 0;
        scaleInc = 0;
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            displayYInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            displayYInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            displayXInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            displayXInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_A)) {
            displayZInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            displayZInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_Z)) {
            scaleInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            scaleInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_W)) {
            rotationFactor++;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            rotationFactor--;
        }
    }

    public void update(float interval) {
        for (Block block : blocks) {
            // Update position
            Vector pos = block.getPosition();
            float posX = pos.get(X) + displayXInc * 0.01f;
            float posY = pos.get(Y) + displayYInc * 0.01f;
            float posZ = pos.get(Z) + displayZInc * 0.01f;
            block.setPosition(posX, posY, posZ);

            // Update scale
            float scale = block.getScale();
            scale += scaleInc * 0.05f;
            if ( scale < 0 ) {
                scale = 0;
            }
            block.setScale(scale);

            // Update rotation angle
            float rotation = block.getRotation().get(Z) + (rotationFactor / 100);
            if ( rotation > 360 ) {
                rotation = 0;
            }
            block.setRotation(rotation, rotation, rotation);
        }
    }

    public void render(Window window) {
        if (window.wasResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
        }
        
        renderer.render(window, blocks);
    }

    public void cleanup() {
        renderer.cleanup();
        for (Block block : blocks) {
            block.getMesh().cleanup();
        }
    }
}
