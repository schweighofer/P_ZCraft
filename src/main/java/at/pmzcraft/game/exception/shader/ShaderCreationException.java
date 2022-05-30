package at.pmzcraft.game.exception.shader;

import at.pmzcraft.game.exception.general.ShaderException;

public class ShaderCreationException extends ShaderException {

    public ShaderCreationException(int type) {
        super(type + "");
    }
}
