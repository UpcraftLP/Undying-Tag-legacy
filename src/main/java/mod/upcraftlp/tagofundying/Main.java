package mod.upcraftlp.tagofundying;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(name = Reference.MODNAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSION, modid = Reference.MODID, canBeDeactivated = false, updateJSON = Reference.INTERNAL_UPDATE_URL)
public class Main {

	@Instance
	public static Main instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.register(UNDYING_TAG);
		if(event.getSide() == Side.CLIENT) ModelLoader.setCustomModelResourceLocation(UNDYING_TAG, 0, new ModelResourceLocation(Items.NAME_TAG.getRegistryName(), "inventory"));
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	
	public static Item UNDYING_TAG = new ItemUndyingTag();
	
	public static final String KEY_UNDYING = "undying";
	public static final String KEY_SPAWN = "spawnpoint";
	
}
