package at.pmzcraft.game.program.engine;

import at.pmzcraft.game.exception.PMZException;
import at.pmzcraft.game.exception.general.ShaderException;
import at.pmzcraft.game.exception.general.TextureException;
import at.pmzcraft.game.exception.general.WindowException;
import at.pmzcraft.game.program.engine.utils.Synchronizer;
import at.pmzcraft.game.program.engine.utils.Timer;
import at.pmzcraft.game.program.game.PMZGame;
import org.lwjgl.Version;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

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
            loop();
        } catch (PMZException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Cleanup the shader program
            cleanup();
            // Cleanup the window after the game is done
            window.close();

            //Menu.setVisibleAgain();

            // TODO: return to menu?
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
}
