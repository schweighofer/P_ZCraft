package at.pmzcraft.program.game;

import at.pmzcraft.exception.ShaderException;
import at.pmzcraft.exception.TextureException;
import at.pmzcraft.program.engine.Window;
import at.pmzcraft.program.engine.graphical.Texture;
import at.pmzcraft.program.engine.render.Mesh;
import at.pmzcraft.program.engine.render.Renderer;
import at.pmzcraft.program.engine.render.mathematical.vector.Vector;
import at.pmzcraft.program.game.world.gameitem.blocks.Block;
import at.pmzcraft.program.game.world.gameitem.blocks.blocktypes.*;

import java.io.IOException;
import java.nio.file.Path;

import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class PMZGame {

    private int displayXInc = 0;
    private int displayYInc = 0;
    private int displayZInc = 0;
    private int scaleInc = 0;
    private int rotationFactor = 100;

    private final Renderer renderer;
    private Block[] blocks;

    public PMZGame() {
        renderer = new Renderer();
    }

    public void init() throws ShaderException, TextureException,IOException {
        renderer.init();
        // Create the Mesh
        float[] positions = new float[] {
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,

                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,

                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,

                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,};
        Texture texture = new Texture(Path.of("src", "main", "resources", "game", "texture", "grass_block.png"));
        Mesh mesh = new Mesh(positions, textCoords, indices, texture);
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
