package thelm.jaopca.additions.modules;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.Loader;
import thelm.jaopca.additions.compat.CompatIndustrialCraft;
import thelm.jaopca.additions.compat.CompatThermalExpansion;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.utils.Utils;

public class ModulePlate extends ModuleBase {

	public static final ItemEntry PLATE_ENTRY = new ItemEntry(EnumEntryType.ITEM, "plate", new ModelResourceLocation("jaopca:plate#inventory"));

	@Override
	public String getName() {
		return "plate";
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(PLATE_ENTRY);
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("plate")) {
			if(Loader.isModLoaded("IC2")) {
				Utils.addShapelessOreRecipe(Utils.getOreStack("plate", entry, 1), new Object[] {
						"ingot"+entry.getOreName(),
						"craftingToolForgeHammer",
				});
				CompatIndustrialCraft.addRollingRecipe("ingot"+entry.getOreName(), Utils.getOreStack("plate", entry, 1));
				if(Utils.doesOreNameExist("block"+entry.getOreName())) {
					CompatIndustrialCraft.addBlockCutterRecipe("block"+entry.getOreName(), entry.getEnergyModifier()>1.5?8:5, Utils.getOreStack("plate", entry, 9));
				}
			}
		}
	}

	@Override
	public void postInit() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("plate")) {
			if(Loader.isModLoaded("thermalexpansion")) {
				CompatThermalExpansion.addPressRecipe(Utils.energyI(entry, 4000), Utils.getOreStack("ingot", entry, 1), Utils.getOreStack("plate", entry, 1));
			}
		}
	}
}
