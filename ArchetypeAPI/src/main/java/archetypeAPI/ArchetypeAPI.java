package archetypeAPI;

import archetypeAPI.archetypes.tests.brandNewMod.archetype.poisonArchetype;
import archetypeAPI.archetypes.tests.brandNewMod.cards.DiscardPoisonTestCard;
import archetypeAPI.archetypes.theSilent.basicSilent;
import archetypeAPI.util.IDCheckDontTouchPls;
import archetypeAPI.util.TextureLoader;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

@SpireInitializer
public class ArchetypeAPI implements
        EditStringsSubscriber,
        PostInitializeSubscriber,
        EditCardsSubscriber {
    public static final Logger logger = LogManager.getLogger(ArchetypeAPI.class.getName());
    private static String modID;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Archetype API";
    private static final String AUTHOR = "Gremious";
    private static final String DESCRIPTION = "An API for Slay the Spire to sort/select/add/generate card Archetypes in basegame classes.";
    public static final String BADGE_IMAGE = "archetypeAPIResources/images/Badge.png";

    public static Properties archetypeSettingsDefaults = new Properties();

    public static final String PROP_SELECT_ARCHETYPES = "selectArchetypes";
    public static final String PROP_NUMBER_OF_ARCHETYPES = "numOfArchetypes";
    public static boolean selectArchetypes = false;
    public static int numOfArchetypes = 0;

    public ArchetypeAPI() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("archetypeAPI");

        archetypeSettingsDefaults.setProperty(PROP_SELECT_ARCHETYPES, "FALSE");
        archetypeSettingsDefaults.setProperty(PROP_NUMBER_OF_ARCHETYPES, "5");
        try {
            SpireConfig config = new SpireConfig("archetypeAPI", "ArchetypeAPIConfig", archetypeSettingsDefaults);
            config.load();
            selectArchetypes = config.getBool(PROP_SELECT_ARCHETYPES);
            numOfArchetypes = config.getInt(PROP_NUMBER_OF_ARCHETYPES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done subscribing");
    }


    @SuppressWarnings("unused")
    public static void initialize() {
        ArchetypeAPI ArchetypeApiInit = new ArchetypeAPI();
        logger.info("Archetype API is on.");
    }

    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        logger.info("Done loading badge Image and mod options");


        ModLabeledToggleButton crazyBtn = new ModLabeledToggleButton("Replace 2 of your starting Ceremony cards with Crazy Rituals.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                selectArchetypes, settingsPanel, (label) -> {
        }, (button) -> {
            selectArchetypes = button.enabled;
            try {
                SpireConfig config = new SpireConfig("archetypeAPI", "ArchetypeAPIConfig", archetypeSettingsDefaults);
                config.setBool(PROP_SELECT_ARCHETYPES, selectArchetypes);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
            resetCharSelect();
        });
        settingsPanel.addUIElement(crazyBtn);

        basicSilent basicSilent = new basicSilent(true);
        poisonArchetype poisonArchetype = new poisonArchetype(true);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
    }

    // =============== / POST-INITIALIZE/ =================

    public void resetCharSelect() {
        ((ArrayList<CharacterOption>) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "options")).clear();
        CardCrawlGame.mainMenuScreen.charSelectScreen.initialize();
    }
    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // UI Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/" + getModID() + "-UI-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }


    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new DiscardPoisonTestCard());
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStrings.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = ArchetypeAPI.class.getResourceAsStream("/IDCheckStrings.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT

        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStrings.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = ArchetypeAPI.class.getResourceAsStream("/IDCheckStrings.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT

        String packageName = ArchetypeAPI.class.getPackage().getName(); // STILL NOT EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    // ====== YOU CAN EDIT AGAIN ======

}
