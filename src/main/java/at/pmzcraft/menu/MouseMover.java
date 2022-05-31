package at.pmzcraft.menu;

import java.awt.*;

public class MouseMover implements Runnable {
    @Override
    public void run() {
        try {
            updateButtons();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void updateButtons() throws InterruptedException, AWTException {
        Thread.sleep(35);
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();

        int x = (int) b.getX();
        int y = (int) b.getY(); //coordinaten von maus um dorthin zurück zu kehren
        Robot r = new Robot();

        r.mouseMove(485 + 10, 285 + 10);//für play
        Thread.sleep(10);
        r.mouseMove(485 + 15, 475 + 10);//für setting
        Thread.sleep(10);
        r.mouseMove(500 + 10, 650 + 10);//für exit
        Thread.sleep(10 + 5);
        r.mouseMove(x, y); //auf ursprung zurück
    }
}
