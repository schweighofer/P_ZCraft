#version 330

in  vec2 outTexCoord;
in vec3 mvVertexNormal;
in vec3 mvVertexPos;

out vec4 fragColor;

struct Attenuation {
    float constant;
    float linear;
    float exponent;
};

struct PointLight {
    vec3 colour;
    // Light position is assumed to be in view coordinates
    vec3 position;
    float intensity;
    Attenuation att;
};

struct Material {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflectance;
};

uniform sampler2D t_textureSampler;
uniform vec3 l_ambientLight;
uniform float l_specularPower;
uniform Material l_material;
uniform PointLight l_pointLight;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

void setupColours(Material l_material, vec2 textCoord) {
    if (l_material.hasTexture == 1) {
        ambientC = texture(t_textureSampler, textCoord);
        diffuseC = ambientC;
        speculrC = ambientC;
    } else {
        ambientC = l_material.ambient;
        diffuseC = l_material.diffuse;
        speculrC = l_material.specular;
    }
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal) {
    vec4 diffuseColour = vec4(0, 0, 0, 0);
    vec4 specColour = vec4(0, 0, 0, 0);

    // Diffuse Light
    vec3 light_direction = light.position - position;
    vec3 to_light_source  = normalize(light_direction);
    float diffuseFactor = max(dot(normal, to_light_source ), 0.0);
    diffuseColour = diffuseC * vec4(light.colour, 1.0) * light.intensity * diffuseFactor;

    // Specular Light
    vec3 camera_direction = normalize(-position);
    vec3 from_light_source = -to_light_source;
    vec3 reflected_light = normalize(reflect(from_light_source, normal));
    float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, l_specularPower);
    specColour = speculrC * light.intensity * specularFactor * l_material.reflectance * vec4(light.colour, 1.0);

    // Attenuation
    float distance = length(light_direction);
    float attenuationInv = light.att.constant + light.att.linear * distance +
        light.att.exponent * distance * distance;
    return (diffuseColour + specColour) / attenuationInv;
}

void main() {
    setupColours(l_material, outTexCoord);
    vec4 diffuseSpecularComp = calcPointLight(l_pointLight, mvVertexPos, mvVertexNormal);
    fragColor = ambientC * vec4(l_ambientLight, 1) + diffuseSpecularComp;
}