package at.pmzcraft.game.program.engine.render.mathematical.vector.vector;

import at.pmzcraft.game.program.engine.render.mathematical.MathematicalCloneable;
import at.pmzcraft.game.program.engine.render.mathematical.MathematicalStructure;
import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;

import java.util.Arrays;

public class Vector3 extends Vector implements MathematicalCloneable {
    public static final int SIZE = 3;

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;

    public Vector3() {
        super(SIZE);
        coordinates[X] = 0.0f;
        coordinates[Y] = 0.0f;
        coordinates[Z] = 0.0f;
    }

    public Vector3(float x, float y, float z) {
        super(SIZE);
        coordinates[X] = x;
        coordinates[Y] = y;
        coordinates[Z] = z;
    }

    public Vector3(float[] coordinates) {
        super(SIZE);
        this.coordinates[X] = coordinates[X];
        this.coordinates[Y] = coordinates[Y];
        this.coordinates[Z] = coordinates[Z];
    }

    @Override
    public MathematicalStructure clone() {
        return new Vector3(Arrays.copyOf(coordinates, coordinates.length));
    }

    @Override
    public Vector add(Vector v2) {
        Vector3 v = new Vector3();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) + v2.get(i));
        }
        return v;
    }

    @Override
    public Vector sub(Vector v2) {
        Vector3 v = new Vector3();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) - v2.get(i));
        }
        return v;
    }

    @Override
    public Vector mul(Vector v2) {
        Vector3 v = new Vector3();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) * v2.get(i));
        }
        return v;
    }

    @Override
    public Vector div(Vector v2) {
        Vector3 v = new Vector3();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, this.get(i) / v2.get(i));
        }
        return v;
    }

    @Override
    public Vector mathScalarProduct(float scalar) {
        Vector3 res = new Vector3();
        for (int i = 0; i < SIZE; i++) {
            res.set(i, this.get(i) * scalar);
        }
        return res;
    }
}
