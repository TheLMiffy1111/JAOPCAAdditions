package thelm.jaopca.additions.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.block.IBlockWithProperty;
import thelm.jaopca.api.utils.JAOPCAStateMap;
import thelm.jaopca.api.utils.Utils;

public class BlockStairsBase extends BlockStairs implements IBlockWithProperty {

	public final IOreEntry oreEntry;
	public final ItemEntry itemEntry;

	public BlockStairsBase(Material material, MapColor mapColor, ItemEntry itemEntry, IOreEntry oreEntry) {
		super(getModelState(oreEntry));
		setUnlocalizedName("jaopca."+itemEntry.name);
		setRegistryName("jaopca:block_"+itemEntry.name+oreEntry.getOreName());
		this.oreEntry = oreEntry;
		this.itemEntry = itemEntry;
	}

	@Override
	public IOreEntry getOreEntry() {
		return oreEntry;
	}

	@Override
	public ItemEntry getItemEntry() {
		return itemEntry;
	}

	public static IBlockState getModelState(IOreEntry oreEntry) {
		return Block.getBlockFromItem(Utils.getOreStack("block", oreEntry, 1).getItem()).getDefaultState();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomStateMapper(this, new JAOPCAStateMap.Builder(new ResourceLocation(this.getItemEntry().itemModelLocation.toString().split("#")[0])).build());
	}

	@Override
	public BlockStairsBase setSoundType(SoundType sound) {
		return this;
	}

	@Override
	public BlockStairsBase setLightOpacity(int opacity) {
		return this;
	}

	@Override
	public BlockStairsBase setLightLevel(float value) {
		super.setLightLevel(value);
		return this;
	}

	@Override
	public BlockStairsBase setResistance(float resistance) {
		return this;
	}

	@Override
	public BlockStairsBase setHardness(float hardness) {
		return this;
	}
}
