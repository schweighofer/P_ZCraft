package at.pmzcraft.program.engine.graphical;


import at.pmzcraft.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.program.engine.render.mathematical.vector.Vector;

import static at.pmzcraft.program.engine.render.mathematical.matrix.MatrixUtils.*;
import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.*;

public class Transformation {

    private Matrix worldMatrix;
    private Matrix projectionMatrix;

    public Transformation() {
    }


    // fixed #cool
    public Matrix getProjectionMatrix(float fov, float aspectRatio, float zNear, float zFar) {
        projectionMatrix = createProjectionMatrix(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    // wrong position indices vllt
    public Matrix getWorldMatrix(Vector offset, Vector rotation, float scale) {

        System.out.println(offset.get(Z));

        System.out.println("worldMatrix:\n" + worldMatrix);
        System.out.println("rotation:\n" + rotation);
        System.out.println("scale:\n" + scale);

        Matrix translationMatrix = createTranslationMatrix(offset);
        System.out.println("transMatrix:\n" + translationMatrix);

        Matrix rotationMatrix = createRotationMatrixX(rotation.get(X) * PI / 180);
        System.out.println("rotMatrixX:\n" + rotationMatrix);

        rotationMatrix = multiply(rotationMatrix, createRotationMatrixY(rotation.get(Y) * PI / 180));
        System.out.println("rotMatrixY:\n" + rotationMatrix);

        rotationMatrix = multiply(rotationMatrix, createRotationMatrixZ(rotation.get(Z) * PI / 180));
        System.out.println("rotMatrixZ:\n" + rotationMatrix);

        Matrix scalationMatrix = createScalationMatrix(scale);
        System.out.println("scaleMatrixX:\n" + scalationMatrix);

        worldMatrix = multiply(translationMatrix, rotationMatrix);
        System.out.println("worldMatrixTransRot:\n" + worldMatrix);

        worldMatrix = multiply(worldMatrix, scalationMatrix);
        System.out.println("worldMatrixTransRotScal:\n" + worldMatrix);

        return worldMatrix;
    }
}
