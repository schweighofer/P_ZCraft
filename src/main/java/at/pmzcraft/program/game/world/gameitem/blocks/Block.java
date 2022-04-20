package at.pmzcraft.program.game.world.gameitem.blocks;

import at.pmzcraft.program.engine.render.Mesh;
import at.pmzcraft.program.engine.render.mathematical.vector.Vector;

import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.*;

public abstract class Block {
    protected BlockType type = BlockType.UNKNOWN;

    private final Mesh mesh;

    private final Vector position;
    private float scale;
    private final Vector rotation;

    public Block(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector();
        scale = 1;
        rotation = new Vector();
    }

    public Vector getPosition() {
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

    public Vector getRotation() {
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
