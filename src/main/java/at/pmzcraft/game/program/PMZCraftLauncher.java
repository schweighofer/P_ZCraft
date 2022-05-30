package at.pmzcraft.game.program;

import at.pmzcraft.game.program.engine.PMZEngine;
import at.pmzcraft.game.program.game.PMZGame;

import java.awt.*;

public class PMZCraftLauncher {

    public static void main(String[] args) {
        startGameInstance();
    }

    public static void startGameInstance() {
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
