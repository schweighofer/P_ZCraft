package at.pmzcraft.game.program.engine.render;


import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4;

public class Material {

    private static final Vector4 DEFAULT_COLOUR = new Vector4(1.0f, 1.0f, 1.0f, 1.0f);

    private Vector4 ambientColour;

    private Vector4 diffuseColour;

    private Vector4 specularColour;

    private float reflectance;

    private Texture texture;

    public Material() {
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vector4 colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
    }

    public Material(Texture texture, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
    }

    public Material(Vector4 ambientColour, Vector4 diffuseColour, Vector4 specularColour, Texture texture, float reflectance) {
        this.ambientColour = ambientColour;
        this.diffuseColour = diffuseColour;
        this.specularColour = specularColour;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public Vector4 getAmbientColour() {
        return ambientColour;
    }

    public void setAmbientColour(Vector4 ambientColour) {
        this.ambientColour = ambientColour;
    }

    public Vector4 getDiffuseColour() {
        return diffuseColour;
    }

    public void setDiffuseColour(Vector4 diffuseColour) {
        this.diffuseColour = diffuseColour;
    }

    public Vector4 getSpecularColour() {
        return specularColour;
    }

    public void setSpecularColour(Vector4 specularColour) {
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
