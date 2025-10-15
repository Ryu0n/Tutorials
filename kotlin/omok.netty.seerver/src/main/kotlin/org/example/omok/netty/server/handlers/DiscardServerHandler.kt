package org.example.omok.netty.server.handlers

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class DiscardServerHandler: ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
//        (msg as ByteBuf).release()

        val inbound = msg as ByteBuf
        try {
            while (inbound.isReadable) {
                val b = inbound.readByte()
                println(b.toChar())
            }
        } finally {
            inbound.release()
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }
}