package at.pmzcraft.game.exception.shader;

import at.pmzcraft.game.exception.general.ShaderException;

public class UniformNotFoundException extends ShaderException {

    public UniformNotFoundException(String message) {
        super(message);
    }
}
