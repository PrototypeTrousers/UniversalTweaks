package mod.acgaming.universaltweaks.core;

import java.util.List;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;

import zone.rong.mixinbooter.ILateMixinLoader;

public class UTMixinLoader implements ILateMixinLoader
{
    @Override
    public List<String> getMixinConfigs()
    {
        return Lists.newArrayList(
            "mixins.mods.biomesoplenty.json",
            "mixins.mods.customspawner.json",
            "mixins.mods.epicsiegemod.json",
            "mixins.mods.forestry.json",
            "mixins.mods.storagedrawers.client.json",
            "mixins.mods.storagedrawers.server.json",
            "mixins.mods.thaumcraft.json",
            "mixins.mods.tconstruct.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        if (UTLoadingPlugin.isClient)
        {
            switch (mixinConfig)
            {
                case "mixins.mods.storagedrawers.client.json":
                case "mixins.mods.storagedrawers.server.json":
                    return Loader.isModLoaded("storagedrawers");
            }
        }
        switch (mixinConfig)
        {
            case "mixins.mods.biomesoplenty.json":
                return Loader.isModLoaded("biomesoplenty");
            case "mixins.mods.customspawner.json":
                return Loader.isModLoaded("customspawner");
            case "mixins.mods.epicsiegemod.json":
                return Loader.isModLoaded("epicsiegemod");
            case "mixins.mods.forestry.json":
                return Loader.isModLoaded("forestry");
            case "mixins.mods.storagedrawers.server.json":
                return Loader.isModLoaded("storagedrawers");
            case "mixins.mods.thaumcraft.json":
                return Loader.isModLoaded("thaumcraft");
            case "mixins.mods.tconstruct.json":
                return Loader.isModLoaded("tconstruct");
        }
        return false;
    }
}