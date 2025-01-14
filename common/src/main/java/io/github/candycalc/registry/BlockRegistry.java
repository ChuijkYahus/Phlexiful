package io.github.candycalc.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.candycalc.blocks.CobbledBedrock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static io.github.candycalc.Phlexiful.MOD_ID;

//TODO("Learn how to do this in kotlin. Also learn how to do kotlin generics.")
public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> COBBLED_BEDROCK = addToRegistry("cobbled_bedrock",
            // strength is set to -2 because pistons will not move blocks with -1 strength
            // why is there not just a "pushable" block property? This is the most sold game ever btw.
            () -> new CobbledBedrock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).strength(-2.0f, 3600000.0f)));

    private static <T extends Block> RegistrySupplier<T> addToRegistry(String name, Supplier<T> block) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(COBBLED_BEDROCK.get(), ItemRegistry.defaultProperties()));
        return BLOCKS.register(name, block);
    }

    public static void register() {
        BLOCKS.register();
    }
}
