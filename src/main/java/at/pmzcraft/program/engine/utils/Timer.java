package at.pmzcraft.program.engine.utils;

// Timer Util for game loop
public class Timer {

    private double lastLoopTime;

    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1_000_000_000.0d;
    }

    public float getElapsedTime() {
        double currentTime = getTime();
        float elapseTime = (float)(currentTime - lastLoopTime);
        lastLoopTime = currentTime;
        return elapseTime;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
