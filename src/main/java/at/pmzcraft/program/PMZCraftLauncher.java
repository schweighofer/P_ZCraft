package at.pmzcraft.program;

import at.pmzcraft.program.engine.PMZGameController;
import at.pmzcraft.program.game.ConcreteMainLogicImplementation;

import java.awt.*;

public class PMZCraftLauncher {

    public static void main(String[] args) {
        String title = "P*ZCraft";
        String threadName = "main-game-" + 0;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        boolean isVSyncEnabled = true;
        ConcreteMainLogicImplementation concreteLogic = new ConcreteMainLogicImplementation();
        PMZGameController controller = new PMZGameController(title, 600, 480, isVSyncEnabled, concreteLogic);
        Thread mainGameThread = new Thread(controller);
        mainGameThread.setName(threadName);
        mainGameThread.start();
    }
}
