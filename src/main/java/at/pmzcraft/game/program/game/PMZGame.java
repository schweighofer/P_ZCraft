package at.pmzcraft.game.program.game;

import at.pmzcraft.game.exception.general.ShaderException;
import at.pmzcraft.game.exception.general.TextureException;
import at.pmzcraft.game.program.engine.Camera;
import at.pmzcraft.game.program.engine.Window;
import at.pmzcraft.game.program.engine.input.KeyboardInputHandler;
import at.pmzcraft.game.program.engine.render.*;
import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;
import at.pmzcraft.game.program.engine.render.mathematical.vector.VectorUtils;
import at.pmzcraft.game.program.engine.utils.OBJLoader;
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

    private Vector ambientLight;
    private PointLight pointLight;

    public PMZGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraPosition = new Vector();
        cameraRotation = new Vector();
        keyboardInputHandler = new KeyboardInputHandler(this);
    }

    public void init() throws ShaderException, TextureException, IOException {
        renderer.init();
        float reflectance = 1;
        Texture texture = new Texture(Path.of("src", "main", "resources", "game", "texture", "texture_map.png"));
        Mesh mesh = OBJLoader.loadMesh(Path.of("src", "main", "resources", "game", "model", "bunny.obj"));
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);

        Block b1 = new GrassBlock(mesh);
        b1.setScale(0.5f);
        b1.setPosition(0, -0.5f, -2.5f);

        Block b2 = new GrassBlock(mesh);
        b2.setScale(0.5f);
        b2.setPosition(0.5f, 0, -2);

        Block b3 = new GrassBlock(mesh);
        b3.setScale(0.5f);
        b3.setPosition(0, 0.5f, -2.5f);

        Block b4 = new GrassBlock(mesh);
        b4.setScale(0.5f);
        b4.setPosition(0.5f, 0.5f, -2.5f);

        //blocks = new Block[] {b1, b2, b3, b4};
        blocks = new Block[] {b1};

        ambientLight = new Vector(0.3f, 0.3f, 0.3f, 0);
        Vector lightColour = new Vector(1, 1, 1, 0);
        Vector lightPosition = new Vector(0, 0, 1, 0);
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
        camera.movePosition(VectorUtils.mathScalarProduct(cameraPosition, CAMERA_STEP_SENSITIVITY));
        camera.moveRotation(VectorUtils.mathScalarProduct(cameraRotation, CAMERA_ROTATION_SENSITIVITY));
    }

    public void render(Window window) {
        renderer.render(window, camera, blocks, ambientLight, pointLight);
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
