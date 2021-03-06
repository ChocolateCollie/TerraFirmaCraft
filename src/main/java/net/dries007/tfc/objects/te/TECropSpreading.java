/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.te;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.nbt.NBTTagCompound;

import net.dries007.tfc.util.agriculture.Crop;

@ParametersAreNonnullByDefault
public class TECropSpreading extends TETickCounter
{
    private int maxGrowthStage; // The max value this crop can grow to
    private int baseAge; // The current age, including all spreading attempts

    public TECropSpreading()
    {
        this.maxGrowthStage = 0;
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("maxGrowthStage", maxGrowthStage);
        nbt.setInteger("baseAge", baseAge);
        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        maxGrowthStage = nbt.getInteger("maxGrowthStage");
        baseAge = nbt.getInteger("baseAge");
        super.readFromNBT(nbt);
    }

    public void onPlaced()
    {
        // Calculate initial max growth stage
        maxGrowthStage = 3 + world.getBlockState(pos).getValue(Crop.STAGE_8);
        if (maxGrowthStage >= 8)
        {
            maxGrowthStage = 7;
        }
        // Reset counter
        resetCounter();
    }

    public int getMaxGrowthStage()
    {
        return maxGrowthStage;
    }

    public void setMaxGrowthStage(int maxGrowthStage)
    {
        this.maxGrowthStage = maxGrowthStage;
        if (this.maxGrowthStage > 7)
        {
            this.maxGrowthStage = 7;
        }
        markDirty();
    }

    public int getBaseAge()
    {
        return baseAge;
    }

    public void setBaseAge(int baseAge)
    {
        this.baseAge = baseAge;
    }
}
