package at.pmzcraft.game.program.engine.render;


import at.pmzcraft.game.exception.general.ShaderException;
import at.pmzcraft.game.program.engine.Camera;
import at.pmzcraft.game.program.engine.Window;
import at.pmzcraft.game.program.engine.graphical.ShaderProgram;
import at.pmzcraft.game.program.engine.graphical.Transformation;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.game.program.engine.utils.ResourceLoader;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;

import java.io.IOException;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11.*;
public class Renderer {

    private final float FOV = (float) Math.toRadians(60.0);
    private final float Z_NEAR = 0.01f;
    private final float Z_FAR = 1000f;

    private Transformation transformation;

    private ShaderProgram shaderProgram;

    private final Path vertexShaderPath = Path.of("src", "main", "resources", "game", "shader", "vertex.vs");
    private final Path fragmentShaderPath = Path.of("src", "main", "resources", "game", "shader", "fragment.fs");

    public Renderer() {
        transformation = new Transformation();
    }

    public void init() throws ShaderException, IOException {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(ResourceLoader.load(vertexShaderPath));
        shaderProgram.createFragmentShader(ResourceLoader.load(fragmentShaderPath));
        shaderProgram.link();

        // Create uniforms for world and projection matrices
        shaderProgram.createUniform("m_projectionMatrix");
        shaderProgram.createUniform("m_modelViewMatrix");
        shaderProgram.createUniform("t_textureSampler");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, Block[] blocks) {
        clear();

        if (window.wasResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
        }

        shaderProgram.bind();

        // Update the projection matrix
        Matrix projectionMatrix = transformation.getProjectionMatrix(FOV, (float) window.getWidth() / (float) window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("m_projectionMatrix", projectionMatrix);

        // Update view matrix
        Matrix viewMatrix = transformation.getViewMatrix(camera);

        shaderProgram.setUniform("t_textureSampler", 0);

        for (Block block : blocks) {
            Matrix modelViewMatrix = transformation.getModelViewMatrix(block, viewMatrix);

            shaderProgram.setUniform("m_modelViewMatrix", modelViewMatrix);
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
