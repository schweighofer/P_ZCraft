#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 m_modelViewMatrix;
uniform mat4 m_projectionMatrix;

void main() {
    gl_Position = m_projectionMatrix * m_modelViewMatrix * vec4(position, 1.0);
    outTexCoord = texCoord;
}