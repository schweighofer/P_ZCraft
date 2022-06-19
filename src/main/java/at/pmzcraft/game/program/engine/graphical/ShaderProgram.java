package at.pmzcraft.game.program.engine.graphical;

import at.pmzcraft.game.exception.shader.*;
import at.pmzcraft.game.program.engine.render.Material;
import at.pmzcraft.game.program.engine.render.PointLight;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4.*;
import static org.lwjgl.opengl.GL20.*;

// Shader handling for rendering
public class ShaderProgram {

    private final int programID;

    private int vertexShaderID;
    private int fragmentShaderID;

    private Map<String, Integer> uniforms;

    public ShaderProgram() throws ShaderProgramCreationException {
        // Try creating a OpenGL program for the shaders
        programID = glCreateProgram();

        if (programID == 0) {
            throw new ShaderProgramCreationException("Shader could not be created!");
        }

        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName) throws UniformNotFoundException {
        int uniformLocation = glGetUniformLocation(programID, uniformName);
        if (uniformLocation < 0) {
            throw new UniformNotFoundException(uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix value) {
        // Dump the matrix into a float buffer

        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(uniforms.get(uniformName), false, value.toFloatBuffer());
        }
    }

    public void setUniform(String uniformName, int value) {
        // Set uniform to simple integer value
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        // Set uniform to simple float value
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3 value) {
        glUniform3f(uniforms.get(uniformName), value.get(X), value.get(Y), value.get(Z));
    }

    public void setUniform(String uniformName, Vector4 value) {
        glUniform3f(uniforms.get(uniformName), value.get(X), value.get(Y), value.get(Z));
    }

    public void setUniform(String uniformName, PointLight pointLight) {
        setUniform(uniformName + ".colour", pointLight.getColor() );
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniform(uniformName + ".intensity", pointLight.getIntensity());
        PointLight.Attenuation att = pointLight.getAttenuation();
        setUniform(uniformName + ".att.constant", att.getConstant());
        setUniform(uniformName + ".att.linear", att.getLinear());
        setUniform(uniformName + ".att.exponent", att.getExponent());
    }

    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".ambient", material.getAmbientColour());
        setUniform(uniformName + ".diffuse", material.getDiffuseColour());
        setUniform(uniformName + ".specular", material.getSpecularColour());
        setUniform(uniformName + ".hasTexture", material.isTextured() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
    }

    public int createShader(String code, int type) throws ShaderCreationException, ShaderCompilationException {
        // Create shader
        int shaderID = glCreateShader(type);
        // Exception if shader was not created
        if (shaderID == 0) {
            throw new ShaderCreationException(type);
        }

        // OpenGL methods for compiling shader
        glShaderSource(shaderID, code);
        glCompileShader(shaderID);

        // Exception if shader could not be compiled
        // Shader-i :)
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == 0) {
            throw new ShaderCompilationException(glGetShaderInfoLog(shaderID));
        }

        // attach shader to OpenGL Program
        glAttachShader(programID, shaderID);

        return shaderID;
    }

    // Create a Vertex shader
    public void createVertexShader(String code) throws ShaderCreationException, ShaderCompilationException {
        vertexShaderID = createShader(code, GL_VERTEX_SHADER);
    }

    // Create a Fragment shader
    public void createFragmentShader(String code) throws ShaderCreationException, ShaderCompilationException {
        fragmentShaderID = createShader(code, GL_FRAGMENT_SHADER);
    }

    public void createPointLightUniform(String uniformName) throws UniformNotFoundException {
        createUniform(uniformName + ".colour");
        createUniform(uniformName + ".position");
        createUniform(uniformName + ".intensity");
        createUniform(uniformName + ".att.constant");
        createUniform(uniformName + ".att.linear");
        createUniform(uniformName + ".att.exponent");
    }

    public void createMaterialUniform(String uniformName) throws UniformNotFoundException {
        createUniform(uniformName + ".ambient");
        createUniform(uniformName + ".diffuse");
        createUniform(uniformName + ".specular");
        createUniform(uniformName + ".hasTexture");
        createUniform(uniformName + ".reflectance");
    }

    public void link() throws ShaderLinkingException, ShaderCodeValidationException {
        // OpenGL method for linking Program
        glLinkProgram(programID);

        // Exception if Program could not be linked
        if (glGetProgrami(programID, GL_LINK_STATUS) == 0) {
            throw new ShaderLinkingException(glGetProgramInfoLog(programID));
        }

        // Detach shaders if possible
        if (vertexShaderID != 0) {
            glDetachShader(programID, vertexShaderID);
        }
        if (fragmentShaderID != 0) {
            glDetachShader(programID, fragmentShaderID);
        }

        // Validate our OpenGL program
        glValidateProgram(programID);

        // Runtime Exception if validation fails
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0) {
            throw new ShaderCodeValidationException(glGetProgramInfoLog(programID));
        }
    }

    // Bind program to context, so that this shader program is used in rendering
    public void bind() {
        glUseProgram(programID);
    }

    // Go back to the default program
    public void unbind() {
        glUseProgram(0);
    }

    // clean up the mess that this program left behind now that it's no longer in use
    public void cleanup() {
        unbind();
        // delete the program itself if possible
        if (programID != 0) {
            glDeleteProgram(programID);
        }
    }
}
