package thelm.jaopca.additions.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import thelm.jaopca.additions.block.BlockStairsBase;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.block.BlockProperties;
import thelm.jaopca.api.utils.Utils;

public class ModuleStairs extends ModuleBase {

	public static final BlockProperties STAIRS_PROPERTIES = new BlockProperties().
			setBlockClass(BlockStairsBase.class);

	public static final ArrayList<String> BLACKLIST = Lists.<String>newArrayList(
			"Quartz", "Prismarine"
			);
	
	public static final ItemEntry STAIRS_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "stairs", new ModelResourceLocation("jaopca:stairs#inventory"), BLACKLIST).
			setProperties(STAIRS_PROPERTIES).
			setOreTypes(EnumOreType.values());

	@Override
	public String getName() {
		return "stairs";
	}

	@Override
	public List<String> getDependencies() {
		return Lists.<String>newArrayList("block");
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(STAIRS_ENTRY);
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("stairs")) {
			Utils.addShapedOreRecipe(Utils.getOreStack("stairs", entry, 8), new Object[] {
					"B  ",
					"BB ",
					"BBB",
					'B', "block"+entry.getOreName(),
			});
		}
	}
}
