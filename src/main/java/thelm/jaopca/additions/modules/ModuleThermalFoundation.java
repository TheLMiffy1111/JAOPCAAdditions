package thelm.jaopca.additions.modules;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import thelm.jaopca.additions.block.BlockHardenedGlassBase;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.block.BlockProperties;
import thelm.jaopca.api.utils.Utils;
import thelm.jaopca.modules.ModuleThermalExpansion;

public class ModuleThermalFoundation extends ModuleBase {

	public static final BlockProperties HARDENED_GLASS_PROPERTIES = new BlockProperties().
			setHardnessFunc(entry->3F).
			setResistanceFunc(entry->200F).
			setLightOpacityFunc(entry->0).
			setMaterialMapColor(Material.GLASS).
			setSoundType(SoundType.GLASS).
			setFull(false).
			setOpaque(false).
			setBlockClass(BlockHardenedGlassBase.class);

	public static final ItemEntry HARDENED_GLASS_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "glassHardened", new ModelResourceLocation("jaopca:glass_hardened#normal"), ImmutableList.<String>of(
			"Copper", "Tin", "Silver", "Lead", "Aluminium", "Nickel", "Platinum", "Iridium", "Mithril", "Steel", "Electrum", "Invar", "Bronze", "Constantan", "Signalum", "Lumium", "Enderium"
			)).setProperties(HARDENED_GLASS_PROPERTIES).
			setOreTypes(EnumOreType.INGOTS);

	@Override
	public String getName() {
		return "thermalfoundation";
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(HARDENED_GLASS_ENTRY);
	}

	@Override
	public void preInit() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("glassHardened")) {
			OreDictionary.registerOre("blockGlassHardened", Utils.getOreStack("glassHardened", entry, 1));
		}
	}

	@Override
	public void init() {
		if(Loader.isModLoaded("thermalexpansion")) {
			for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("glassHardened")) {
				ModuleThermalExpansion.replaceInductionSmelterRecipe(4000, Utils.getOreStack("ingot", entry, 1), Utils.getOreStack("dustObsidian", 4), Utils.getOreStack("glassHardened", entry, 2), null, 0);
				if(Utils.doesOreNameExist("dust"+entry.getOreName())) {
					ModuleThermalExpansion.replaceInductionSmelterRecipe(4000, Utils.getOreStack("dust", entry, 1), Utils.getOreStack("dustObsidian", 4), Utils.getOreStack("glassHardened", entry, 2), null, 0);
				}
			}
		}
	}
}
