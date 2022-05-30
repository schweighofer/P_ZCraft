package at.pmzcraft.game.program.engine.render.mathematical.vector;

import at.pmzcraft.game.program.engine.render.mathematical.MathematicalCloneable;
import at.pmzcraft.game.program.engine.render.mathematical.MathematicalStructure;

import java.util.Arrays;

public class Vector extends MathematicalStructure implements MathematicalCloneable {
    public static  final int SIZE = 4;

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int W = 3;

    private float[] coordinates = new float[4];

    /**
     * @return Nullvector
     */
    public Vector() {
        coordinates[X] = 0.0f;
        coordinates[Y] = 0.0f;
        coordinates[Z] = 0.0f;
        coordinates[W] = 1.0f;
    }

    public Vector(float x, float y, float z, float w) {
        coordinates[X] = x;
        coordinates[Y] = y;
        coordinates[Z] = z;
        coordinates[W] = w;
    }

    public Vector(float[] coordinates) {
        this.coordinates[X] = coordinates[X];
        this.coordinates[Y] = coordinates[Y];
        this.coordinates[Z] = coordinates[Z];
        this.coordinates[W] = 1.0f;
    }

    public float get(int index) {
        return coordinates[index];
    }

    public float[] getArray() {
        return coordinates;
    }

    public void set(int index, float value) {
        this.coordinates[index] = value;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "coordinates=" + Arrays.toString(coordinates) +
                '}';
    }


    @Override
    public MathematicalStructure clone() {
        return new Vector(Arrays.copyOf(coordinates, coordinates.length));
    }
}

