package at.pmzcraft.game.program.engine.render.mathematical.matrix;

import at.pmzcraft.game.program.engine.render.mathematical.MathematicalCloneable;
import at.pmzcraft.game.program.engine.render.mathematical.MathematicalStructure;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class Matrix extends MathematicalStructure implements MathematicalCloneable {

    public static final int SIZE = 4;

    private float[][] matrix;

    public Matrix() {
        matrix = new float[4][4];
    }

    public Matrix(float[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * @param row Zeile
     * @param col Spalte
     */
    public float get(int row, int col) {
        return matrix[row][col];
    }

    /**
     * @param row Zeile
     * @param col Spalte
     */
    public void set(int row, int col, float value) {
        matrix[row][col] = value;
    }

    public FloatBuffer toFloatBuffer() {
        float[] tmp = new float[SIZE * SIZE];
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                tmp[col * 4 + row] = matrix[row][col];
            }
        }
        return floatArrayToBuffer(tmp);
    }

    private FloatBuffer floatArrayToBuffer(float[] floatArray) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floatArray.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floatArray);
        floatBuffer.position(0);
        return floatBuffer;
    }

    @Override
    public String toString() {
        String result = "";
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result += " | " + String.format("%10f", matrix[row][col]);
            }
            result += "\n";
        }
        result += "\n------------------------------------------";
        return result;
    }

    @Override
    public MathematicalStructure clone() {
        return new Matrix(Arrays.stream(matrix).map(a -> Arrays.copyOf(a, a.length)).toArray(float[][]::new));
    }
}
