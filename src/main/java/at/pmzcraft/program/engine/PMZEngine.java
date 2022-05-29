package at.pmzcraft.program.engine;

import at.pmzcraft.exception.ShaderException;
import at.pmzcraft.exception.TextureException;
import at.pmzcraft.exception.WindowException;
import at.pmzcraft.program.engine.utils.ResourceLoader;
import at.pmzcraft.program.engine.utils.Synchronizer;
import at.pmzcraft.program.engine.utils.Timer;
import at.pmzcraft.program.game.PMZGame;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWImage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;

import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryUtil.memAllocInt;

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
        } catch (WindowException | ShaderException | TextureException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Cleanup the shader program
            cleanup();
            // Cleanup the window after the game is done
            window.close();
        }
    }

    private void init() throws WindowException, ShaderException, TextureException, IOException {
        window.init();
        timer.init();
        logicImplementation.init();

        initWindowIcon();
    }

    private void initWindowIcon() {
        ByteBuffer icon16;
        ByteBuffer icon32;
        try {
            icon16 = ResourceLoader.ioResourceToByteBuffer(Path.of("resources", "title_logo.png"), 2048);
            icon32 = ResourceLoader.ioResourceToByteBuffer(Path.of("resources", "title_logo.png"), 4096);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        IntBuffer width = memAllocInt(1);
        IntBuffer height = memAllocInt(1);
        IntBuffer comp = memAllocInt(1);

        try ( GLFWImage.Buffer icons = GLFWImage.malloc(2) ) {
            ByteBuffer pixels16 = stbi_load_from_memory(icon16, width, height, comp, 4);
            icons
                    .position(0)
                    .width(width.get(0))
                    .height(height.get(0))
                    .pixels(pixels16);

            ByteBuffer pixels32 = stbi_load_from_memory(icon32, width, height, comp, 4);
            icons
                    .position(1)
                    .width(width.get(0))
                    .height(height.get(0))
                    .pixels(pixels32);

            icons.position(0);
            glfwSetWindowIcon(window.getHandle(), icons);

            stbi_image_free(pixels32);
            stbi_image_free(pixels16);
        }
    }

    private void loop() throws InterruptedException {
        double elapsedTime;
        double accumulator = 0d;
        float interval = 1.0f / 30.0f;
//        int loop = 1;

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

//            loop+=1;
//
//            if (loop>100) {
//                running = false;
//            }
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
