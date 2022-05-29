package at.pmzcraft.program.engine.graphical;


import at.pmzcraft.program.engine.Camera;
import at.pmzcraft.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.program.engine.render.mathematical.vector.Vector;
import at.pmzcraft.program.game.world.gameitem.blocks.Block;

import static at.pmzcraft.program.engine.render.mathematical.matrix.MatrixUtils.*;
import static at.pmzcraft.program.engine.render.mathematical.utils.AngleUtils.*;
import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.*;
import static at.pmzcraft.program.engine.render.mathematical.vector.VectorUtils.*;

public class Transformation {

    private Matrix worldMatrix;

    private Matrix projectionMatrix;
    private Matrix modelViewMatrix;
    private Matrix viewMatrix;

    public Transformation() {
    }

    public Matrix getProjectionMatrix(float fov, float aspectRatio, float zNear, float zFar) {
        projectionMatrix = createProjectionMatrix(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public Matrix getViewMatrix(Camera camera) {
        Vector cameraPosition = camera.getPosition();
        Vector cameraRotation = camera.getRotation();

        viewMatrix = createIdentityMatrix();
        viewMatrix = multiply(viewMatrix, createRotationMatrixX(toRadians(cameraRotation.get(X))));
        viewMatrix = multiply(viewMatrix, createRotationMatrixY(toRadians(cameraRotation.get(Y))));

        viewMatrix = multiply(viewMatrix, createTranslationMatrix(cameraPosition));

        return viewMatrix;
    }

    public Matrix getModelViewMatrix(Block block, Matrix viewMatrix) {
        Vector position = block.getPosition();
        Vector rotation = mathScalarProduct(block.getRotation(), -1);
        modelViewMatrix = createIdentityMatrix();
        modelViewMatrix = multiply(modelViewMatrix, createTranslationMatrix(position));

        Matrix rotationMatrix = createRotationMatrixX(toRadians(rotation.get(X)));
        rotationMatrix = multiply(rotationMatrix, createRotationMatrixY(toRadians(rotation.get(Y))));
        rotationMatrix = multiply(rotationMatrix, createRotationMatrixZ(toRadians(rotation.get(Z))));

        modelViewMatrix = multiply(modelViewMatrix, rotationMatrix);

        modelViewMatrix = multiply(modelViewMatrix, createScalationMatrix(block.getScale()));

        Matrix viewCurrent = (Matrix) viewMatrix.clone();

        return multiply(modelViewMatrix, viewCurrent);
    }

    public Matrix getWorldMatrix(Vector offset, Vector rotation, float scale) {
        Matrix translationMatrix = createTranslationMatrix(offset);

        Matrix rotationMatrix = createRotationMatrixX(rotation.get(X) * PI / 180);
        rotationMatrix = multiply(rotationMatrix, createRotationMatrixY(rotation.get(Y) * PI / 180));
        rotationMatrix = multiply(rotationMatrix, createRotationMatrixZ(rotation.get(Z) * PI / 180));

        Matrix scalationMatrix = createScalationMatrix(scale);

        worldMatrix = multiply(translationMatrix, rotationMatrix);
        worldMatrix = multiply(worldMatrix, scalationMatrix);

        return worldMatrix;
    }
}
