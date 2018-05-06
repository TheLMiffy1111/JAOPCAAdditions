package thelm.jaopca.additions.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.block.BlockBase;

public class BlockSlabBase extends BlockBase {

	public static final PropertyEnum<EnumBlockHalf> HALF = BlockSlab.HALF;
	public static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);
	public static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0, 0.5, 0, 1, 1, 1);

	public BlockSlabBase(Material material, MapColor mapColor, ItemEntry itemEntry, IOreEntry oreEntry) {
		super(material, mapColor, itemEntry, oreEntry);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if(ForgeModContainer.disableStairSlabCulling) {
			return false;
		}

		EnumBlockHalf side = state.getValue(HALF);
		return (side == EnumBlockHalf.TOP && face == EnumFacing.UP) || (side == EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? iblockstate : iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.TOP);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if(side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side)) {
			return false;
		}
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1;
    }

	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }
}
