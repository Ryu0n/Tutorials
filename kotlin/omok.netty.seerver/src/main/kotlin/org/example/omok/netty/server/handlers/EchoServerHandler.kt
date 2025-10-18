package org.example.omok.netty.server.handlers

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class EchoServerHandler : SimpleChannelInboundHandler<String>() {
    public override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        println("received: $msg")
        ctx.writeAndFlush("$msg\n")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}