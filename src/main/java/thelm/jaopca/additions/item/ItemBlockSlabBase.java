package thelm.jaopca.additions.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.additions.block.BlockSlabBase;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.block.IBlockWithProperty;
import thelm.jaopca.api.item.ItemBlockBase;

public class ItemBlockSlabBase extends ItemBlockBase {

	public ItemBlockSlabBase(IBlockWithProperty block) {
		super(block);
	}

	public Block getSingleSlab() {
		return JAOPCAApi.BLOCKS_TABLE.get("slab", this.getOreEntry().getOreName());
	}

	public Block getDoubleSlab() {
		return JAOPCAApi.BLOCKS_TABLE.get("doubleSlab", this.getOreEntry().getOreName());
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(stack.stackSize != 0 && player.canPlayerEdit(pos.offset(facing), facing, stack)) {
			IBlockState state = world.getBlockState(pos);

			if(state.getBlock() == this.getSingleSlab()) {
				EnumBlockHalf half = state.getValue(BlockSlabBase.HALF);

				if((facing == EnumFacing.UP && half == EnumBlockHalf.BOTTOM || facing == EnumFacing.DOWN && half == EnumBlockHalf.TOP)) {
					IBlockState state1 = this.getDoubleSlab().getDefaultState();
					AxisAlignedBB axisalignedbb = state1.getCollisionBoundingBox(world, pos);

					if(axisalignedbb != Block.NULL_AABB && world.checkNoEntityCollision(axisalignedbb.offset(pos)) && world.setBlockState(pos, state1, 11)) {
						SoundType soundtype = this.getDoubleSlab().getSoundType(state1, world, pos,player);
						world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						--stack.stackSize;
					}

					return EnumActionResult.SUCCESS;
				}
			}

			return this.tryPlace(player, stack, world, pos.offset(facing)) ? EnumActionResult.SUCCESS : super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
		else {
			return EnumActionResult.FAIL;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		BlockPos blockpos = pos;
		IBlockState state = world.getBlockState(pos);

		if(state.getBlock() == this.getSingleSlab()) {
			boolean flag = state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;

			if((side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag)) {
				return true;
			}
		}

		pos = pos.offset(side);
		IBlockState state1 = world.getBlockState(pos);
		return state1.getBlock() == this.getSingleSlab() ? true : super.canPlaceBlockOnSide(world, blockpos, side, player, stack);
	}

	private boolean tryPlace(EntityPlayer player, ItemStack stack, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);

		if(state.getBlock() == this.getSingleSlab()) {
			IBlockState state1 = this.getSingleSlab().getDefaultState();
			AxisAlignedBB axisalignedbb = state1.getCollisionBoundingBox(world, pos);

			if(axisalignedbb != Block.NULL_AABB && world.checkNoEntityCollision(axisalignedbb.offset(pos)) && world.setBlockState(pos, state1, 11)) {
				SoundType soundtype = this.getDoubleSlab().getSoundType(state1, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return true;
		}

		return false;
	}
}
