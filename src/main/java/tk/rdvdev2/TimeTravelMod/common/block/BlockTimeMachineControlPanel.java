package tk.rdvdev2.TimeTravelMod.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BlockTimeMachineControlPanel extends tk.rdvdev2.TimeTravelMod.api.timemachine.block.BlockTimeMachineControlPanel {

    private String name = "timemachinecontrolpanel";

    public BlockTimeMachineControlPanel() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(3f)
                .lightValue(0 / 16)
                .variableOpacity()
        );
        setRegistryName(name);
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState p_getHarvestTool_1_) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_) {
        return 2;
    }
}
