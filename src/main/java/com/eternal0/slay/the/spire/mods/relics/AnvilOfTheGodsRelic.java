package com.eternal0.slay.the.spire.mods.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class AnvilOfTheGodsRelic extends CardGridRelic {

    public static final String ID = "Anvil of the Gods";

    public AnvilOfTheGodsRelic() {
        super(ID, "anvilOfTheGods.png", RelicTier.SPECIAL, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return "Right-clicking this relic allows you upgrading any cards";
    }


    @Override
    protected CardGroup getCardsToSelect() {
        return AbstractDungeon.player.masterDeck.getUpgradableCards();
    }


    @Override
    protected String getGridMessage() {
        return "Select any number of cards to upgrade";
    }

    @Override
    protected void onCardsSelected(ArrayList<AbstractCard> cards) {
        final int count = cards.size();
        final float min = -15.0f * count;
        for (int i = 0; i < count; ++i) {
            final AbstractCard card = cards.get(i);
            card.upgrade();
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F + (min + i * 30.0f) * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        }

        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        CardCrawlGame.sound.play("CARD_UPGRADE");
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AnvilOfTheGodsRelic();
    }
}
