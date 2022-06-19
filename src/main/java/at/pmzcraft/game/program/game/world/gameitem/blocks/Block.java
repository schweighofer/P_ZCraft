package at.pmzcraft.game.program.game.world.gameitem.blocks;

import at.pmzcraft.game.program.engine.render.Mesh;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3;

import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3.*;

public abstract class Block {
    protected BlockType type = BlockType.UNKNOWN;

    private Mesh mesh;

    private final Vector3 position;
    private float scale;
    private final Vector3 rotation;

    public Block() {
        position = new Vector3();
        scale = 1;
        rotation = new Vector3();
    }

    public Vector3 getPosition() {
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

    public Vector3 getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(X, x);
        this.rotation.set(Y, y);
        this.rotation.set(Z, z);
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
