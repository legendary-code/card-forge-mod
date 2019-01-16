package com.eternal0.slay.the.spire.mods.relics;

import basemod.abstracts.CustomRelic;
import com.eternal0.slay.the.spire.mods.ui.ScreenManager;
import com.eternal0.slay.the.spire.mods.ui.screens.CardSelectionScreen;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public abstract class CardGridRelic  extends CustomRelic implements ClickableRelic {
    private boolean used;

    protected CardGridRelic(String id, String imgName, RelicTier tier, LandingSound sfx) {
        super(id, imgName, tier, sfx);
    }

    protected abstract CardGroup getCardsToSelect();
    protected abstract String getGridMessage();
    protected abstract void onCardsSelected(ArrayList<AbstractCard> cards);

    public void onRightClick() {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.MAP ||
            AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            return;
        }

        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;

        final CardSelectionScreen screen = new CardSelectionScreen(
            getGridMessage(),
            getCardsToSelect(),
            this::onCardsSelected
        );
        ScreenManager.INSTANCE.show(screen);

        used = true;
    }

    @Override
    public void update() {
        super.update();

        if (!used) {
            return;
        }

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            return;
        }

        used = false;

        final int count = AbstractDungeon.gridSelectScreen.selectedCards.size();
        if (count == 0) {
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            return;
        }

        onCardsSelected(AbstractDungeon.gridSelectScreen.selectedCards);

        AbstractDungeon.gridSelectScreen.selectedCards.forEach(AbstractCard::stopGlowing);
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }
}
