package com.wjz.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by wujiazhi on 15/5/28.
 */
public class NioServer {

    Selector selector = null;

    public void initServer(int port) throws IOException{
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //将通道对应的socket绑定到port端口
        ssc.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();

        ssc.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Success init server!");
    }

    /**
     * ServerSocketChannel主要用在Server中，用于接收客户端的链接请求;
     * SocketChannel则用于真正的读写数据，同时还可以用于客户端发送链接请求;
     * @throws IOException
     */
    public void listen() throws IOException{
        System.out.println("Start Listen......");

        while(true){
            //当注册的事件到达时，方法返回；否则,该方法会一直阻塞
            selector.select();

            Iterator it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey sk = (SelectionKey)it.next();
                it.remove();

                if(sk.isAcceptable()){
                    ServerSocketChannel ssc = (ServerSocketChannel)sk.channel();
                    //获得和客户端连接的通道
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    sc.write(ByteBuffer.wrap(new String("Send sth to client!").getBytes()));

                    //在通道上注册可读的事件监听
                    sc.register(selector, SelectionKey.OP_READ);
                }
                else if(sk.isReadable()){
                    read(sk);
                }
            }

        }
    }

    public void read(SelectionKey sk) throws IOException{
        SocketChannel sc = (SocketChannel)sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(20);
        sc.read(bb);

        byte[] arr = bb.array();
        System.out.println("Read: " + new String(arr));

        ByteBuffer outB = ByteBuffer.wrap(new String("Have read!").getBytes());
        sc.write(outB);
    }

    public static void main(String []args) throws Exception{
        NioServer server = new NioServer();
        server.initServer(8000);
        server.listen();
    }
}
