package at.pmzcraft.game.program.engine.render;


import at.pmzcraft.game.exception.general.ShaderException;
import at.pmzcraft.game.program.engine.Camera;
import at.pmzcraft.game.program.engine.Window;
import at.pmzcraft.game.program.engine.graphical.ShaderProgram;
import at.pmzcraft.game.program.engine.graphical.Transformation;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.MatrixUtils;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4;
import at.pmzcraft.game.program.engine.utils.PropertyLoader;
import at.pmzcraft.game.program.engine.utils.ResourceLoader;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;

import java.io.IOException;
import java.nio.file.Path;

import static at.pmzcraft.game.program.engine.render.mathematical.utils.AngleUtils.toRadians;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4.*;
import static org.lwjgl.opengl.GL11.*;
public class Renderer {

    private static final float FOV = toRadians(60);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000f;

    private final Transformation transformation;

    private ShaderProgram shaderProgram;

    private final float specularPower;

    private static final Path vertexShaderPath = PropertyLoader.getPath("vertex_shader_path");
    private static final Path fragmentShaderPath = PropertyLoader.getPath("fragment_shader_path");

    public Renderer() {
        transformation = new Transformation();
        specularPower = 10;
    }

    /**
     * Init the Renderer
     * */
    public void init() throws ShaderException, IOException {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(ResourceLoader.loadResource(vertexShaderPath));
        shaderProgram.createFragmentShader(ResourceLoader.loadResource(fragmentShaderPath));
        shaderProgram.link();

        // Create uniforms for world and projection matrices
        shaderProgram.createUniform("m_projectionMatrix");
        shaderProgram.createUniform("m_modelViewMatrix");
        shaderProgram.createUniform("t_textureSampler");

        // Create uniform for material
        shaderProgram.createMaterialUniform("l_material");
        // Create lighting related uniforms
        shaderProgram.createUniform("l_specularPower");
        shaderProgram.createUniform("l_ambientLight");
        shaderProgram.createPointLightUniform("l_pointLight");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Main Render Method (the magic happens here)
     * */
    public void render(Window window, Camera camera, Block[] blocks, Vector3 ambientLight, PointLight pointLight) {
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

        // Update Light Uniforms
        shaderProgram.setUniform("l_ambientLight", ambientLight);
        shaderProgram.setUniform("l_specularPower", specularPower);

        // Transform light object
        PointLight currPointLight = new PointLight(pointLight);
        Vector3 lightPos = currPointLight.getPosition();
        Vector4 aux = new Vector4(lightPos.get(X), lightPos.get(Y), lightPos.get(Z), 1);
        aux = MatrixUtils.multiplyMatrixVector(viewMatrix, aux);
        lightPos.set(X, aux.get(X));
        lightPos.set(Y, aux.get(Y));
        lightPos.set(Z, aux.get(Z));
        shaderProgram.setUniform("l_pointLight", currPointLight);

        shaderProgram.setUniform("t_textureSampler", 0);


        for (Block block : blocks) {
            Mesh mesh = block.getMesh();
            Matrix modelViewMatrix = transformation.getModelViewMatrix(block, viewMatrix, camera);

            shaderProgram.setUniform("m_modelViewMatrix", modelViewMatrix);
            shaderProgram.setUniform("l_material", mesh.getMaterial());
            block.getMesh().render();
        }

        shaderProgram.unbind();
    }

    /**
     * Cleanup the Shader Program
    * */
    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
