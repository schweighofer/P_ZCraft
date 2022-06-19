package at.pmzcraft.game.program.engine.render.mathematical.vector;

import at.pmzcraft.game.program.engine.render.mathematical.MathematicalStructure;

import java.util.Arrays;

public abstract class Vector extends MathematicalStructure {
    public static final int SIZE = -1;

    protected float[] coordinates;

    /**
     * @return Nullvector
     */
    public Vector(int size) {
        coordinates = new float[size];
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
        throw new RuntimeException("YOu cant clone this :(");
    }

    public abstract Vector add(Vector v2);
    public abstract Vector sub(Vector v2);
    public abstract Vector mul(Vector v2);
    public abstract Vector div(Vector v2);
    public abstract Vector mathScalarProduct(float scalar);
}
