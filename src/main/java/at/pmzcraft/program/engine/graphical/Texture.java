package at.pmzcraft.program.engine.graphical;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import at.pmzcraft.exception.TextureException;
import at.pmzcraft.exception.texture.LoadTextureException;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private final int ID;

    private Texture(int ID) {
        this.ID = ID;
    }

    public Texture(String path) throws TextureException {
        this(loadTexture(path));
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, ID);
    }

    public int getID() {
        return ID;
    }

    public void cleanup() {
        glDeleteTextures(ID);
    }

    private static int loadTexture(String path) throws TextureException {
        int width;
        int height;
        ByteBuffer buf;
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load(path, w, h, channels, 4);
            if (buf == null) {
                throw new LoadTextureException(stbi_failure_reason());
            }

            //Get width and height of image
            width = w.get();
            height = h.get();
        }

        // Create a new OpenGL texture
        int textureId = glGenTextures();
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Upload the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                GL_RGBA, GL_UNSIGNED_BYTE, buf);
        // Generate Mip Map
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(buf);

        return textureId;
    }
}
