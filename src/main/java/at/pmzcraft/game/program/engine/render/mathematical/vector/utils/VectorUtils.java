package at.pmzcraft.game.program.engine.render.mathematical.vector.utils;

import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4;

import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4.SIZE;

public class VectorUtils {

    public static float mathDotProduct(Vector4 v1, Vector4 v2) {
        float dp = 0.0f;
        for (int i = 0; i < SIZE; i++) {
            dp += v1.get(i) * v2.get(i);
        }
        return dp;
    }

    public static float length(Vector4 v) {
        return (float)Math.sqrt(mathDotProduct(v, v));
    }

    public static Vector4 normalize(Vector4 v) {
        float length = length(v);
        Vector4 v1 = new Vector4();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, v.get(i) / length);
        }
        return v1;
    }

    public static Vector4 mathCrossProduct(Vector4 v1, Vector4 v2) {
        Vector4 v = new Vector4();
        for (int i = 0; i < SIZE; i++) {
            v.set(i, (v1.get((i + 1) % SIZE) * v2.get(i + 2) % SIZE) - (v1.get(i + 2) % SIZE) * v2.get(i + 1) % SIZE);
        }
        return v;
    }
}
