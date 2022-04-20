package at.pmzcraft.exception.shader;

import at.pmzcraft.exception.ShaderException;

public class ShaderCreationException extends ShaderException {

    public ShaderCreationException(int type) {
        super(type + "");
    }
}
