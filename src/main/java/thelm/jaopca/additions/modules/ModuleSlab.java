package thelm.jaopca.additions.modules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thelm.jaopca.additions.block.BlockDoubleSlabBase;
import thelm.jaopca.additions.block.BlockSlabBase;
import thelm.jaopca.additions.item.ItemBlockSlabBase;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.ItemEntryGroup;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.block.BlockProperties;
import thelm.jaopca.api.utils.Utils;

public class ModuleSlab extends ModuleBase {

	public static final BlockProperties SLAB_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.IRON).
			setHardnessFunc(entry->5F).
			setResistanceFunc(entry->10F).
			setSoundType(SoundType.METAL).
			setBlockClass(BlockSlabBase.class).
			setItemBlockClass(ItemBlockSlabBase.class);
	public static final BlockProperties DOUBLE_SLAB_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.IRON).
			setHardnessFunc(entry->5F).
			setResistanceFunc(entry->10F).
			setSoundType(SoundType.METAL).
			setBlockClass(BlockDoubleSlabBase.class);

	public static final ArrayList<String> BLACKLIST = Lists.<String>newArrayList(
			"Quartz", "Prismarine"
			);
	
	public static final ItemEntry SLAB_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "slab", new ModelResourceLocation("jaopca:slab#inventory"), BLACKLIST).
			setProperties(SLAB_PROPERTIES).
			setOreTypes(EnumOreType.values());
	public static final ItemEntry DOUBLE_SLAB_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "doubleSlab", new ModelResourceLocation("jaopca:block#normal"), BLACKLIST).
			setProperties(DOUBLE_SLAB_PROPERTIES).
			setOreTypes(EnumOreType.values());

	@Override
	public String getName() {
		return "slab";
	}

	@Override
	public List<String> getDependencies() {
		return Lists.<String>newArrayList("block");
	}

	@Override
	public List<ItemEntryGroup> getItemRequests() {
		return Lists.<ItemEntryGroup>newArrayList(ItemEntryGroup.of(SLAB_ENTRY, DOUBLE_SLAB_ENTRY));
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("slab")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(Utils.getOreStack("slab", entry, 6), new Object[] {
					"BBB",
					'B', "block"+entry.getOreName(),
			}));
		}
	}
}
