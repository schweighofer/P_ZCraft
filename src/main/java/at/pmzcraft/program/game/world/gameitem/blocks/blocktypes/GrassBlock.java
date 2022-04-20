package at.pmzcraft.program.game.world.gameitem.blocks.blocktypes;

import at.pmzcraft.program.engine.render.Mesh;
import at.pmzcraft.program.game.world.gameitem.blocks.Block;
import at.pmzcraft.program.game.world.gameitem.blocks.BlockType;

public class GrassBlock extends Block {

    public GrassBlock(Mesh mesh) {
        super(mesh);
        type = BlockType.GRASS_BLOCK;
    }
}
