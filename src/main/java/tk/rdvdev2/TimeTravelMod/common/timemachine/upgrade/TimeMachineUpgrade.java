package tk.rdvdev2.TimeTravelMod.common.timemachine.upgrade;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import tk.rdvdev2.TimeTravelMod.api.timemachine.TimeMachine;
import tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineHook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

public class TimeMachineUpgrade extends ForgeRegistryEntry<tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineUpgrade> implements tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineUpgrade {

    private ArrayList<TimeMachineHook> hooks;
    private TimeMachine[] compatibleTMs;
    private HashSet<Class<? extends TimeMachineHook>> exclusiveHooks = new HashSet<>();

    public TimeMachineUpgrade() {
        this.hooks = new ArrayList<TimeMachineHook>(0);
    }

    public tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineUpgrade addHook(TimeMachineHook hook) {
        addHook(hook, false);
        return this;
    }

    public tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineUpgrade addHook(TimeMachineHook hook, boolean exclusiveMode) {
        this.hooks.add(hook);
        if (exclusiveMode) {
            exclusiveHooks.add(hook.getClass());
        }
        return this;
    }

    public tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineUpgrade addAllHooks(TimeMachineHook... hooks) {
        for (TimeMachineHook hook:hooks) {
            this.addHook(hook);
        }
        return this;
    }

    public <T> T runHook(Optional<T> original, Class<? extends TimeMachineHook> clazz, TimeMachine tm, Object... args) {
        Optional<T> result = original;
        for (TimeMachineHook hook:this.hooks) {
            if (clazz.isInstance(hook)) {
                result = Optional.of((T)(hook.run(result, tm, args)));
            }
        }
        return result.orElse(original.orElseThrow(RuntimeException::new));
    }

    public boolean runVoidHook(Class<? extends TimeMachineHook> clazz, TimeMachine tm, Object... args) {
        for (TimeMachineHook hook:this.hooks) {
            if (clazz.isInstance(hook)) {
                hook.run(null, tm, args);
                return true;
            }
        }
        return false;
    }

    public TimeMachine[] getCompatibleTMs() {
        return compatibleTMs;
    }

    public tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade.TimeMachineUpgrade setCompatibleTMs(TimeMachine... compatibleTMs) {
        this.compatibleTMs = compatibleTMs;
        return this;
    }

    public boolean isExclusiveHook(Class<? extends TimeMachineHook> hook) {
        if (exclusiveHooks.isEmpty()) return false;
        Iterator<Class<? extends TimeMachineHook>> it = exclusiveHooks.iterator();
        while (it.hasNext()) if (hook.isAssignableFrom(it.next())) return true;
        return false;
    }

    public final TranslationTextComponent getName() { // tmupgrade.modid.registryname.name
        return new TranslationTextComponent("tmupgrade."+getRegistryName().getNamespace()+"."+getRegistryName().getPath()+".name");
    }

    public final TranslationTextComponent getDescription() { // tmupgrade.modid.registryname.description
        return new TranslationTextComponent("tmupgrade."+getRegistryName().getNamespace()+"."+getRegistryName().getPath()+".description");
    }
}
