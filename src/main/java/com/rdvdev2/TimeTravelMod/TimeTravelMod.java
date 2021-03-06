package com.rdvdev2.TimeTravelMod;

import com.rdvdev2.TimeTravelMod.client.itemgroup.TTMItemGroup;
import com.rdvdev2.TimeTravelMod.common.world.VanillaBiomesFeatures;
import com.rdvdev2.TimeTravelMod.common.world.dimension.oldwest.village.OldWestVillagePools;
import com.rdvdev2.TimeTravelMod.proxy.ClientProxy;
import com.rdvdev2.TimeTravelMod.proxy.CommonProxy;
import com.rdvdev2.TimeTravelMod.proxy.IProxy;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.rdvdev2.TimeTravelMod.TimeTravelMod.MODID;

@Mod(MODID)
public class TimeTravelMod {

    public static final String MODID = "timetravelmod";

    public static final IProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static final Logger LOGGER = LogManager.getLogger("TIMETRAVELMOD");

    public static final ItemGroup TAB_TTM = new TTMItemGroup();

    public TimeTravelMod() {
        PROXY.modConstructor(this);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        TimeTravelMod.LOGGER.info("Time Travel Mod is in common setup state.");
        ModPacketHandler.init();
        VanillaBiomesFeatures.register();
        ModCapabilities.register();
        OldWestVillagePools.init();
        ModTriggers.register();
        ModBiomes.addBiomeTypes();
    }
}
