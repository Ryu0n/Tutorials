package org.example.omok.netty.server.handlers

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.example.omok.netty.server.managers.PlayerManager
import org.example.omok.netty.server.managers.RoomManager
import org.example.omok.netty.server.packets.*
import org.example.omok.netty.server.packets.data.*
import org.example.omok.netty.server.players.Player
import reactor.core.publisher.Sinks

class OmokServerHandler(
    private val roomManager: RoomManager,
    private val playerManager: PlayerManager,
    private val sink: Sinks.Many<PacketMessage>,
) : SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        val player = playerManager.addPlayer(ctx.channel())
        roomManager.addPlayerToWaitingRoom(player)
        println("Channel active for ${player.id}")
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        val player = playerManager.getPlayer(ctx.channel())
        if (player != null) {
            roomManager.removePlayerFromApplication(player)
            playerManager.removePlayer(player)
            println("Channel inactive for ${player.id}")
        }
    }

    public override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        val player = playerManager.getPlayer(ctx.channel())
        if (player == null) {
            println("Player not found for channel: ${ctx.channel()}")
            return
        }

        try {
            val packet = deserializePacket(msg)
            sink.tryEmitNext(
                PacketMessage(
                    playerId = player.id,
                    packet = packet,
                )
            )
        } catch (e: Exception) {
            println("Error processing message from ${player.id}: '$msg'. Error: ${e.message}")
            // Optionally, send an error message back to the player
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        val player = playerManager.getPlayer(ctx.channel())
        println("Exception caught for player ${player?.id}: ${cause.message}")
        cause.printStackTrace()
        ctx.close()
    }

    private fun deserializePacket(data: String): Packet {
        val content = data.removePrefix("<").removeSuffix(">")
        val parts = content.split(":")
        val packetTypeString = parts[0]
        val payload = parts.drop(1)

        return when (packetTypeString) {
            PacketType.MESSAGE.name -> MessagePacket(MessagePacketData(payload))
            PacketType.ATTENDANCE.name -> AttendancePacket(AttendancePacketData(payload))
            PacketType.EXIT.name -> ExitPacket(ExitPacketData(payload))
            PacketType.COORDINATE.name -> CoordinatePacket(CoordinatePacketData(payload))
            PacketType.MATCH_RESULT.name -> MatchResultPacket(MatchResultPacketData(payload))
            else -> throw IllegalArgumentException("Unknown packet type: $packetTypeString")
        }
    }
}
