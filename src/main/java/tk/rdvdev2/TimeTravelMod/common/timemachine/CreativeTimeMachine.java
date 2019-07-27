package tk.rdvdev2.TimeTravelMod.common.timemachine;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import tk.rdvdev2.TimeTravelMod.ModItems;
import tk.rdvdev2.TimeTravelMod.ModRegistries;
import tk.rdvdev2.TimeTravelMod.ModTriggers;
import tk.rdvdev2.TimeTravelMod.TimeTravelMod;
import tk.rdvdev2.TimeTravelMod.api.timemachine.TimeMachine;

public class CreativeTimeMachine extends TimeMachine {

    @Override
    public int getCooldownTime() {
        return 0;
    }

    private int tier = 0;

    @Override
    public int getTier() {
        if (tier == 0) {
            ModRegistries.timeLinesRegistry.forEach(timeLine -> tier = Math.max(timeLine.getMinTier(), tier));
        }
        return tier;
    }

    @Override
    public int[][] coreBlocksPos() {
        return new int[0][0];
    }

    @Override
    public int[][] basicBlocksPos() {
        return new int[0][0];
    }

    @Override
    public int[][] airBlocksPos() {
        return new int[0][0];
    }

    @Override
    public void run(World world, PlayerEntity playerIn, BlockPos controllerPos, Direction side) {
        if (isPlayerInside(world, controllerPos, side, playerIn) &&
                !isOverloaded(world, controllerPos, side)) {
            if (playerIn instanceof ServerPlayerEntity) {
                ModTriggers.ACCESS_TIME_MACHINE.trigger((ServerPlayerEntity) playerIn);
            }
            TimeTravelMod.proxy.displayTMGuiScreen(playerIn, this, controllerPos, side);
        }
    }

    @Override
    public boolean isBuilt(World world, BlockPos controllerPos, Direction side) {
        return true;
    }

    @Override
    public AxisAlignedBB getAirSpace(BlockPos controllerPos, Direction side) {
        return new AxisAlignedBB(
                controllerPos.getX() -1,
                controllerPos.getY() -1,
                controllerPos.getZ() -1,
                controllerPos.getX() +1,
                controllerPos.getY() +1,
                controllerPos.getZ() +1
        );
    }

    @Override
    public boolean isPlayerInside(World world, BlockPos controllerPos, Direction side, PlayerEntity player) {
        return ItemStack.areItemsEqual(player.inventory.getCurrentItem(), new ItemStack(ModItems.creativeTimeMachine, 1));
    }

    @Override
    public void teleporterTasks(Entity entity, World worldIn, World worldOut, BlockPos controllerPos, Direction side, boolean shouldBuild) {
        IChunk chunk = worldIn.getChunk(controllerPos);
        chunk = worldIn.getChunkProvider().getChunk(chunk.getPos().x, chunk.getPos().z, ChunkStatus.FULL, true);
        int height = chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) entity.posX, (int) entity.posZ);
        entity.setPosition(entity.posX, height + 1, entity.posZ);
    }

    @Override
    public boolean isOverloaded(World world, BlockPos controllerPos, Direction side) {
        return false;
    }

    @Override
    public int getCorruptionMultiplier() {
        return 0;
    }
}
