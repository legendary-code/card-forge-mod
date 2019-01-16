
package com.eternal0.slay.the.spire.mods;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.eternal0.slay.the.spire.mods.relics.AnvilOfTheGodsRelic;
import com.eternal0.slay.the.spire.mods.relics.HammerOfThorRelic;
import com.eternal0.slay.the.spire.mods.relics.SeethingFireRelic;
import com.eternal0.slay.the.spire.mods.ui.ScreenManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class CardForgeMod implements PostDungeonInitializeSubscriber, EditRelicsSubscriber, EditStringsSubscriber {
    private static final Logger logger = LogManager.getLogger(CardForgeMod.class.getName());

    public static void initialize() {
        BaseMod.subscribe(new CardForgeMod());
        BaseMod.subscribe(ScreenManager.INSTANCE);
    }

    public void receiveEditStrings() {
        final String relicStrings = Gdx.files.internal("localization/RelicStrings.json").readString("UTF-8");
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
    }

    public void receiveEditRelics() {
        logger.info("Adding relics");
        BaseMod.addRelic(new SeethingFireRelic(), RelicType.SHARED);
        BaseMod.addRelic(new HammerOfThorRelic(), RelicType.SHARED);
        BaseMod.addRelic(new AnvilOfTheGodsRelic(), RelicType.SHARED);
        logger.info("Done adding relics");
    }

    public void receivePostDungeonInitialize() {
        RelicLibrary.getRelic(SeethingFireRelic.ID).makeCopy().instantObtain();
        RelicLibrary.getRelic(HammerOfThorRelic.ID).makeCopy().instantObtain();
        RelicLibrary.getRelic(AnvilOfTheGodsRelic.ID).makeCopy().instantObtain();
    }
}
