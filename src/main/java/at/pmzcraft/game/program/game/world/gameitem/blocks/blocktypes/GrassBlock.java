package at.pmzcraft.game.program.game.world.gameitem.blocks.blocktypes;

import at.pmzcraft.game.program.engine.render.Mesh;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;
import at.pmzcraft.game.program.game.world.gameitem.blocks.BlockType;

public class GrassBlock extends Block {

    public GrassBlock(Mesh mesh) {
        super(mesh);
        type = BlockType.GRASS_BLOCK;
    }
}
