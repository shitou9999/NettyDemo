package com.test.nettyclient.client;

import com.test.nettyclient.proto.HelloTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by sun on 2017/7/29.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ConnectionClient client;
    protected ClientHandler(final ConnectionClient client) {
        super();
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] packet = new byte[buf.readableBytes()];
        buf.readBytes(packet);
        HelloTest.Hello hello = HelloTest.Hello.parseFrom(packet);
        System.out.println(hello.getContent().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println("client is connect " +  ctx.channel().remoteAddress().toString());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println("client is connect " +  ctx.channel().remoteAddress().toString());
        client.reconnect(ctx.channel());
        super.channelInactive(ctx);
    }
}
