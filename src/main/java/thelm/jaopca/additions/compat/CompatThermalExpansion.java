package thelm.jaopca.additions.compat;

import cofh.thermalexpansion.util.managers.dynamo.NumismaticManager;
import cofh.thermalexpansion.util.managers.machine.CompactorManager;
import cofh.thermalexpansion.util.managers.machine.CompactorManager.Mode;
import net.minecraft.item.ItemStack;

public class CompatThermalExpansion {

	public static void addPressRecipe(int energy, ItemStack input, ItemStack output) {
		CompactorManager.addRecipe(energy, input, output, Mode.PRESS);
	}

	public static void addMintRecipe(int energy, ItemStack input, ItemStack output) {
		CompactorManager.addRecipe(energy, input, output, Mode.MINT);
	}

	public static void addNumismaticFuel(ItemStack input, int energy) {
		NumismaticManager.addFuel(input, energy);
	}
}
