package at.pmzcraft.program.engine.render.mathematical.matrix;

import at.pmzcraft.program.engine.render.mathematical.vector.Vector;

import static at.pmzcraft.program.engine.render.mathematical.matrix.Matrix.SIZE;
import static at.pmzcraft.program.engine.render.mathematical.vector.Vector.*;
import static java.lang.Math.*;

public class MatrixUtils {

    public static final float PI = (float) (Math.PI);

    public static Vector multiplyMatrixVector(Matrix m, Vector v) {
        Vector o = new Vector();
        float sum;
        for (int i = 0; i < SIZE; i++) {
            sum = 0.0f;
            for (int j = 0; j < SIZE; j++) {
                sum += v.get(j) * m.get(j, i);
            }
            o.set(i, sum);
        }
        return o;
    }

    public static Matrix createIdentityMatrix() {
        Matrix m = new Matrix();
        for (int i = 0; i < SIZE; i++) {
            m.set(i, i, 1.0f);
        }
        return m;
    }

    public static Matrix createProjectionMatrix(float fov, float aspectRatio, float zNear, float zFar) {
        Matrix m = new Matrix();
        float calculatedFov = (float) (1 / tan(fov / 2));
        float zm = zFar - zNear;
        float zp = zFar + zNear;
        m.set(0, 0, calculatedFov / aspectRatio);
        m.set(1, 1, calculatedFov);
        m.set(2, 2, -zp / zm);
        m.set(2, 3, -(2 * zFar * zNear) / zm);
        m.set(3, 2, -1);
        return m;
    }

    public static Matrix createTranslationMatrix(Vector offset) {
        Matrix m = createIdentityMatrix();
        m.set(0, 3, offset.get(X));
        m.set(1, 3, offset.get(Y));
        m.set(2, 3, offset.get(Z));
        return m;
    }

    public static Matrix createRotationMatrixX(float angleRad) {
        Matrix m = createIdentityMatrix();
        //m.set(0, 0, 1);
        m.set(1, 1, (float) cos(angleRad));
        m.set(1, 2, (float) -sin(angleRad));
        m.set(2, 1, (float) sin(angleRad));
        m.set(2, 2, (float) cos(angleRad));
        return m;
    }

    public static Matrix createRotationMatrixY(float angleRad) {
        Matrix m = createIdentityMatrix();
        m.set(0, 0, (float) cos(angleRad));
        m.set(0, 2, (float) sin(angleRad));
        //m.set(1, 1, 1);
        m.set(2, 0, (float) -sin(angleRad));
        m.set(2, 2, (float) cos(angleRad));
        return m;
    }

    public static Matrix createRotationMatrixZ(float angleRad) {
        Matrix m = createIdentityMatrix();
        m.set(0, 0, (float) cos(angleRad));
        m.set(0, 1, (float) -sin(angleRad));
        m.set(1, 0, (float) sin(angleRad));
        m.set(1, 1, (float) cos(angleRad));
        //m.set(2, 2, 1);
        return m;
    }

    public static Matrix createScalationMatrix(float scale) {
        Matrix m = createIdentityMatrix();
        for (int i = 0; i < SIZE - 1; i++) {
            m.set(i, i, scale);
        }
        return m;
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        Matrix m = new Matrix();
        float tmp;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tmp = 0.0f;
                for (int k = 0; k < SIZE; k++) {
                    tmp += m1.get(i , k) * m2.get(k, j);
                }
                m.set(i, j ,tmp);
            }
        }
        return m;
    }


}
