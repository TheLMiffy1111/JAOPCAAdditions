package thelm.jaopca.additions.compat;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CompatIndustrialCraft {

	public static void addCompressorRecipe(Object input, ItemStack output) {
		IRecipeInput ri = null;
		if(input instanceof String) {
			ri = Recipes.inputFactory.forOreDict((String)input);
		}
		if(input instanceof ItemStack) {
			ri = Recipes.inputFactory.forStack((ItemStack)input);
		}
		Recipes.compressor.addRecipe(ri, null, false, output);
	}
	
	public static void addRollingRecipe(Object input, ItemStack output) {
		IRecipeInput ri = null;
		if(input instanceof String) {
			ri = Recipes.inputFactory.forOreDict((String)input);
		}
		if(input instanceof ItemStack) {
			ri = Recipes.inputFactory.forStack((ItemStack)input);
		}
		Recipes.metalformerRolling.addRecipe(ri, null, false, output);
	}

	public static void addBlockCutterRecipe(Object input, int hardness, ItemStack output) {
		IRecipeInput ri = null;
		if(input instanceof String) {
			ri = Recipes.inputFactory.forOreDict((String)input);
		}
		if(input instanceof ItemStack) {
			ri = Recipes.inputFactory.forStack((ItemStack)input);
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("hardness", hardness);
		Recipes.blockcutter.addRecipe(ri, nbt, false, output);
	}
}
