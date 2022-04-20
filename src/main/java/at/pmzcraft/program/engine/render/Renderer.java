package at.pmzcraft.program.engine.render;


import at.pmzcraft.exception.ShaderException;
import at.pmzcraft.exception.shader.*;
import at.pmzcraft.program.engine.Window;
import at.pmzcraft.program.engine.graphical.ShaderProgram;
import at.pmzcraft.program.engine.graphical.Transformation;
import at.pmzcraft.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.program.engine.utils.ResourceLoader;
import at.pmzcraft.program.game.world.gameitem.blocks.Block;
import org.joml.Matrix4f;

import java.io.IOException;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
public class Renderer {

    private final float FOV = (float) Math.toRadians(60.0);
    private final float Z_NEAR = 0.01f;
    private final float Z_FAR = 1000f;

    private Transformation transformation;

    private ShaderProgram shaderProgram;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init() throws ShaderException, IOException {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(ResourceLoader.load(Path.of("src", "main", "resources", "game", "vertex.vs")));
        shaderProgram.createFragmentShader(ResourceLoader.load(Path.of("src", "main", "resources", "game", "fragment.fs")));
        shaderProgram.link();

        // Create uniforms for world and projection matrices
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Block[] blocks) {
        clear();

        if (window.wasResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
        }

        shaderProgram.bind();
        //System.out.println(window.getWidth() + " / " + window.getHeight() + " | " + ((float)window.getWidth() / (float)window.getHeight()));
        Matrix projectionMatrix = transformation.getProjectionMatrix(FOV, (float) window.getWidth() / (float) window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        //System.out.println(projectionMatrix);

        for (Block block : blocks) {
            Matrix worldMatrix = transformation.getWorldMatrix(block.getPosition(), block.getRotation(), block.getScale());
            shaderProgram.setUniform("worldMatrix", worldMatrix);
            block.getMesh().render();
        }

        shaderProgram.unbind();
    }


    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
