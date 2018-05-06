package thelm.jaopca.additions.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.block.IBlockWithProperty;
import thelm.jaopca.api.utils.JAOPCAStateMap;

public class BlockFenceBase extends BlockFence implements IBlockWithProperty {

	public final IOreEntry oreEntry;
	public final ItemEntry itemEntry;

	public BlockFenceBase(Material material, MapColor mapColor, ItemEntry itemEntry, IOreEntry oreEntry) {
		super(material, mapColor);
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelLoader.setCustomStateMapper(this, new JAOPCAStateMap.Builder(new ResourceLocation(this.getItemEntry().itemModelLocation.toString().split("#")[0])).build());
	}
	
	@Override
	public BlockFenceBase setSoundType(SoundType sound) {
		super.setSoundType(sound);
		return this;
	}

	@Override
	public BlockFenceBase setLightOpacity(int opacity) {
		super.setLightOpacity(opacity);
		return this;
	}

	@Override
	public BlockFenceBase setLightLevel(float value) {
		super.setLightLevel(value);
		return this;
	}

	@Override
	public BlockFenceBase setResistance(float resistance) {
		super.setResistance(resistance);
		return this;
	}

	@Override
	public BlockFenceBase setHardness(float hardness) {
		super.setHardness(hardness);
		return this;
	}
}
