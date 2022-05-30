package at.pmzcraft.game.program.engine;

import at.pmzcraft.game.exception.window.GLFWInitializationException;
import at.pmzcraft.game.exception.window.WindowCreationException;
import at.pmzcraft.game.program.engine.utils.ResourceLoader;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAllocInt;

// Wrapper Class for GLFWs window handle
public class Window {

    private final String title;
    private WindowIcon icon;
    private int width;
    private int height;
    private long glfwWindowHandle;
    private boolean isVSyncEnabled;

    private boolean isResized = false;

    public Window(String title, int width, int height, boolean isVSyncEnabled) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isVSyncEnabled = isVSyncEnabled;
    }

    public void init() throws GLFWInitializationException, WindowCreationException {
        // Create the error callback pointer to use System.err
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new GLFWInitializationException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Create the window
        glfwWindowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwWindowHandle == NULL) {
            throw new WindowCreationException("Failed to create the GLFW window");
        }

        // Setup resize callback
        glfwSetFramebufferSizeCallback(glfwWindowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            isResized = true;
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(glfwWindowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_BACKSPACE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                glfwWindowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindowHandle);

        if (isVSyncEnabled()) {
            // Enable v-sync
            glfwSwapInterval(1);
        }

        // Make the window visible
        glfwShowWindow(glfwWindowHandle);

        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Enable Depth-Testing so that triangles are drawn in the correct order
        glEnable(GL_DEPTH_TEST);

        // Set the WindowIcon (this should be done last)
        this.icon = new WindowIcon(this);
        // TODO: Extract Paths into Structure
        Path resources = Path.of("resources", "title_logo.png");
        icon.initWindowIcon(resources);
    }


    public void update() {
        // Swap color buffers
        glfwSwapBuffers(glfwWindowHandle);

        // Poll for window events
        glfwPollEvents();
    }

    public void close() {
        // Cleanup the window and free its callback after the game is done
        glfwFreeCallbacks(glfwWindowHandle);
        glfwDestroyWindow(glfwWindowHandle);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(glfwWindowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(glfwWindowHandle);
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getHandle() {
        return glfwWindowHandle;
    }

    public boolean isVSyncEnabled() {
        return isVSyncEnabled;
    }

    public boolean wasResized() {
        if (isResized) {
            isResized = false;
            return true;
        }
        return false;
    }
}

class WindowIcon {

    private Window window;

    public WindowIcon(Window window) {
        this.window = window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    void initWindowIcon(Path path) {
        ByteBuffer icon16;
        ByteBuffer icon32;
        try {
            icon16 = ResourceLoader.ioResourceToByteBuffer(path, 2048);
            icon32 = ResourceLoader.ioResourceToByteBuffer(path, 4096);
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
}
