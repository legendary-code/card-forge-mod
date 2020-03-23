
package com.eternal0.slay.the.spire.mods;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostCreateStartingRelicsSubscriber;

import com.badlogic.gdx.Gdx;
import com.eternal0.slay.the.spire.mods.relics.AnvilOfTheGodsRelic;
import com.eternal0.slay.the.spire.mods.relics.HammerOfThorRelic;
import com.eternal0.slay.the.spire.mods.relics.SeethingFireRelic;
import com.eternal0.slay.the.spire.mods.ui.ScreenManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpireInitializer
public class CardForgeMod implements PostCreateStartingRelicsSubscriber, EditRelicsSubscriber, EditStringsSubscriber {
    private static final Logger logger = LogManager.getLogger(CardForgeMod.class.getName());

    public static void initialize() {
        logger.info("reached initialize");
        BaseMod.subscribe(new CardForgeMod());
        BaseMod.subscribe(ScreenManager.INSTANCE);
    }

    public void receiveEditStrings() {
        logger.info("reached receiveeditstrings");
        final String relicStrings = Gdx.files.internal("localization/RelicStrings.json").readString("UTF-8");
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
    }

    public void receiveEditRelics() {
        logger.info("reached receiveeditrelics");
        logger.info("Adding relics");
        BaseMod.addRelic(new SeethingFireRelic(), RelicType.SHARED);
        BaseMod.addRelic(new HammerOfThorRelic(), RelicType.SHARED);
        BaseMod.addRelic(new AnvilOfTheGodsRelic(), RelicType.SHARED);
        logger.info("Done adding relics");
    }
/*
    public void receivePostDungeonInitialize() {
        logger.info("reached receivepostdungeoninitialize");
        RelicLibrary.getRelic(SeethingFireRelic.ID).makeCopy().instantObtain();
        RelicLibrary.getRelic(HammerOfThorRelic.ID).makeCopy().instantObtain();
        RelicLibrary.getRelic(AnvilOfTheGodsRelic.ID).makeCopy().instantObtain();
    }
*/
    public void receivePostCreateStartingRelics(AbstractPlayer.PlayerClass pclass, ArrayList<String> relicslist) {
        logger.info("reached receivepostcreatestartingrelics");
        relicslist.add(HammerOfThorRelic.ID);
        relicslist.add(SeethingFireRelic.ID);
        relicslist.add(AnvilOfTheGodsRelic.ID);
    }
}
