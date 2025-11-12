package io.github.candycalc

import io.github.candycalc.interop.PhlexInterop
import io.github.candycalc.interop.trinkets.TrinketsApiInterop
import io.github.candycalc.registry.PacketRegistry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory

object PhlexifulClient : ClientModInitializer {
	val cowLogger = LoggerFactory.getLogger(":3")


	override fun onInitializeClient() {
		PacketRegistry.registerS2CPackets()

		if (FabricLoader.getInstance().isModLoaded(PhlexInterop.TRINKETS_API_ID)) {
			TrinketsApiInterop.clientInit()
		}

		//	this is critical for the function of the mod. Do not remove.
		cowLogger.info(
				"\n" +
				" ____________\n" +
				"< omg haiii! >\n" +
				" ------------\n" +
				"        \\   ^__^\n" +
				"         \\  (oo)\\_______\n" +
				"            (__)\\       )\\/\\\n" +
				"                ||----w |\n" +
				"                ||     ||")
	}
}