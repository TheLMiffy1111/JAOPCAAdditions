package thelm.jaopca.additions.modules;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.block.BlockBase;
import thelm.jaopca.api.block.BlockProperties;
import thelm.jaopca.api.utils.JAOPCAStateMap;
import thelm.jaopca.api.utils.Utils;

public class ModuleFence extends ModuleBase {

	public static final BlockProperties FENCE_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.IRON).
			setHardnessFunc(entry->5F).
			setResistanceFunc(entry->10F).
			setLightOpacityFunc(entry->0).
			setSoundType(SoundType.METAL).
			setOpaque(false).
			setFull(false).
			setBlockClass(BlockFenceBase.class);
	
	public static final ItemEntry FENCE_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "fence", new ModelResourceLocation("jaopca:fence#inventory")).
			setProperties(FENCE_PROPERTIES).
			setOreTypes(EnumOreType.DUSTLESS);

	@Override
	public String getName() {
		return "fence";
	}

	@Override
	public List<String> getDependencies() {
		return Lists.<String>newArrayList("stick");
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(FENCE_ENTRY);
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("fence")) {
			String s = "ingot";
			switch(entry.getOreType()) {
			case GEM:
			case GEM_ORELESS:
				s = "gem";
				break;
			default:
				break;
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(Utils.getOreStack("fence", entry, 3), new Object[] {
					"B#B",
					"B#B",
					'B', s+entry.getOreName(),
					'#', "stick"+entry.getOreName(),
			}));
		}
	}

	public static class BlockFenceBase extends BlockBase {

		public static final PropertyBool NORTH = PropertyBool.create("north");
		public static final PropertyBool EAST = PropertyBool.create("east");
		public static final PropertyBool SOUTH = PropertyBool.create("south");
		public static final PropertyBool WEST = PropertyBool.create("west");
		protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
		public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D);
		public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.625D, 0.625D, 1.5D, 1.0D);
		public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1.5D, 0.625D);
		public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 0.375D);
		public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);

		public BlockFenceBase(Material material, MapColor mapColor, ItemEntry itemEntry, IOreEntry oreEntry) {
			super(material, mapColor, itemEntry, oreEntry);
			this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
		}

		@Override
		public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
			state = state.getActualState(worldIn, pos);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, PILLAR_AABB);

			if(state.getValue(NORTH)) {
				addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
			}

			if(state.getValue(EAST)) {
				addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
			}

			if(state.getValue(SOUTH)) {
				addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
			}

			if(state.getValue(WEST)) {
				addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
			}
		}

		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
			state = this.getActualState(state, source, pos);
			return BOUNDING_BOXES[getBoundingBoxIdx(state)];
		}

		private static int getBoundingBoxIdx(IBlockState state)  {
			int i = 0;

			if(state.getValue(NORTH)) {
				i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
			}

			if(state.getValue(EAST)) {
				i |= 1 << EnumFacing.EAST.getHorizontalIndex();
			}

			if(state.getValue(SOUTH)) {
				i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
			}

			if(state.getValue(WEST)) {
				i |= 1 << EnumFacing.WEST.getHorizontalIndex();
			}

			return i;
		}

		public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos){
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();
			return block == Blocks.BARRIER ? false : ((!(block instanceof BlockFenceBase) || iblockstate.getMaterial() != this.blockMaterial) && !(block instanceof BlockFenceGate) ? (iblockstate.getMaterial().isOpaque() && iblockstate.isFullCube() ? iblockstate.getMaterial() != Material.GOURD : false) : true);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
			return true;
		}

		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
			return worldIn.isRemote ? true : ItemLead.attachToFence(playerIn, worldIn, pos);
		}

		@Override
		public int getMetaFromState(IBlockState state) {
			return 0;
		}

		@Override
		public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
			return state.withProperty(NORTH, this.canConnectTo(worldIn, pos.north())).withProperty(EAST, this.canConnectTo(worldIn, pos.east())).withProperty(SOUTH, this.canConnectTo(worldIn, pos.south())).withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
		}

		@Override
		public IBlockState withRotation(IBlockState state, Rotation rot) {
			switch(rot) {
			case CLOCKWISE_180:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
			case COUNTERCLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
			case CLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
			default:
				return state;
			}
		}

		@Override
		public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
			switch(mirrorIn) {
			case LEFT_RIGHT:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
			case FRONT_BACK:
				return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
			default:
				return super.withMirror(state, mirrorIn);
			}
		}

		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels() {
			ModelLoader.setCustomStateMapper(this, new JAOPCAStateMap.Builder(new ResourceLocation(this.getItemEntry().itemModelLocation.toString().split("#")[0])).build());
		}

		@Override
		public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
			return true;
		}
	}
}
