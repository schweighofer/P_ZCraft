package at.pmzcraft.game.program.engine;

import at.pmzcraft.game.exception.PMZException;
import at.pmzcraft.game.exception.general.ShaderException;
import at.pmzcraft.game.exception.general.TextureException;
import at.pmzcraft.game.exception.general.WindowException;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3;
import at.pmzcraft.game.program.engine.utils.Synchronizer;
import at.pmzcraft.game.program.engine.utils.Timer;
import at.pmzcraft.game.program.game.PMZGame;
import at.pmzcraft.menu.Master;
import org.lwjgl.Version;

import java.io.IOException;

/**
 * Copyright: Marcus Schweighofer
*/

public class PMZEngine implements Runnable {

    // Window (window handle wrapper)
    private final Window window;

    // Loop Optimization Utils
    private final Timer timer;
    private final Synchronizer synchronizer;

    // Concrete BL
    private final PMZGame logicImplementation;

    public PMZEngine(String title, int width, int height, boolean isVSyncEnabled, PMZGame logicImplementation) {
        window = new Window(title, width, height, isVSyncEnabled);
        this.logicImplementation = logicImplementation;
        timer = new Timer();
        synchronizer = new Synchronizer(this, timer);
    }

    public void run() {
        System.out.println("LWJGL is running with version: " + Version.getVersion());

        // Core components: init and loop

        try {
            init();

            /// STARTING ANIMATION
            startingAnimation();

            loop();
        } catch (PMZException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Cleanup the shader program
            cleanup();
            // Cleanup the window after the game is done
            window.close();

            // Stop musikant
            Thread thisThread = Thread.currentThread();
            System.out.println(thisThread.getName() + " goes bye-bye now");
            thisThread.interrupt();
            // Program goes bye-bye >:)
             Master.stopProgram();
        }
    }

    private void init() throws WindowException, ShaderException, TextureException, IOException {
        window.init();
        timer.init();
        logicImplementation.init();
    }



    private void loop() throws InterruptedException {
        double elapsedTime;
        double accumulator = 0d;
        float interval = 1.0f / 30.0f;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            synchronizer.updateElapsedTime();

            input();

            // this method also handles the updates
            synchronizer.fulfillInterval();

            render();

            if (!window.isVSyncEnabled()) {
                synchronizer.synchronize();
            }
        }
    }

    public void input() {
        logicImplementation.input(window);
    }

    public void update(float interval) {
        logicImplementation.update(interval);
    }

    public void render() {
        logicImplementation.render(window);
        window.update();
    }

    public void cleanup() {
        logicImplementation.cleanup();
    }


    ////// STARTING ANIMATION
    private void startingAnimation() {
        logicImplementation.startingAnimation();
        for (int i = 0; i < 100; i++) {
            logicImplementation.getCamera().movePosition(new Vector3(0,-0.02f,-0.125f));
            render();
            logicImplementation.sleep(10);
        }
        logicImplementation.sleep(2000);
        logicImplementation.afterStartingAnimation();

        logicImplementation.createBlock(0,0,0);
        logicImplementation.createBlock(0.5f,0,0);
        logicImplementation.createBlock(0.5f,0.5f,-0.5f);
        logicImplementation.createBlock(0,0.5f,-0.5f);
    }
}
