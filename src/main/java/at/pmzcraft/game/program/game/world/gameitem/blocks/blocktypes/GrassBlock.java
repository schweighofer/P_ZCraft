package at.pmzcraft.game.program.game.world.gameitem.blocks.blocktypes;

import at.pmzcraft.game.program.engine.render.Mesh;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;
import at.pmzcraft.game.program.game.world.gameitem.blocks.BlockType;
import at.pmzcraft.game.program.game.world.gameitem.CachedMesh;

public class GrassBlock extends Block {

    public GrassBlock() {
        super();
        this.type = BlockType.GRASS_BLOCK;
        Mesh mesh = CachedMesh.obtain(type);
        setMesh(mesh);
    }


}
