package tk.rdvdev2.TimeTravelMod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import tk.rdvdev2.TimeTravelMod.common.networking.DimensionTpPKT;
import tk.rdvdev2.TimeTravelMod.common.networking.OpenTmGuiPKT;

public class ModPacketHandler {
    private static final String PROTOCOL_VERSION = "TTM" + 1 + (ModConfig.COMMON.enableExperimentalFeatures.get() ? "-EXPERIMENTAL" : "");
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(TimeTravelMod.MODID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void init() {
        int disc = 0;

        CHANNEL.registerMessage(disc++, DimensionTpPKT.class, DimensionTpPKT::encode, DimensionTpPKT::decode, DimensionTpPKT.Handler::handle);
        CHANNEL.registerMessage(disc++, OpenTmGuiPKT.class, OpenTmGuiPKT::encode, OpenTmGuiPKT::decode, OpenTmGuiPKT.Handler::handle);
    }
}
