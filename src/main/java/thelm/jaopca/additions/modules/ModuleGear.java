package thelm.jaopca.additions.modules;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.utils.Utils;

public class ModuleGear extends ModuleBase {

	public static final ItemEntry GEAR_ENTRY = new ItemEntry(EnumEntryType.ITEM, "gear", new ModelResourceLocation("jaopca:gear#inventory"));

	@Override
	public String getName() {
		return "gear";
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(GEAR_ENTRY);
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("gear")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(Utils.getOreStack("gear", entry, 1), new Object[] {
					" o ",
					"oio",
					" o ",
					'o', "ingot"+entry.getOreName(),
					'i', "ingotIron",
			}));
		}
	}
}
