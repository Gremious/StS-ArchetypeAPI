package archetypeAPI;

import archetypeAPI.archetypes.AbstractArchetype;
import archetypeAPI.util.IDCheckDontTouchPls;
import archetypeAPI.util.TextureLoader;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PreStartGameSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;

@SpireInitializer
public class ArchetypeAPI implements
        EditStringsSubscriber,
        PostInitializeSubscriber,
        EditCardsSubscriber,
        PreStartGameSubscriber {
    public static final Logger logger = LogManager.getLogger(ArchetypeAPI.class.getName());
    private static String modID;
    
    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Archetype API";
    private static final String AUTHOR = "Gremious";
    private static final String DESCRIPTION = "An API for Slay the Spire to select/add card Archetypes for any characters." +
            " Also stops the overflow of cards if you have a billion content mods installed!" +
            " \n \n Huge huge huge thanks to: Kio for BASICALLY DOING ALL THE FRONT-END WORK WOW THANK YOUUUU, fiiiiilth for neow patching help, Jin for making the selection screens actually work, Vex for archetype card decision and playtesting, Rattus for playetesting and original wiki help, and all of #modding for being nice ^_^";
    public static final String BADGE_IMAGE = "archetypeAPIResources/images/Badge.png";
    
    private static final String ARCHETYPES_DIR = "archetypes";
    
    public static Properties archetypeSettingsDefaults = new Properties();
    public static final String PROP_SELECT_ARCHETYPES = "selectArchetypes";
    public static boolean selectArchetypes = false;
    public static final String PROP_ENABLE_NON_API_CARDS = "enableNonAPICards";
    public static boolean enableNonAPICards = false;
    
    public static Map<AbstractPlayer.PlayerClass, Integer> characterCardNums = new HashMap<>();
    
    public ArchetypeAPI() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("archetypeAPI");
        
        archetypeSettingsDefaults.setProperty(PROP_SELECT_ARCHETYPES, "FALSE");
        archetypeSettingsDefaults.setProperty(PROP_ENABLE_NON_API_CARDS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("archetypeAPI", "ArchetypeAPIConfig", archetypeSettingsDefaults);
            config.load();
            selectArchetypes = config.getBool(PROP_SELECT_ARCHETYPES);
            enableNonAPICards = config.getBool(PROP_ENABLE_NON_API_CARDS);
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
        
        
        ModLabeledToggleButton selectArchetypesButton = new ModLabeledToggleButton("Select Archetypes at the start of each run.",
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
        });
        ModLabeledToggleButton enableNonAPIButton = new ModLabeledToggleButton("After manually choosing archetypes, should cards from content mods not registered with \n Archetype API be added? \n Example: Choosing only 1 Silent archetype, but having \"Mod with no ArchAPI support installed\", \n ALL the cards from that mod will be added on top of the ones you selected.\n This option has no effect when the 'archetype selection' is off, as non-API mod cards will always be added.",
                350.0f, 400.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                selectArchetypes, settingsPanel, (label) -> {
        }, (button) -> {
            selectArchetypes = button.enabled;
            try {
                SpireConfig config = new SpireConfig("archetypeAPI", "ArchetypeAPIConfig", archetypeSettingsDefaults);
                config.setBool(PROP_ENABLE_NON_API_CARDS, enableNonAPICards);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(selectArchetypesButton);
        settingsPanel.addUIElement(enableNonAPIButton);
    
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        characterCardNums.putIfAbsent(AbstractPlayer.PlayerClass.IRONCLAD, 72);
        characterCardNums.putIfAbsent(AbstractPlayer.PlayerClass.THE_SILENT, 71);
        characterCardNums.putIfAbsent(AbstractPlayer.PlayerClass.DEFECT, 71);
        
        loadBaseArchetypes();
        loadArchetypesDirectory();
    }
    
    // =============== / POST-INITIALIZE/ =================
    private static void loadBaseArchetypes() {
        loadArchetypes("archetypeAPIResources/localization/eng/archetypes/");
    }
    
    @SuppressWarnings("unused")
    public static void setCharacterDefaultNumOfCards(AbstractPlayer.PlayerClass characterEnum, int numberOfCards){
        characterCardNums.putIfAbsent(characterEnum, numberOfCards);
    }
    
    /**
     * takes the path to your archetype json directory (not file!) and loads all the archetype jsons found.
     * @param pathPrefix - the directory of your archetype files
     * <p> Example:  {@code loadArchetypes("myModResources/localization/eng/archetypes/");}
     * <p> NOT! {@code loadArchetypes("myModResources/localization/eng/archetypes/archetypeForSilent.json");}
     */
    public static void loadArchetypes(String pathPrefix) {
        try {
            Class<?> caller = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            // Find files
            CodeSource src = caller.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jarURL = src.getLocation();
                try {
                    JarFile jarFile = new JarFile(Paths.get(jarURL.toURI()).toFile());
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().startsWith(pathPrefix) && entry.getName().endsWith(".json")) {
                            FileHandle file = Gdx.files.internal(entry.getName());
                            AbstractArchetype.readArchetypeJsonFile(file);
                        }
                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO
            e.printStackTrace();
        }
    }
    
    /**
     * Loads any archetype jsons found in the "archetypes" folder in your sts install directory.
     * <p> This folder is in the same place your "mods" folder and "SlayTheSpire.exe" is.
     * <p> Keep in mind that both "mods" and "archetypes" folders need to be created manually if they are not there.
     */
    private static void loadArchetypesDirectory() {
        File dir = new File(ARCHETYPES_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".json"));
        if (files == null) {
            return;
        }
        
        for (File file : files) {
            AbstractArchetype.readArchetypeJsonFile(new FileHandle(file));
        }
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
    
    @Override
    public void receivePreStartGame() {
        if (!CardCrawlGame.loadingSave) {
            cardsOfTheArchetypesInUse.clear();
        }
    }
    
    // ====== YOU CAN EDIT AGAIN ======
}
