package thelm.jaopca.additions.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cofh.api.block.IDismantleable;
import cofh.core.util.CoreUtils;
import cofh.core.util.RayTracer;
import cofh.core.util.helpers.ServerHelper;
import cofh.core.util.helpers.WrenchHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.block.BlockBase;
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

	public static class BlockHardenedGlassBase extends BlockBase implements IDismantleable {

		public BlockHardenedGlassBase(Material material, MapColor mapColor, ItemEntry itemEntry, IOreEntry oreEntry) {
			super(material, mapColor, itemEntry, oreEntry);
		}

		@Override
		public int quantityDropped(Random rand) {
			return 0;
		}

		@Override
		public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {
			return false;
		}

		@Override
		public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
			return true;
		}

		@Override
		public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
			return true;
		}

		@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
			if(player.isSneaking()) {
				RayTraceResult traceResult = RayTracer.retrace(player);
				if(WrenchHelper.isHoldingUsableWrench(player, traceResult)) {
					if(ServerHelper.isServerWorld(world)) {
						dismantleBlock(world, pos, state, player, false);
						WrenchHelper.usedWrench(player, traceResult);
					}
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
			IBlockState offset = blockAccess.getBlockState(pos.offset(side));
			return !(offset.getBlock() instanceof BlockHardenedGlassBase) && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}

		@Override
		public float[] getBeaconColorMultiplier(IBlockState state, World world, BlockPos pos, BlockPos beaconPos) {
			return new float[] {(oreEntry.getColor()>>16&0xFF)/255F, (oreEntry.getColor()>>8&0xFF)/255F, (oreEntry.getColor()&0xFF)/255F};
		}

		@Override
		public ArrayList<ItemStack> dismantleBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, boolean returnDrops) {
			ItemStack dropBlock = new ItemStack(this, 1, 0);
			world.setBlockToAir(pos);
			if (!returnDrops) {
				float f = 0.3F;
				double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
				EntityItem dropEntity = new EntityItem(world, pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2, dropBlock);
				dropEntity.setPickupDelay(10);
				world.spawnEntity(dropEntity);
				CoreUtils.dismantleLog(player.getName(), this, 0, pos);
			}
			ArrayList<ItemStack> ret = Lists.<ItemStack>newArrayList(dropBlock);
			return ret;
		}

		@Override
		public boolean canDismantle(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
			return true;
		}
	}
}
