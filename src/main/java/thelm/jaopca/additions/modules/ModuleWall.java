package thelm.jaopca.additions.modules;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thelm.jaopca.additions.block.BlockWallBase;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.block.BlockProperties;
import thelm.jaopca.api.utils.Utils;

public class ModuleWall extends ModuleBase {

	public static final BlockProperties WALL_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.IRON).
			setHardnessFunc(entry->5F).
			setResistanceFunc(entry->10/3F).
			setLightOpacityFunc(entry->0).
			setSoundType(SoundType.METAL).
			setBlockClass(BlockWallBase.class);

	public static final ItemEntry WALL_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "wall", new ModelResourceLocation("jaopca:wall#inventory")).
			setProperties(WALL_PROPERTIES).
			setOreTypes(EnumOreType.DUSTLESS);

	@Override
	public String getName() {
		return "wall";
	}

	@Override
	public List<String> getDependencies() {
		return Lists.<String>newArrayList("block");
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(WALL_ENTRY);
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("wall")) {
			Utils.addShapedOreRecipe(Utils.getOreStack("wall", entry, 12), new Object[] {
					"BBB",
					"BBB",
					'B', "block"+entry.getOreName(),
			});
		}
	}
}
