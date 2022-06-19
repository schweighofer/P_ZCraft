package at.pmzcraft.game.program.game;

import at.pmzcraft.game.exception.general.ShaderException;
import at.pmzcraft.game.exception.general.TextureException;
import at.pmzcraft.game.program.engine.Camera;
import at.pmzcraft.game.program.engine.Window;
import at.pmzcraft.game.program.engine.input.KeyboardInputHandler;
import at.pmzcraft.game.program.engine.render.*;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3;
import at.pmzcraft.game.program.engine.utils.PropertyLoader;
import at.pmzcraft.game.program.game.world.gameitem.CachedMaterial;
import at.pmzcraft.game.program.game.world.gameitem.CachedTextureMap;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;
import at.pmzcraft.game.program.game.world.gameitem.CachedMesh;
import at.pmzcraft.game.program.game.world.gameitem.blocks.blocktypes.GrassBlock;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PMZGame {

    private Vector3 cameraPosition;
    private Vector3 cameraRotation;
    private final Camera camera;

    private static final float CAMERA_STEP_SENSITIVITY = 0.1f;
    private static final float CAMERA_ROTATION_SENSITIVITY = 0.2f;

    private final KeyboardInputHandler keyboardInputHandler;

    private final Renderer renderer;
    private List<Block> blocks;

    private Vector3 ambientLight;
    private PointLight pointLight;

    public PMZGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraPosition = new Vector3();
        cameraRotation = new Vector3();
        keyboardInputHandler = new KeyboardInputHandler(this);
    }

    public void init() throws ShaderException, TextureException, IOException {
        renderer.init();

        CachedTextureMap.init(PropertyLoader.getPath("texture_map_path"));
        CachedMaterial.init();
        CachedMesh.init(PropertyLoader.getPath("mesh_path"));

        blocks = new ArrayList<>();

        ambientLight = new Vector3(0.3f, 0.3f, 0.3f);
        Vector3 lightColour = new Vector3(1, 1, 1);
        Vector3 lightPosition = new Vector3(0, 0, 1);
        float lightIntensity = 1.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

    }

    public void input(Window window) {
        cameraPosition = keyboardInputHandler.inputMovement(window);
        cameraRotation = keyboardInputHandler.inputRotation(window);
    }

    public void update(float interval) {
        camera.movePosition((Vector3) cameraPosition.mathScalarProduct(CAMERA_STEP_SENSITIVITY));
        //camera.moveRotation((Vector3) cameraRotation.mathScalarProduct(CAMERA_ROTATION_SENSITIVITY));
    }

    public void render(Window window) {
        renderer.render(window, camera, blocks.toArray(Block[]::new), ambientLight, pointLight);
    }

    public void cleanup() {
        renderer.cleanup();
        for (Block block : blocks) {
            block.getMesh().cleanup();
        }
    }

    public Vector3 getCameraPosition() {
        return cameraPosition;
    }

    public Camera getCamera() {
        return camera;
    }





    ////// STARTING ANIMATION

    public void startingAnimation() {

        // P
        createBlock(-2,-0.5f,0);
        createBlock(-2,0,0);
        createBlock(-2,0.5f,0);
        createBlock(-2,1,0);
        createBlock(-2,1.5f,0);
        createBlock(-1.5f,1.5f,0);
        createBlock(-1,1.5f,0);
        createBlock(-1,1,0);
        createBlock(-1,0.5f,0);
        createBlock(-1.5f,0.5f,0);

        // *
        createBlock(0,1.5f,0);
        createBlock(-0.35f,1.25f,0);
        createBlock(0.35f,1.25f,0);
        createBlock(0.25f,0.8f,0);
        createBlock(-0.25f,0.8f,0);

        // Z
        createBlock(1,-0.5f,0);
        createBlock(1.5f,-0.5f,0);
        createBlock(2,-0.5f,0);
        createBlock(2,0,0);
        createBlock(1.5f,0.5f,0);
        createBlock(1,1,0);
        createBlock(1,1.5f,0);
        createBlock(1.5f,1.5f,0);
        createBlock(2,1.5f,0);
    }

    public void afterStartingAnimation() {
        blocks.clear();
    }

    public void createBlock(float x, float y, float z) {
        Block block = new GrassBlock();
        block.setScale(0.25f);
        block.setPosition(x, y, z);
        blocks.add(block);
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
