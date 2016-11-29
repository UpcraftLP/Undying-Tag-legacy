package mod.upcraftlp.tagofundying;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class AnvilHandler {

	@SubscribeEvent
	public static void onTryCrafting(AnvilUpdateEvent event) {
		if(event.getLeft().getItem() == Items.NAME_TAG && event.getRight().getItem() == Items.TOTEM) {
			NonNullList<ItemStack> subItems = NonNullList.<ItemStack>create();
			Main.UNDYING_TAG.getSubItems(null, null, subItems);
			event.setOutput(subItems.get(0));
			event.setCost(5);
		}
	}
	
}
