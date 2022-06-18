package at.pmzcraft.game.program.engine.graphical;


import at.pmzcraft.game.program.engine.Camera;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.MatrixUtils;
import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;
import at.pmzcraft.game.program.game.world.gameitem.blocks.Block;

import static at.pmzcraft.game.program.engine.render.mathematical.matrix.MatrixUtils.*;
import static at.pmzcraft.game.program.engine.render.mathematical.utils.AngleUtils.*;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.Vector.*;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.VectorUtils.*;

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
        Vector cameraPosition = camera.getPosition();
        Vector cameraRotation = camera.getRotation();

        viewMatrix = multiply(
                  multiply(
                    createIdentityMatrix(),
                    // Rotation (here is problem)
                    createRotationMatrixX(toRadians(cameraRotation.get(X)))
                  ), createRotationMatrixY(toRadians(cameraRotation.get(Y)))
                 );
         viewMatrix = multiply(viewMatrix,createTranslationMatrix(cameraPosition));

        return viewMatrix;
    }

    public Matrix getModelViewMatrix(Block block, Matrix viewMatrix, Camera camera) {
        Vector position = block.getPosition();
        Vector rotation = mathScalarProduct(block.getRotation(), -1);

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
