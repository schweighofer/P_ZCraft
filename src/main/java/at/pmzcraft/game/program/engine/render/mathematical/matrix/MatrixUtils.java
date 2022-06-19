package at.pmzcraft.game.program.engine.render.mathematical.matrix;

import at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4;

import static at.pmzcraft.game.program.engine.render.mathematical.matrix.Matrix.SIZE;
import static at.pmzcraft.game.program.engine.render.mathematical.utils.AngleUtils.*;
import static at.pmzcraft.game.program.engine.render.mathematical.vector.vector.Vector4.*;
import static at.pmzcraft.game.program.engine.render.mathematical.utils.AlgebraUtils.*;

public class MatrixUtils {

    public static Vector4 multiplyMatrixVector(Matrix m, Vector4 v) {
        Vector4 o = new Vector4();
        float sum;
        for (int col = 0; col < SIZE; col++) {
            sum = 0.0f;
            for (int row = 0; row < SIZE; row++) {
                sum += v.get(row) * m.get(row, col);
            }
            o.set(col, sum);
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
        float calculatedFov = (1 / tan(fov / 2));
        float zm = zFar - zNear;
        float zp = zFar + zNear;
        m.set(0, 0, calculatedFov / aspectRatio);
        m.set(1, 1, calculatedFov);
        m.set(2, 2, -zp / zm);
        m.set(2, 3, -(2 * zFar * zNear) / zm);
        m.set(3, 2, -1);
        return m;
    }

    public static Matrix createTranslationMatrix(Vector4 offset) {
        Matrix m = createIdentityMatrix();
        m.set(0, 3, offset.get(X));
        m.set(1, 3, offset.get(Y));
        m.set(2, 3, offset.get(Z));
        return m;
    }

    public static Matrix createRotationMatrixX(float angleRad) {
        Matrix m = createIdentityMatrix();
        float sin = sin(angleRad);
        float cos = cos(angleRad);
        m.set(1, 1, cos);
        m.set(1, 2, -sin);
        m.set(2, 1, sin);
        m.set(2, 2, cos);
        return m;
    }

    public static Matrix createRotationMatrixY(float angleRad) {
        Matrix m = createIdentityMatrix();
        float sin = sin(angleRad);
        float cos = cos(angleRad);
        m.set(0, 0, cos);
        m.set(0, 2, sin);
        m.set(2, 0, -sin);
        m.set(2, 2, cos);
        return m;
    }

    public static Matrix createRotationMatrixZ(float angleRad) {
        Matrix m = createIdentityMatrix();
        float sin = sin(angleRad);
        float cos = cos(angleRad);
        m.set(0, 0, cos);
        m.set(0, 1, -sin);
        m.set(1, 0, sin);
        m.set(1, 1, cos);
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
        Matrix result = new Matrix();
        float tmp;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                tmp = 0.0f;
                for (int k = 0; k < SIZE; k++) {
                    tmp += m1.get(row , k) * m2.get(k, col);
                }
                result.set(row, col ,tmp);
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix... matrices) {
        Matrix result = matrices[0];
        for (int index = 1; index < matrices.length; index++) {
            result = multiply(result, matrices[index]);
        }
        return result;
    }
/*
    public Matrix4f mulAffine(Matrix4fc right, Matrix4f dest) {
        // erstes: zeile
        // zweites: spalte
        // this =^= dest
        float m00 = this.m00();
        float m01 = this.m01();
        float m02 = this.m02();
        float m10 = this.m10();
        float m11 = this.m11();
        float m12 = this.m12();
        float m20 = this.m20();
        float m21 = this.m21();
        float m22 = this.m22();
        float rm00 = right.m00();
        float rm01 = right.m01();
        float rm02 = right.m02();
        float rm10 = right.m10();
        float rm11 = right.m11();
        float rm12 = right.m12();
        float rm20 = right.m20();
        float rm21 = right.m21();
        float rm22 = right.m22();
        float rm30 = right.m30();
        float rm31 = right.m31();
        float rm32 = right.m32();
        return dest
                ._m00(Math.fma(m00, rm00, Math.fma(m10, rm01, m20 * rm02)))
                ._m01(Math.fma(m01, rm00, Math.fma(m11, rm01, m21 * rm02)))
                ._m02(Math.fma(m02, rm00, Math.fma(m12, rm01, m22 * rm02)))
                ._m03(this.m03())

                ._m10(Math.fma(m00, rm10, Math.fma(m10, rm11, m20 * rm12)))
                ._m11(Math.fma(m01, rm10, Math.fma(m11, rm11, m21 * rm12)))
                ._m12(Math.fma(m02, rm10, Math.fma(m12, rm11, m22 * rm12)))
                ._m13(this.m13())
                ._m20(Math.fma(m00, rm20, Math.fma(m10, rm21, m20 * rm22)))
                ._m21(Math.fma(m01, rm20, Math.fma(m11, rm21, m21 * rm22)))
                ._m22(Math.fma(m02, rm20, Math.fma(m12, rm21, m22 * rm22)))
                ._m23(this.m23())


                ._m30(Math.fma(m00, rm30, Math.fma(m10, rm31, Math.fma(m20, rm32, this.m30()))))
                ._m31(Math.fma(m01, rm30, Math.fma(m11, rm31, Math.fma(m21, rm32, this.m31()))))
                ._m32(Math.fma(m02, rm30, Math.fma(m12, rm31, Math.fma(m22, rm32, this.m32()))))
                ._m33(this.m33())

                ._properties(2 | this.properties & right.properties() & 16);
    }
  */

    public static Matrix mulAffine(Matrix r, Matrix d) {
        Matrix m = new Matrix();
        r = flip(r);
        d = flip(d);
        m.set(0,0, fma(d.get(0,0), r.get(0,0), fma(d.get(1,0), r.get(0,1), d.get(2,0) * r.get(0,2))));
        m.set(0,1, fma(d.get(0,1), r.get(0,0), fma(d.get(1,1), r.get(0,1), d.get(2,1) * r.get(0,2))));
        m.set(0,2, fma(d.get(0,2), r.get(0,0), fma(d.get(1,2), r.get(0,1), d.get(2,2) * r.get(0,2))));
        m.set(0,3, d.get(0,3));

        m.set(1,0, fma(d.get(0,0), r.get(1,0), fma(d.get(1,0), r.get(1,1), d.get(2,0) * r.get(1,2))));
        m.set(1,1, fma(d.get(0,1), r.get(1,0), fma(d.get(1,1), r.get(1,1), d.get(2,1) * r.get(1,2))));
        m.set(1,2, fma(d.get(0,2), r.get(1,0), fma(d.get(1,2), r.get(1,1), d.get(2,2) * r.get(1,2))));
        m.set(1,3, d.get(1,3));

        m.set(2,0, fma(d.get(0,0), r.get(2,0), fma(d.get(1,0), r.get(2,1), d.get(2,0) * r.get(2,2))));
        m.set(2,1, fma(d.get(0,1), r.get(2,0), fma(d.get(1,1), r.get(2,1), d.get(2,1) * r.get(2,2))));
        m.set(2,2, fma(d.get(0,2), r.get(2,0), fma(d.get(1,2), r.get(2,1), d.get(2,2) * r.get(2,2))));
        m.set(2,3, d.get(2,3));

        m.set(3,0, fma(d.get(0,0), r.get(3,0), fma(d.get(1,0), r.get(3,1), fma(d.get(2,0), r.get(3,2), d.get(3,0)))));
        m.set(3,1, fma(d.get(0,1), r.get(3,0), fma(d.get(1,1), r.get(3,1), fma(d.get(2,1), r.get(3,2), d.get(3,1)))));
        m.set(3,2, fma(d.get(0,2), r.get(3,0), fma(d.get(1,2), r.get(3,1), fma(d.get(2,2), r.get(3,2), d.get(3,2)))));
        m.set(3,3, d.get(3,3));

        return flip(m);
        //return m;
    }


    public static Matrix flip(Matrix r) {
        Matrix m = new Matrix();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                m.set(col,row,r.get(row,col));
            }
        }

        return m;
    }

}
