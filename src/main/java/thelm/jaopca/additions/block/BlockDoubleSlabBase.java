package thelm.jaopca.additions.block;

import java.util.Random;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.block.BlockBase;

public class BlockDoubleSlabBase extends BlockBase {

	public BlockDoubleSlabBase(Material material, MapColor mapColor, ItemEntry itemEntry, IOreEntry oreEntry) {
		super(material, mapColor, itemEntry, oreEntry);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(JAOPCAApi.BLOCKS_TABLE.get("slab", this.getOreEntry().getOreName()));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(JAOPCAApi.BLOCKS_TABLE.get("slab", this.getOreEntry().getOreName()));
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 2;
	}
}
