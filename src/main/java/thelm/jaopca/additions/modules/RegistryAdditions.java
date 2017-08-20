package thelm.jaopca.additions.modules;

import thelm.jaopca.api.JAOPCAApi;

public class RegistryAdditions {
	
	public static void preInit() {
		JAOPCAApi.registerModule(new ModuleCoin());
		JAOPCAApi.registerModule(new ModuleGear());
		JAOPCAApi.registerModule(new ModulePlate());
		JAOPCAApi.registerModule(new ModuleStick());
	}
}
