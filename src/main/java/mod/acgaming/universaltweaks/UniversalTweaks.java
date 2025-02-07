package mod.acgaming.universaltweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.bugfixes.UTHelp;
import mod.acgaming.universaltweaks.bugfixes.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;
import mod.acgaming.universaltweaks.tweaks.UTAttributes;
import mod.acgaming.universaltweaks.tweaks.UTLoadSound;
import mod.acgaming.universaltweaks.tweaks.UTTutorialHints;
import mod.acgaming.universaltweaks.tweaks.endportal.UTEndPortalParallax;
import mod.acgaming.universaltweaks.tweaks.stronghold.UTStronghold;
import mod.acgaming.universaltweaks.tweaks.stronghold.worldgen.SafeStrongholdWorldGenerator;
import mod.acgaming.universaltweaks.tweaks.swingthroughgrass.UTSwingThroughGrassLists;
import mod.acgaming.universaltweaks.util.UTObsoleteModsHandler;
import mod.acgaming.universaltweaks.util.UTPacketHandler;

@Mod(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME, version = UniversalTweaks.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = UniversalTweaks.DEPENDENCIES)
public class UniversalTweaks
{
    public static final String MODID = "universaltweaks";
    public static final String NAME = "Universal Tweaks";
    public static final String VERSION = "1.12.2-1.3.0";
    public static final String DEPENDENCIES = "required-after:mixinbooter;after:biomesoplenty;after:customspawner;after:epicsiegemod;after:forestry;after:storagedrawers;after:tconstruct;after:thaumcraft";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        UTPacketHandler.init();
        if (UTConfig.TWEAKS_ENTITIES.utAttributesToggle) UTAttributes.utSetAttributes();
        if (UTConfig.TWEAKS_WORLD.utStrongholdToggle) GameRegistry.registerWorldGenerator(new SafeStrongholdWorldGenerator(), Integer.MAX_VALUE);
        LOGGER.info(NAME + " pre-initialized");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (UTConfig.BUGFIXES_BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) UTBlockOverlayLists.initLists();
        if (UTConfig.TWEAKS_MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) UTSwingThroughGrassLists.initLists();
        if (UTConfig.TWEAKS_WORLD.utStrongholdToggle) MinecraftForge.TERRAIN_GEN_BUS.register(new UTStronghold());
        LOGGER.info(NAME + " initialized");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LOGGER.info(NAME + " post-initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void postInitClient(FMLPostInitializationEvent event)
    {
        if (UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfig.TweaksMiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) UTLoadSound.initLists();
        if (UTConfig.TWEAKS_MISC.utTutorialHintsToggle) UTTutorialHints.utTutorialHints();
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void initClient(FMLInitializationEvent event)
    {
        if (UTConfig.TWEAKS_MISC.utEndPortalParallaxToggle) UTEndPortalParallax.initRenderer();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        if (UTConfig.BUGFIXES_MISC.utHelpToggle) UTHelp.onServerStarting(event);
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        if (UTConfig.BUGFIXES_MISC.utHelpToggle) UTHelp.onServerStarted(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        if (UTConfig.DEBUG.utLoadingTimeToggle) LOGGER.info("The game loaded in approximately {} seconds", (System.currentTimeMillis() - UTLoadingPlugin.launchTime) / 1000F);
        if (UTObsoleteModsHandler.obsoleteModsMessage().size() > 5) UniversalTweaks.LOGGER.warn(String.join(System.lineSeparator(), UTObsoleteModsHandler.obsoleteModsMessage()));
    }
}