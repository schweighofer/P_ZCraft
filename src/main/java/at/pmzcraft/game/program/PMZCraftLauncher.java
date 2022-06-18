package at.pmzcraft.game.program;

import at.pmzcraft.game.program.engine.PMZEngine;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.Matrix;
import at.pmzcraft.game.program.engine.render.mathematical.matrix.MatrixUtils;
import at.pmzcraft.game.program.game.PMZGame;

import java.awt.*;

public class PMZCraftLauncher {

    public static void main(String[] args) {
//
//       Matrix r = new Matrix(
//                new float[][]{
//                 {11, 12, 13 ,14},
//                {21, 22, 23 ,24},
//                {31, 32, 33 ,34},
//                {41, 42, 43 ,44}});
//        Matrix d = new Matrix(
//                new float[][]{
//                 {51, 52, 53 ,54},
//                 {61, 62, 63 ,64},
//                 {71, 72, 73 ,74},
//                 {81, 82, 83 ,84}});
//
//        System.out.println("r: " + r);
//        System.out.println("d: " + d);
//        System.out.println("mulAffine: " + MatrixUtils.mulAffine(r,d));
//        System.exit(0);

        startGameInstance();
    }

    public static void startGameInstance() {
        String title = "P*ZCraft";
        String threadName = "main-game-" + 0;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        boolean isVSyncEnabled = true;
        PMZGame concreteLogic = new PMZGame();
        PMZEngine controller = new PMZEngine(title, screen.width, screen.height, isVSyncEnabled, concreteLogic);
        Thread mainGameThread = new Thread(controller);
        mainGameThread.setName(threadName);
        mainGameThread.start();
    }
}
