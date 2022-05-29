package at.pmzcraft.program;

import at.pmzcraft.program.engine.PMZEngine;
import at.pmzcraft.program.game.PMZGame;

import java.awt.*;

public class PMZCraftLauncher {

    public static void main(String[] args) {
        String title = "P*ZCraft";
        String threadName = "main-game-" + 0;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        boolean isVSyncEnabled = true;
        PMZGame concreteLogic = new PMZGame();
        PMZEngine controller = new PMZEngine(title, screen.width / 2, screen.height / 2, isVSyncEnabled, concreteLogic);
        Thread mainGameThread = new Thread(controller);
        mainGameThread.setName(threadName);
        mainGameThread.start();
    }
}
