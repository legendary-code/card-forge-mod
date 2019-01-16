package com.eternal0.slay.the.spire.mods.ui;

import basemod.interfaces.PreUpdateSubscriber;
import basemod.interfaces.RenderSubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class ScreenManager implements Component, RenderSubscriber, PreUpdateSubscriber {
    private static final Logger LOGGER = LogManager.getLogger(ScreenManager.class);
    public static final ScreenManager INSTANCE = new ScreenManager();

    private final Stack<Screen> screens = new Stack<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private CurrentScreen currentScreen;
    private CurrentScreen previousScreen;

    public void update() {
        withCurrentScreen(Screen::update);
    }

    public void receiveRender(SpriteBatch sb) {
        render(sb);
    }

    public void receivePreUpdate() {
        update();
    }

    public void render(SpriteBatch sb) {
        withCurrentScreen(screen -> screen.render(sb));
    }

    private void overrideScreenState() {
        previousScreen = AbstractDungeon.previousScreen;
        currentScreen = AbstractDungeon.screen;
        AbstractDungeon.previousScreen = CurrentScreen.NONE;
        AbstractDungeon.screen = CurrentScreen.NONE;
    }

    private void restoreScreenState() {
        AbstractDungeon.previousScreen = previousScreen;
        AbstractDungeon.screen = currentScreen;
        previousScreen = null;
        currentScreen = null;
    }

    public void show(Screen screen) {
        LOGGER.info(String.format("Showing screen %s", screen.getClass().getSimpleName()));

        writeLocked(() -> {
            if (screens.isEmpty()) {
                overrideScreenState();
            }

            screens.push(screen);
        });
    }

    public void closeTopMost() {
        writeLocked(() -> {
            final Screen screen = screens.pop();

            LOGGER.info(String.format("Closing screen %s", screen.getClass().getSimpleName()));

            if (screens.isEmpty()) {
                restoreScreenState();
            }
        });
    }

    private void withCurrentScreen(Consumer<Screen> consumer) {
        final Screen screen;

        try {
            lock.readLock().lock();
            if (screens.isEmpty()) {
                return;
            }
            screen = screens.peek();
        } finally {
            lock.readLock().unlock();
        }

        consumer.accept(screen);
    }

    private void writeLocked(Runnable block) {
        try {
            lock.writeLock().lock();
            block.run();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
