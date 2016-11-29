package mod.upcraftlp.tagofundying;

import net.minecraft.entity.EntityLiving;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class DeathHandler {

	@SubscribeEvent
	public static void onNonPlayerDeath(LivingDeathEvent event) {
		if(event.getEntityLiving() instanceof EntityLiving)  {
			EntityLiving entity = (EntityLiving) event.getEntityLiving();
			if(entity.posY < 0) { //used to prevent infinite falling
				FMLCommonHandler.instance().getFMLLogger().info("[" + Reference.MODID + "] Unable to respawn tagged entity at [" + entity.getPosition().getX() + ", " + entity.getPosition().getY() + ", " + entity.getPosition().getZ() + "], y-Level was less than 0.");
				return;
			}
			NBTTagCompound nbt = entity.getEntityData();
			if(nbt.getByte(Main.KEY_UNDYING) == (byte) 1) {
				FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(entity.dimension).spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, entity.posX, entity.posY, entity.posZ, 50, 0.5D, 1.0D, 0.5D, 0.005D);
				entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0f, 0.7f);
				BlockPos posToSpawn = entity.world.getSpawnPoint();
				if(nbt.hasKey(Main.KEY_SPAWN)) {
					posToSpawn = NBTUtil.getPosFromTag((NBTTagCompound) nbt.getTag(Main.KEY_SPAWN));
				}
				entity.setHealth(entity.getMaxHealth());
				entity.setPositionAndUpdate(posToSpawn.getX() + 0.5D, posToSpawn.getY(), posToSpawn.getZ() + 0.5D);
				entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0f, 0.7f);
				FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(entity.dimension).spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, entity.posX, entity.posY, entity.posZ, 50, 0.5D, 1.0D, 0.5D, 0.005D);
				event.setCanceled(true);
			}
		}
	}
}
