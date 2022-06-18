package at.pmzcraft.game.program.engine.render;


import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;

public class Material {

    private static final Vector DEFAULT_COLOUR = new Vector(1.0f, 1.0f, 1.0f, 1.0f);

    private Vector ambientColour;

    private Vector diffuseColour;

    private Vector specularColour;

    private float reflectance;

    private Texture texture;

    public Material() {
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vector colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
    }

    public Material(Texture texture, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
    }

    public Material(Vector ambientColour, Vector diffuseColour, Vector specularColour, Texture texture, float reflectance) {
        this.ambientColour = ambientColour;
        this.diffuseColour = diffuseColour;
        this.specularColour = specularColour;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public Vector getAmbientColour() {
        return ambientColour;
    }

    public void setAmbientColour(Vector ambientColour) {
        this.ambientColour = ambientColour;
    }

    public Vector getDiffuseColour() {
        return diffuseColour;
    }

    public void setDiffuseColour(Vector diffuseColour) {
        this.diffuseColour = diffuseColour;
    }

    public Vector getSpecularColour() {
        return specularColour;
    }

    public void setSpecularColour(Vector specularColour) {
        this.specularColour = specularColour;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}
