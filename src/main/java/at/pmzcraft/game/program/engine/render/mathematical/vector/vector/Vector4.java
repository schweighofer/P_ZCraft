package at.pmzcraft.game.program.engine.render.mathematical.vector.vector;

import at.pmzcraft.game.program.engine.render.mathematical.MathematicalCloneable;
import at.pmzcraft.game.program.engine.render.mathematical.MathematicalStructure;
import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;

import java.util.Arrays;

public class Vector4 extends Vector implements MathematicalCloneable {
    public static final int SIZE = 4;

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int W = 3;

    /**
     * @return Nullvector
     */

    public Vector4() {
        super(SIZE);
        coordinates[X] = 0.0f;
        coordinates[Y] = 0.0f;
        coordinates[Z] = 0.0f;
        coordinates[W] = 0.0f;
    }

    public Vector4(float x, float y, float z, float w) {
        super(SIZE);
        coordinates[X] = x;
        coordinates[Y] = y;
        coordinates[Z] = z;
        coordinates[W] = w;
    }

    public Vector4(float[] coordinates) {
        super(SIZE);
        this.coordinates[X] = coordinates[X];
        this.coordinates[Y] = coordinates[Y];
        this.coordinates[Z] = coordinates[Z];
        this.coordinates[W] = coordinates[W];
    }

    @Override
    public MathematicalStructure clone() {
        return new Vector4(Arrays.copyOf(coordinates, coordinates.length));
    }

    @Override
    public Vector add(Vector v2) {
        Vector4 v = new Vector4();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) + v2.get(i));
        }
        return v;
    }

    @Override
    public Vector sub(Vector v2) {
        Vector4 v = new Vector4();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) - v2.get(i));
        }
        return v;
    }

    @Override
    public Vector mul(Vector v2) {
        Vector4 v = new Vector4();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) * v2.get(i));
        }
        return v;
    }

    @Override
    public Vector div(Vector v2) {
        Vector4 v = new Vector4();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) / v2.get(i));
        }
        return v;
    }

    @Override
    public Vector mathScalarProduct(float scalar) {
        Vector4 res = new Vector4();
        for (int i = 0; i < SIZE; i++) {
            res.set(i, this.get(i) * scalar);
        }
        return res;
    }
}

