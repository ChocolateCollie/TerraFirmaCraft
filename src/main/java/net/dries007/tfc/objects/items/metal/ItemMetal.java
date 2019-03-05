/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.items.metal;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.forge.ForgeableHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.util.IMetalObject;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

public class ItemMetal extends ItemTFC implements IMetalObject
{
    private static final Map<Metal, EnumMap<Metal.ItemType, ItemMetal>> TABLE = new HashMap<>();

    public static ItemMetal get(Metal metal, Metal.ItemType type)
    {
        return TABLE.get(metal).get(type);
    }

    protected final Metal metal;
    protected final Metal.ItemType type;

    public ItemMetal(Metal metal, Metal.ItemType type)
    {
        this.metal = metal;
        this.type = type;

        if (!TABLE.containsKey(metal))
            TABLE.put(metal, new EnumMap<>(Metal.ItemType.class));
        TABLE.get(metal).put(type, this);

        setNoRepair();
        OreDictionaryHelper.register(this, type);
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, type, metal.getRegistryName().getPath());
    }

    @Override
    public Metal getMetal(ItemStack stack)
    {
        return metal;
    }

    @Override
    public int getSmeltAmount(ItemStack stack)
    {
        if (!isDamageable() || !stack.isItemDamaged()) return type.getSmeltAmount();
        double d = (stack.getMaxDamage() - stack.getItemDamage()) / (double) stack.getMaxDamage() - .10;
        return d < 0 ? 0 : MathHelper.floor(type.getSmeltAmount() * d);
    }

    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        switch (type)
        {
            case HAMMER:
            case INGOT:
            case SCRAP:
            case LAMP:
            case TUYERE:
            case PICK_HEAD:
            case SHOVEL_HEAD:
            case AXE_HEAD:
            case HOE_HEAD:
            case CHISEL:
            case CHISEL_HEAD:
            case SWORD_BLADE:
            case MACE_HEAD:
            case SAW_BLADE:
            case JAVELIN_HEAD:
            case HAMMER_HEAD:
            case PROPICK:
            case PROPICK_HEAD:
            case KNIFE:
            case KNIFE_BLADE:
            case SCYTHE:
                return Size.SMALL;
            case SAW:
            case SHEET:
            case DOUBLE_SHEET:
                return Size.NORMAL;
            case ANVIL:
                return Size.HUGE;
            case DUST:
                return Size.VERY_SMALL;
            case NUGGET:
                return Size.TINY;
            default:
                return Size.LARGE;
        }
    }

    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        switch (type)
        {
            case DOUBLE_SHEET:
            case ANVIL:
            case HELMET:
            case GREAVES:
            case CHESTPLATE:
            case BOOTS:
                return Weight.HEAVY;
            case HOE:
            case DUST:
            case NUGGET:
            case LAMP:
            case TUYERE:
            case UNFINISHED_CHESTPLATE:
            case UNFINISHED_GREAVES:
            case UNFINISHED_HELMET:
            case UNFINISHED_BOOTS:
                return Weight.LIGHT;
            default:
                return Weight.MEDIUM;
        }
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack)
    {
        switch (type)
        {
            case DUST:
            case LAMP:
            case ANVIL:
            case SCRAP:
            case INGOT:
            case SHEET:
            case NUGGET:
            case AXE_HEAD:
            case HOE_HEAD:
            case MACE_HEAD:
            case PICK_HEAD:
            case SAW_BLADE:
            case CHISEL_HEAD:
            case HAMMER_HEAD:
            case KNIFE_BLADE:
            case SHOVEL_HEAD:
            case SWORD_BLADE:
            case DOUBLE_INGOT:
            case DOUBLE_SHEET:
            case JAVELIN_HEAD:
            case PROPICK_HEAD:
            case SCYTHE_BLADE:
                return true;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ForgeableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
    }

    @Override
    @Nonnull
    public EnumRarity getRarity(ItemStack stack)
    {
        switch (metal.getTier())
        {
            case TIER_I:
            case TIER_II:
                return EnumRarity.COMMON;
            case TIER_III:
                return EnumRarity.UNCOMMON;
            case TIER_IV:
                return EnumRarity.RARE;
            case TIER_V:
                return EnumRarity.EPIC;
        }
        return super.getRarity(stack);
    }

    public Metal.ItemType getType()
    {
        return type;
    }
}
