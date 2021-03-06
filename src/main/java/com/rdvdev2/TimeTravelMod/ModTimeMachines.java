package com.rdvdev2.TimeTravelMod;

import com.rdvdev2.TimeTravelMod.api.timemachine.TimeMachine;
import com.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineUpgrade;
import com.rdvdev2.TimeTravelMod.common.timemachine.CreativeTimeMachine;
import com.rdvdev2.TimeTravelMod.common.timemachine.Tier1TimeMachine;
import com.rdvdev2.TimeTravelMod.common.timemachine.hook.TrackerHooks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.rdvdev2.TimeTravelMod.TimeTravelMod.MODID;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModTimeMachines {

    public static final TimeMachine TIER_1 = TimeMachine.fromTemplate(new Tier1TimeMachine()).setRegistryName(MODID, "tier1");
    public static final TimeMachine CREATIVE = new CreativeTimeMachine().setRegistryName(MODID, "creative");

    @SubscribeEvent
    public static void registerTimeMachines(RegistryEvent.Register<TimeMachine> event) {
        event.getRegistry().registerAll(
                TIER_1,
                CREATIVE
        );
    }

    @SubscribeEvent
    public static void remapLegacyNames(RegistryEvent.MissingMappings<TimeMachine> event) {
        event.getMappings().forEach(mapping -> {
            if (mapping.key.equals(new ResourceLocation(MODID, "tmtier1"))) {
                mapping.remap(TIER_1);
            } else if (mapping.key.equals(new ResourceLocation(MODID, "tmcreative"))) {
                mapping.remap(CREATIVE);
            }
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Upgrades {

        public static final TimeMachineUpgrade TRACKER = TimeMachineUpgrade.getNew().addHook(TrackerHooks.HOOKS[0], true).setRegistryName(MODID, "tracker").setCompatibleTMs(TIER_1);

        @SubscribeEvent
        public static void registerUpgrades(RegistryEvent.Register<TimeMachineUpgrade> event) {
            event.getRegistry().registerAll(
                    TRACKER
            );
        }
    }
}
