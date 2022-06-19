package at.pmzcraft.game.program.game.world.gameitem;

import at.pmzcraft.game.program.engine.render.Mesh;
import at.pmzcraft.game.program.engine.utils.OBJLoader;
import at.pmzcraft.game.program.game.world.gameitem.blocks.BlockType;

import java.io.IOException;
import java.nio.file.Path;

public class CachedMesh {
    private static Mesh blockMesh;

    public static void init(Path filePath) throws IOException {
        Mesh mesh = OBJLoader.loadMesh(Path.of(filePath.toString(), "block.obj"));
        mesh.setMaterial(CachedMaterial.getMaterial());
        blockMesh = mesh;
    }

    public static Mesh obtain(BlockType blockType) {
        return blockMesh;
    }
}
