package com.eternal0.slay.the.spire.mods.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.Comparator;

public abstract class Cards {
    private static final Comparator<AbstractCard> BY_COLOR = Comparator.comparing(card -> card.color);
    private static final Comparator<AbstractCard> BY_NAME = Comparator.comparing(card -> card.name);

    private Cards() {
    }

    public static CardGroup sorted(CardGroup cards) {
        cards.group.sort(BY_COLOR.thenComparing(BY_NAME));
        return cards;
    }
}
