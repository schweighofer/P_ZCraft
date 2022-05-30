package at.pmzcraft.game.program.engine.render.mathematical.utils;

public class AngleUtils {

    public static final float PI = (float) (Math.PI);

    private static final float DEGREES_TO_RADIANS = (float) 0.017453292519943295;
    private static final float RADIANS_TO_DEGREES = (float) 57.29577951308232;

    public static float toRadians(float value) {
        return value * DEGREES_TO_RADIANS;
    }

    public static float toDegrees(float value) {
        return value * RADIANS_TO_DEGREES;
    }

    public static float sin(float value) {
        return (float) StrictMath.sin (value);
    }

    public static float cos(float value) {
        return (float) StrictMath.cos(value);
    }

    public static float tan(float value) {
        return (float) StrictMath.tan(value);
    }
}
