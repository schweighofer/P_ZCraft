package at.pmzcraft.program.engine.render.mathematical.vector;

import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.SIZE;

public class VectorUtils {

    public static Vector add(Vector v1, Vector v2) {
        Vector v = new Vector();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, v1.get(i) + v2.get(i));
        }
        return v;
    }

    public static Vector sub(Vector v1, Vector v2) {
        Vector v = new Vector();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, v1.get(i) - v2.get(i));
        }
        return v;
    }

    public static Vector mul(Vector v1, Vector v2) {
        Vector v = new Vector();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, v1.get(i) * v2.get(i));
        }
        return v;
    }

    public static Vector div(Vector v1, Vector v2) {
        Vector v = new Vector();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, v1.get(i) / v2.get(i));
        }
        return v;
    }

    public static float mathDotProduct(Vector v1, Vector v2) {
        float dp = 0.0f;
        for (int i = 0; i < SIZE; i++) {
            dp += v1.get(i) * v2.get(i);
        }
        return dp;
    }

    public static float length(Vector v) {
        return (float)Math.sqrt(mathDotProduct(v, v));
    }

    public static Vector normalize(Vector v) {
        float length = length(v);
        Vector v1 = new Vector();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, v.get(i) / length);
        }
        return v1;
    }

    public static Vector mathCrossProduct(Vector v1, Vector v2) {
        Vector v = new Vector();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, (v1.get((i + 1) % SIZE) * v2.get(i + 2) % SIZE) - (v1.get(i + 2) % SIZE) * v2.get(i + 1) % SIZE);
        }
        return v;
    }
}
