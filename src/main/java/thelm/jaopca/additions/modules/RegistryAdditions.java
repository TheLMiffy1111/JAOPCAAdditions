package thelm.jaopca.additions.modules;

import net.minecraftforge.fml.common.Loader;
import thelm.jaopca.api.JAOPCAApi;

public class RegistryAdditions {
	
	public static void preInit() {
		JAOPCAApi.registerModule(new ModuleFence());
		JAOPCAApi.registerModule(new ModuleWall());
		JAOPCAApi.registerModule(new ModuleStairs());
		JAOPCAApi.registerModule(new ModuleSlab());
		if(Loader.isModLoaded("thermalfoundation")) {
			JAOPCAApi.registerModule(new ModuleThermalFoundation());
		}
	}
}
