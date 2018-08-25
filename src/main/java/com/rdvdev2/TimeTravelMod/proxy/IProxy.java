package com.rdvdev2.TimeTravelMod.proxy;

import com.rdvdev2.TimeTravelMod.api.timemachine.ITimeMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
    void preInit(FMLPreInitializationEvent event);
    void init (FMLInitializationEvent event);
    void postInit (FMLPostInitializationEvent event);
    void displayTMGuiScreen(EntityPlayer player, ITimeMachine tm, BlockPos pos, EnumFacing side);
}
