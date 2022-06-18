package at.pmzcraft.game.program.engine.utils;


import at.pmzcraft.game.program.engine.render.Mesh;
import at.pmzcraft.game.program.engine.render.mathematical.vector.Vector;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static at.pmzcraft.game.program.engine.render.mathematical.vector.Vector.*;

public class OBJLoader {

    public static Mesh loadMesh(Path fileName) throws IOException {
        List<String> lines = ResourceLoader.readAllLines(fileName);
        
        List<Vector> vertices = new ArrayList<>();
        List<Vector> textures = new ArrayList<>();
        List<Vector> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    // Geometric vertex
                    Vector vec3 = new Vector(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]),
                            0);
                    vertices.add(vec3);
                    break;
                case "vt":
                    // Texture coordinate
                    Vector vec2 = new Vector(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            0,
                            0);
                    textures.add(vec2);
                    break;
                case "vn":
                    // Vertex normal
                    Vector vec3Norm = new Vector(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]),
                            0);
                    normals.add(vec3Norm);
                    break;
                case "f":
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    // Ignore other lines
                    break;
            }
        }
        return reorderLists(vertices, textures, normals, faces);
    }

    private static Mesh reorderLists
            (List<Vector> posList, List<Vector> textCoordList, List<Vector> normList, List<Face> facesList) {

        List<Integer> indices = new ArrayList<>();
        // Create position array in the order it has been declared
        float[] posArr = new float[posList.size() * 3];
        int i = 0;
        for (Vector pos : posList) {
            posArr[i * 3] = pos.get(X);
            posArr[i * 3 + 1] = pos.get(Y);
            posArr[i * 3 + 2] = pos.get(Z);
            i++;
        }
        float[] textCoordArr = new float[posList.size() * 2];
        float[] normArr = new float[posList.size() * 3];

        for (Face face : facesList) {
            IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for (IdxGroup indValue : faceVertexIndices) {
                processFaceVertex(indValue, textCoordList, normList,
                        indices, textCoordArr, normArr);
            }
        }
        int[] indicesArr = new int[indices.size()];
        indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        Mesh mesh = new Mesh(posArr, textCoordArr, normArr, indicesArr);
        return mesh;
    }

    private static void processFaceVertex
            (IdxGroup indices, List<Vector> textCoordList, List<Vector> normList, List<Integer> indicesList,
            float[] texCoordArr, float[] normArr) {

        // Set index for vertex coordinates
        int posIndex = indices.idxPos;
        indicesList.add(posIndex);

        // Reorder texture coordinates
        if (indices.idxTextCoord >= 0) {
            Vector textCoord = textCoordList.get(indices.idxTextCoord);
            texCoordArr[posIndex * 2] = textCoord.get(X);
            texCoordArr[posIndex * 2 + 1] = 1 - textCoord.get(Y);
        }
        if (indices.idxVecNormal >= 0) {
            // Reorder vectornormals
            Vector vecNorm = normList.get(indices.idxVecNormal);
            normArr[posIndex * 3] = vecNorm.get(X);
            normArr[posIndex * 3 + 1] = vecNorm.get(Y);
            normArr[posIndex * 3 + 2] = vecNorm.get(Z);
        }
    }

    protected static class Face {

        private IdxGroup[] idxGroups = new IdxGroup[3];

        public Face(String v1, String v2, String v3) {
            idxGroups = new IdxGroup[3];
            // Parse the lines
            idxGroups[0] = parseLine(v1);
            idxGroups[1] = parseLine(v2);
            idxGroups[2] = parseLine(v3);
        }

        private IdxGroup parseLine(String line) {
            IdxGroup idxGroup = new IdxGroup();

            String[] lineTokens = line.split("/");
            int length = lineTokens.length;
            idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
            if (length > 1) {
                // It can be empty if the obj does not define text coords
                String textCoord = lineTokens[1];
                idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
                if (length > 2) {
                    idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
                }
            }

            return idxGroup;
        }

        public IdxGroup[] getFaceVertexIndices() {
            return idxGroups;
        }
    }

    protected static class IdxGroup {

        public static final int NO_VALUE = -1;

        public int idxPos;

        public int idxTextCoord;

        public int idxVecNormal;

        public IdxGroup() {
            idxPos = NO_VALUE;
            idxTextCoord = NO_VALUE;
            idxVecNormal = NO_VALUE;
        }
    }
}
