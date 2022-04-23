package at.pmzcraft.program.engine.render.mathematical.vector;

import java.util.Arrays;

public class Vector {
    public static  final int SIZE = 4;

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int W = 3;

    private float[] coordinates = new float[4];

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
}

