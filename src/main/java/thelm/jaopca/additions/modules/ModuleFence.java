package thelm.jaopca.additions.modules;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thelm.jaopca.additions.block.BlockFenceBase;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.block.BlockProperties;
import thelm.jaopca.api.utils.Utils;

public class ModuleFence extends ModuleBase {

	public static final BlockProperties FENCE_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.IRON).
			setHardnessFunc(entry->5F).
			setResistanceFunc(entry->10F).
			setSoundType(SoundType.METAL).
			setBlockClass(BlockFenceBase.class);
	
	public static final ItemEntry FENCE_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "fence", new ModelResourceLocation("jaopca:fence#inventory")).
			setProperties(FENCE_PROPERTIES).
			setOreTypes(EnumOreType.DUSTLESS);

	@Override
	public String getName() {
		return "fence";
	}

	@Override
	public List<String> getDependencies() {
		return Lists.<String>newArrayList("stick");
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(FENCE_ENTRY);
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("fence")) {
			String s = "ingot";
			switch(entry.getOreType()) {
			case GEM:
			case GEM_ORELESS:
				s = "gem";
				break;
			default:
				break;
			}
			Utils.addShapedOreRecipe(Utils.getOreStack("fence", entry, 3), new Object[] {
					"B#B",
					"B#B",
					'B', s+entry.getOreName(),
					'#', "stick"+entry.getOreName(),
			});
		}
	}
}
