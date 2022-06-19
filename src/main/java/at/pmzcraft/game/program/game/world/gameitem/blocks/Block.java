package at.pmzcraft.game.program.game.world.gameitem.blocks;

import at.pmzcraft.game.program.engine.render.Mesh;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4;

import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4.*;

public abstract class Block {
    protected BlockType type = BlockType.UNKNOWN;

    private final Mesh mesh;

    private final Vector4 position;
    private float scale;
    private final Vector4 rotation;

    public Block(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector4();
        scale = 1;
        rotation = new Vector4();
    }

    public Vector4 getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(X, x);
        this.position.set(Y, y);
        this.position.set(Z, z);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector4 getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(X, x);
        this.rotation.set(Y, y);
        this.rotation.set(Z, z);
    }

    public Mesh getMesh() {
        return mesh;
    }
}
