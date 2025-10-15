package org.example.omok.netty.server.handlers

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class EchoServerHandler: ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        ctx.writeAndFlush(msg)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }
}