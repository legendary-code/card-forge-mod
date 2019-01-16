package com.eternal0.slay.the.spire.mods.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;

public class SeethingFireRelic extends CardGridRelic {
    public static final String ID = "Seething Fire";

    public SeethingFireRelic() {
        super(ID, "seethingFire.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return "Right-clicking this relic allows you to destroy any cards";
    }

    @Override
    protected CardGroup getCardsToSelect() {
        return AbstractDungeon.player.masterDeck.getPurgeableCards();
    }

    @Override
    protected String getGridMessage() {
        return "Select any number of cards to destroy";
    }

    @Override
    protected void onCardsSelected(ArrayList<AbstractCard> cards) {
        final int count = cards.size();
        final float min = -15.0f * count;
        for (int i = 0; i < count; ++i) {
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(cards.get(i), (float) Settings.WIDTH / 2.0F + (min + i * 30.0f) * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        }

        cards.forEach(AbstractDungeon.player.masterDeck::removeCard);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SeethingFireRelic();
    }
}
