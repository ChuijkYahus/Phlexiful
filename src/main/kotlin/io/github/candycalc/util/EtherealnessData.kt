package io.github.candycalc.util

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity


class EtherealnessData {
    companion object {
        fun getEthereal(player: EntityDataSaver): Boolean {
            val nbt: NbtCompound = player.persistentData
            return nbt.getBoolean("ethereal")
        }

        fun setEthereal(player: EntityDataSaver, b: Boolean) {
            val nbt: NbtCompound = player.persistentData
            nbt.putBoolean("ethereal", b)
            syncEthereal(b, player as ServerPlayerEntity)
        }

        fun syncEthereal(b: Boolean, player: ServerPlayerEntity) {
            val buffer: PacketByteBuf = PacketByteBufs.create()
            buffer.writeBoolean(b)
            ServerPlayNetworking.send(player, PhlexUtil.ETHEREAL_SYNC_ID, buffer)
        }
    }
}