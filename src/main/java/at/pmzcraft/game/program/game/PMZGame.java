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

    private static final float CAMERA_STEP_SENSITIVITY = 0.05f;
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

        Block b1 = new GrassBlock();
        b1.setScale(0.25f);
        b1.setPosition(0, -0.5f, -2.5f);

        Block b2 = new GrassBlock();
        b2.setScale(0.25f);
        b2.setPosition(0.5f, 0, -2);

        Block b3 = new GrassBlock();
        b3.setScale(0.25f);
        b3.setPosition(0, 0.5f, -2.5f);

        Block b4 = new GrassBlock();
        b4.setScale(0.25f);
        b4.setPosition(0.5f, 0.5f, -2.5f);

        blocks = new ArrayList<>();

        blocks.add(b1);
        blocks.add(b2);
        blocks.add(b3);
        blocks.add(b4);

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
}
