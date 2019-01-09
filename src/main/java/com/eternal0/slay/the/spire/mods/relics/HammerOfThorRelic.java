package com.eternal0.slay.the.spire.mods.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HammerOfThorRelic extends CardGridRelic {
    private static final Comparator<AbstractCard> BY_COLOR = Comparator.comparing(card -> card.color);
    private static final Comparator<AbstractCard> BY_NAME = Comparator.comparing(card -> card.name);

    public static final String ID = "Hammer of Thor";

    public HammerOfThorRelic() {
        super(ID, "hammerOfThor.png", RelicTier.SPECIAL, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return "Right-clicking this relic allows you to craft any cards";
    }

    @Override
    protected CardGroup getCardsToSelect() {
        final CardGroup cards = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        final List<AbstractCard> pool = CardLibrary.getAllCards();

        pool.sort(BY_COLOR.thenComparing(BY_NAME));
        pool.forEach(cards::addToTop);

        return cards;
    }

    @Override
    protected String getGridMessage() {
        return "Select any number of cards to craft";
    }

    @Override
    protected void onCardsSelected(ArrayList<AbstractCard> cards) {
        final int count = cards.size();
        final float min = -15.0f * count;
        for (int i = 0; i < count; ++i) {
            final AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(i);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card.makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F + (min + i * 30.0f) * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HammerOfThorRelic();
    }
}
