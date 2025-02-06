package io.github.candycalc.registry;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import io.github.candycalc.casting.actions.armor.OpReadPantsMedia;
import io.github.candycalc.casting.actions.armor.OpReadPantsThreshhold;
import io.github.candycalc.casting.actions.armor.OpSetPantsThreshhold;
import io.github.candycalc.patterns.great.OpDestroyBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import static io.github.candycalc.Phlexiful.MOD_ID;

public class PatternRegistry {
    static Map<ResourceLocation, ActionRegistryEntry> HASHMAP = new HashMap<>();

    public static final ActionRegistryEntry
            DESTROY_BLOCK = wrap("destroy_block", HexPattern.fromAngles("qaqqqqqwaeqqqeaeqqq", HexDir.EAST), OpDestroyBlock.INSTANCE),
            //TODO FINISH DISSOCIATE EVAL
            //DISSOCIATE_EVAL = wrap("dissociate_eval", HexPattern.fromAngles("dwddwde", HexDir.EAST), OpDissociateEval.INSTANCE),
            SETPANTSTHRESHHOLD = wrap("set_pants_threshhold", HexPattern.fromAngles("aedwd", HexDir.NORTH_WEST), OpSetPantsThreshhold.INSTANCE),
            GETPANTSTHRESHHOLD = wrap("get_pants_threshhold", HexPattern.fromAngles("dqawa", HexDir.NORTH_EAST), OpReadPantsThreshhold.INSTANCE),
            READPANTSMEDIA = wrap("read_pants_media", HexPattern.fromAngles("aeawaw", HexDir.SOUTH_EAST), OpReadPantsMedia.INSTANCE)
            ;


    static ActionRegistryEntry wrap(String name, HexPattern pattern, Action action) {
        var key = new ResourceLocation(MOD_ID, name);
        var val = new ActionRegistryEntry(pattern, action);
        HASHMAP.put(key, val);
        return val;
    }

    public static void register() {
        var reg = HexActions.REGISTRY;
        for (var pair : HASHMAP.entrySet()) {
            Registry.register(reg, pair.getKey(), pair.getValue());
        }
    }
}
