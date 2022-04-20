package at.pmzcraft.program.engine.render.mathematical.matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Matrix {

    public static final int SIZE = 4;

    private float[][] matrix;

    public Matrix() {
        matrix = new float[4][4];
    }

    public Matrix(float[][] matrix) {
        this.matrix = matrix;
    }

    public float get(int i1, int i2) {
        return matrix[i1][i2];
    }

    public float[][] getArray() {
        return matrix;
    }

    public FloatBuffer toFloatBuffer() {
        float[] tmp = new float[SIZE * SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tmp[i * 4 + j] = matrix[i][j];
            }
        }
        return floatArrayToBuffer(tmp);
    }

    private FloatBuffer floatArrayToBuffer(float[] floatArray) {
        ByteBuffer byteBuffer = ByteBuffer
                .allocateDirect(floatArray.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floatArray);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public void set(int i1, int i2, float value) {
        matrix[i1][i2] = value;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result += " | " + String.format("%10f", matrix[i][j]);
            }
            result += "\n";
        }
        result += "\n------------------------------------------";
        return result;
    }
}
