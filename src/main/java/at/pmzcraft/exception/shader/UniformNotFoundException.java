package at.pmzcraft.exception.shader;

import at.pmzcraft.exception.ShaderException;

public class UniformNotFoundException extends ShaderException {

    public UniformNotFoundException(String message) {
        super(message);
    }
}
