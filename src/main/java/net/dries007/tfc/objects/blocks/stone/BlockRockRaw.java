/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.stone;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.util.ICollapsableBlock;
import net.dries007.tfc.util.OreDictionaryHelper;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockRaw extends BlockRockVariant implements ICollapsableBlock
{

    public BlockRockRaw(Rock.Type type, Rock rock)
    {
        super(type, rock);
    }

    //todo: add collapsable mechanics


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (OreDictionaryHelper.doesStackMatchOre(stack, "hammer"))
        {
            if (!worldIn.isRemote)
            {
                // Create a stone anvil
                BlockStoneAnvil block = BlockStoneAnvil.get(this.rock);
                if (block != null)
                {
                    worldIn.setBlockState(pos, block.getDefaultState());
                    TFCGuiHandler.openGui(worldIn, pos, playerIn, TFCGuiHandler.Type.ANVIL);
                }
            }
            return true;
        }
        return false;
    }
}
