package at.pmzcraft.game.program.game.world.gameitem;

import at.pmzcraft.game.exception.general.TextureException;
import at.pmzcraft.game.program.engine.render.Texture;

import java.nio.file.Path;

public class CachedTextureMap {

    private static Texture textureMap;

    public static void init(Path filePath) throws TextureException {
        textureMap = new Texture(filePath);
    }

    public static Texture getTextureMap() {
        return textureMap;
    }
}
