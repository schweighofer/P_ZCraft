package at.pmzcraft.game.program.game;

import at.pmzcraft.game.exception.general.ShaderException;
import at.pmzcraft.game.exception.general.TextureException;
import at.pmzcraft.game.program.engine.Camera;
import at.pmzcraft.game.program.engine.Window;
import at.pmzcraft.game.program.engine.input.KeyboardInputHandler;
import at.pmzcraft.game.program.engine.render.Texture;
import at.pmzcraft.game.program.engine.render.Mesh;
import at.pmzcraft.game.program.engine.render.Renderer;
import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;
import at.pmzcraft.game.program.engine.render.mathematical.vector.VectorUtils;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;
import at.pmzcraft.game.program.game.world.gameitem.blocks.blocktypes.*;

import java.io.IOException;
import java.nio.file.Path;

public class PMZGame {

    private Vector cameraPosition;
    private Vector cameraRotation;
    private final Camera camera;

    private static final float CAMERA_STEP_SENSITIVITY = 0.05f;
    private static final float CAMERA_ROTATION_SENSITIVITY = 0.2f;

    private final KeyboardInputHandler keyboardInputHandler;

    private final Renderer renderer;
    private Block[] blocks;

    public PMZGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraPosition = new Vector();
        cameraRotation = new Vector();
        keyboardInputHandler = new KeyboardInputHandler(this);
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

        Block b1 = new GrassBlock(mesh);
        b1.setScale(0.5f);
        b1.setPosition(0, 0, -2);

        Block b2 = new GrassBlock(mesh);
        b2.setScale(0.5f);
        b2.setPosition(0.5f, 0, -2);

        Block b3 = new GrassBlock(mesh);
        b3.setScale(0.5f);
        b3.setPosition(0, 0.5f, -2.5f);

        Block b4 = new GrassBlock(mesh);
        b4.setScale(0.5f);
        b4.setPosition(0.5f, 0.5f, -2.5f);

        blocks = new Block[] {b1, b2, b3, b4};
    }

    public void input(Window window) {
        cameraPosition = keyboardInputHandler.inputMovement(window);
        //cameraRotation = keyboardInputHandler.inputRotation(window);
    }

    public void update(float interval) {
        camera.movePosition(VectorUtils.mathScalarProduct(cameraPosition, CAMERA_STEP_SENSITIVITY));
        //camera.moveRotation(VectorUtils.mathScalarProduct(cameraRotation, CAMERA_ROTATION_SENSITIVITY));
        // Other stuff mouse movement ersatz haha
    }

    public void render(Window window) {
        renderer.render(window, camera, blocks);
    }

    public void cleanup() {
        renderer.cleanup();
        for (Block block : blocks) {
            block.getMesh().cleanup();
        }
    }

    public Vector getCameraPosition() {
        return cameraPosition;
    }

    public Camera getCamera() {
        return camera;
    }
}
