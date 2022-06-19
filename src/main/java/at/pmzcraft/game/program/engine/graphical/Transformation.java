package at.pmzcraft.game.program.engine.graphical;


import at.pmzcraft.game.program.engine.Camera;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.MatrixUtils;
import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector3;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;

import static at.pmzcraft.game.program.engine.render.mathematical.matrix.MatrixUtils.*;
import static at.pmzcraft.game.program.engine.render.mathematical.utils.AngleUtils.toRadians;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4.*;

public class Transformation {

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
        Vector3 cameraPosition = camera.getPosition();
        Vector3 cameraRotation = camera.getRotation();

        viewMatrix = multiply(
                createRotationMatrixX(toRadians(cameraRotation.get(X))),
                createRotationMatrixY(toRadians(cameraRotation.get(Y))),
                createTranslationMatrix(cameraPosition)
        );

        return viewMatrix;
    }

    public Matrix getModelViewMatrix(Block block, Matrix viewMatrix, Camera camera) {
        Vector3 position = block.getPosition();
        Vector3 rotation = (Vector3) block.getRotation().mathScalarProduct(-1);

        modelViewMatrix = multiply(
                // Translation
                createTranslationMatrix(position),
                // Rotation
                createRotationMatrixX(toRadians(-rotation.get(X))),
                createRotationMatrixY(toRadians(-rotation.get(Y))),
                createRotationMatrixZ(toRadians(-rotation.get(Z))),
                // "Scalation" Scaling
                createScalationMatrix(block.getScale())
        );

        Matrix viewCurrent = (Matrix) viewMatrix.clone();

        return MatrixUtils.mulAffine(viewCurrent, modelViewMatrix);

    //     return multiply(modelViewMatrix, viewCurrent);
    }





}
