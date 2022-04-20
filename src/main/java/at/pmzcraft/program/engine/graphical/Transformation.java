package at.pmzcraft.program.engine.graphical;


import at.pmzcraft.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.program.engine.render.mathematical.vector.Vector;

import static at.pmzcraft.program.engine.render.mathematical.matrix.MatrixUtils.*;
import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.*;

public class Transformation {

    private Matrix worldMatrix;
    private Matrix projectionMatrix;

    public Transformation() {
        worldMatrix = createIdentityMatrix();
        projectionMatrix = new Matrix();
    }


    // fixed #cool
    public Matrix getProjectionMatrix(float fov, float aspectRatio, float zNear, float zFar) {
        projectionMatrix = createProjectionMatrix(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    // wrong position indices vllt
    public Matrix getWorldMatrix(Vector offset, Vector rotation, float scale) {
        worldMatrix = createIdentityMatrix();

        Matrix translationMatrix = createTranslationMatrix(offset);

        Matrix rotationMatrix = createRotationMatrixX(rotation.get(X) * PI / 180);
        rotationMatrix = multiply(rotationMatrix, createRotationMatrixY(rotation.get(Y) * PI / 180));
        rotationMatrix = multiply(rotationMatrix, createRotationMatrixZ(rotation.get(Z) * PI / 180));

        Matrix scalationMatrix = createScalationMatrix(scale);

        worldMatrix = multiply(worldMatrix, translationMatrix);
        worldMatrix = multiply(worldMatrix, rotationMatrix);
        worldMatrix = multiply(worldMatrix, scalationMatrix);

        System.out.println("World Matrix:\n" + worldMatrix);
        return worldMatrix;
    }
}
