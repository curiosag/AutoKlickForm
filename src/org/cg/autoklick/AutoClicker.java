package org.cg.autoklick;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker implements Runnable {

    private final Robot robot;

    private int interval;
    private boolean shouldKlick;

    AutoClicker() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized AutoClicker setInterval(int interval) {
        if (interval <= 0)
        {
            throw new IllegalStateException();
        }
        this.interval = interval;
        return this;
    }

    synchronized void setShouldKlick(boolean value) {
        if (value && interval <= 0)
        {
            throw new IllegalStateException();
        }
        shouldKlick = value;
        System.out.println("set shouldKlick to: " + shouldKlick);
    }

    @Override
    public void run() {
        while (true) {
            if (shouldKlick()) {
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                System.out.println("klick!");
                waitABit(interval);
            } else
            {
                System.out.println("no klick!");
                waitABit(1000);
            }
        }
    }

    private void waitABit(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            //
        }
    }

    private boolean shouldKlick() {
        return shouldKlick;
    }
}