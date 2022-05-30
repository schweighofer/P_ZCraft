package at.pmzcraft.menu;

import java.awt.*;

public class MouseMover implements Runnable {
    @Override
    public void run() {
        try {
            iLikeToMoveItMoveIt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void iLikeToMoveItMoveIt() throws InterruptedException, AWTException {
        if (Menu.isHovered) { //am anfang längeres sleep weil dort manches noch nicht geladen sein kann
            Thread.sleep(37);
        } else {
            Thread.sleep(200);
            Menu.isHovered = Boolean.TRUE;
        }

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
