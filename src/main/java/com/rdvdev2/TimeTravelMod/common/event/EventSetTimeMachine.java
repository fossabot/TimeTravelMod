package com.rdvdev2.TimeTravelMod.common.event;

import com.rdvdev2.TimeTravelMod.api.timemachine.TimeMachine;
import com.rdvdev2.TimeTravelMod.common.registry.RegistryTimeMachines;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventSetTimeMachine extends Event {

    private RegistryTimeMachines registry;

    public EventSetTimeMachine(RegistryTimeMachines registry) {
        super();
        this.registry = registry;
    }

    public TimeMachine getTimeMachine(IBlockState block) {
        return registry.getCompatibleTimeMachine(block);
    }
}
