package mod.upcraftlp.tagofundying;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemUndyingTag extends ItemNameTag {

	public ItemUndyingTag() {
		this.setUnlocalizedName(Items.NAME_TAG.getUnlocalizedName().substring(5));
		this.setRegistryName("undying_tag");
		this.setCreativeTab(CreativeTabs.TOOLS);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add("apply to any NPC to prevent it from despawning");
		tooltip.add(TextFormatting.RED + "Must be renamed in an anvil!");
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(super.itemInteractionForEntity(stack, playerIn, target, hand)) {
			target.getEntityData().setByte(Main.KEY_UNDYING, (byte) 1);
			BlockPos spawnPos = playerIn.getBedLocation(playerIn.dimension);
			if(stack.hasTagCompound()) {
				NBTTagCompound stackNBT = stack.getTagCompound();
				if(stackNBT.hasKey(Main.KEY_SPAWN)) spawnPos = NBTUtil.getPosFromTag((NBTTagCompound) stackNBT.getTag(Main.KEY_SPAWN));
			}
			if(spawnPos != null) {
				NBTTagCompound pos = NBTUtil.createPosTag(spawnPos);
				target.getEntityData().setTag(Main.KEY_SPAWN, pos);
			}
			playerIn.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 0.7f);
			return true;
		}
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(playerIn.isSneaking() && !worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(handIn);
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt == null) nbt = new NBTTagCompound();
			BlockPos pos = playerIn.getPosition();
			NBTTagCompound nbtPos = NBTUtil.createPosTag(pos);
			nbt.setTag(Main.KEY_SPAWN, nbtPos);
			playerIn.setHeldItem(handIn, stack);
			playerIn.sendMessage(new TextComponentString("Spawn location set to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "."));
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0f, 0.7f);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		ItemStack out = new ItemStack(Main.UNDYING_TAG);
		out.addEnchantment(Enchantments.VANISHING_CURSE, 1);
		subItems.add(out);
	}
}
