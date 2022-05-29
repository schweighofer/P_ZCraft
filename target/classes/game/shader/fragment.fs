#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D t_textureSampler;

void main() {
    fragColor = texture(t_textureSampler, outTexCoord);
}