package at.pmzcraft.program.engine.utils;

import at.pmzcraft.program.engine.PMZGameController;

public class Synchronizer {

    private final int TARGET_FPS = 60;
    private final int TARGET_UPS = 30;

    private final Timer timer;
    private final PMZGameController controller;

    private double elapsedTime;
    private double accumulator = 0.0d;
    private float interval = 1.0f / TARGET_UPS;

    public Synchronizer(PMZGameController controller, Timer timer) {
        this.controller = controller;
        this.timer = timer;
    }

    public void updateElapsedTime() {
        elapsedTime = timer.getElapsedTime();
        accumulator += elapsedTime;
    }

    public void fulfillInterval() {
        while (accumulator >= interval) {
            // Update game state, makes more sense to keep this method in the controller
            controller.update(interval);
            accumulator -= interval;
        }
    }

    public void synchronize() throws InterruptedException {
        double loopSlot = 1.0d / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            Thread.sleep(1);
        }
    }
}
