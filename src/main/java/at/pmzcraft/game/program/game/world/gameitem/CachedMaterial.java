package at.pmzcraft.game.program.game.world.gameitem;

import at.pmzcraft.game.program.engine.render.Material;

public class CachedMaterial {
    private static float reflectance = 1;
    private static Material material;

    public static void init() {
        material = new Material(CachedTextureMap.getTextureMap(), reflectance);
    }

    public static Material getMaterial() {
        return material;
    }
}
