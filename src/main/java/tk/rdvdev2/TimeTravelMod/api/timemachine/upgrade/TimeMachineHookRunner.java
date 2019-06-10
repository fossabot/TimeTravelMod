package tk.rdvdev2.TimeTravelMod.api.timemachine.upgrade;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.rdvdev2.TimeTravelMod.api.timemachine.TimeMachine;

public class TimeMachineHookRunner extends TimeMachine {

    TimeMachine tm;
    TimeMachineUpgrade[] upgrades;

    public TimeMachineHookRunner(TimeMachine tm, TimeMachineUpgrade[] upgrades) {
        this.tm = tm;
        this.upgrades = upgrades;
    }

    public TimeMachine removeHooks() {
        return this.tm;
    }

    @Override
    public int getCooldownTime() {
        return tm.getCooldownTime();
    }

    @Override
    public int getTier() {
        return runHooks(tm.getTier(), TimeMachineHook.TierHook.class);
    }

    @Override
    public int[][] coreBlocksPos() {
        return tm.coreBlocksPos();
    }

    @Override
    public int[][] basicBlocksPos() {
        return tm.basicBlocksPos();
    }

    @Override
    public int[][] airBlocksPos() {
        return tm.airBlocksPos();
    }

    @Override
    public BlockState[] getControllerBlocks() {
        return tm.getControllerBlocks();
    }

    @Override
    public BlockState[] getCoreBlocks() {
        return tm.getCoreBlocks();
    }

    @Override
    public BlockState[] getBasicBlocks() {
        return tm.getBasicBlocks();
    }

    @Override
    public BlockPos[] getCoreBlocksPos(Direction side) {
        return tm.getCoreBlocksPos(side);
    }

    @Override
    public BlockPos[] getBasicBlocksPos(Direction side) {
        return tm.getBasicBlocksPos(side);
    }

    @Override
    public BlockPos[] getAirBlocksPos(Direction side) {
        return tm.getAirBlocksPos(side);
    }

    @Override
    public int getEntityMaxLoad() {
        return runHooks(tm.getEntityMaxLoad(), TimeMachineHook.EntityMaxLoadHook.class);
    }

    @Override
    public BlockState[] getBlocks() {
        return tm.getBlocks();
    }

    @Override
    public void run(World world, PlayerEntity playerIn, BlockPos controllerPos, Direction side) {
        if (runVoidHooks(TimeMachineHook.RunHook.class, world, playerIn, controllerPos, side)) return;
        tm.run(world, playerIn, controllerPos, side);
    }

    @Override
    public boolean triggerTemporalExplosion(World world, BlockPos controllerPos, Direction side) {
        return tm.triggerTemporalExplosion(world, controllerPos, side);
    }

    @Override
    public boolean isBuilt(World world, BlockPos controllerPos, Direction side) {
        return tm.isBuilt(world, controllerPos, side);
    }

    @Override
    public boolean isOverloaded(World world, BlockPos controllerPos, Direction side) {
        return tm.isOverloaded(world, controllerPos, side);
    }

    @Override
    public boolean isPlayerInside(World world, BlockPos controllerPos, Direction side, PlayerEntity player) {
        return tm.isPlayerInside(world, controllerPos, side, player);
    }

    @Override
    public AxisAlignedBB getAirSpace(BlockPos controllerPos, Direction side) {
        return tm.getAirSpace(controllerPos, side);
    }

    @Override
    public void teleporterTasks(Entity entity, World worldIn, World worldOut, BlockPos controllerPos, Direction side) {
        tm.teleporterTasks(entity, worldIn, worldOut, controllerPos, side);
    }

    @Override
    public boolean isCooledDown(World world, BlockPos controllerPos, Direction side) {
        return tm.isCooledDown(world, controllerPos, side);
    }

    private <T> T runHooks(T original, Class<? extends TimeMachineHook> clazz, Object... args) {
        T result = original;
        for (TimeMachineUpgrade upgrade:upgrades) {
            result = upgrade.runHook(result, clazz, this, args);
        }
        return result;
    }

    private boolean runVoidHooks(Class<? extends TimeMachineHook> clazz, Object... args) {
        for(TimeMachineUpgrade upgrade:upgrades) {
            if (upgrade.runVoidHook(clazz, this, args))
                return true;
        }
        return false;
    }
}
