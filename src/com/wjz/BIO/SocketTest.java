package com.wjz.BIO;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by wujiazhi on 15/7/7.
 */
public class SocketTest {

    public static void main(String[] args){

    }

    public static void testServerSocket() throws Exception{
        /**
         * Various states of this socket.

        private boolean created = false;
        private boolean bound = false;
        private boolean closed = false;
        private Object closeLock = new Object();
         */
        //ServerSocket implements java.io.Closeable
        ServerSocket ss = new ServerSocket();

        //ServerSocket只用来A server socket waits for requests to come in over the network.
        //所以设计时，它只有local port，即服务器的监听端口，如Tomcat常用的8080
        ss.getLocalPort();

        //将代码层面的ServerSocket绑定到指定的端口上IP:PORT；
        //并设置连接等待的队列容量；
        ss.bind(new SocketAddress() {
        });

        //A socket will have a channel if, and only if,
        // the channel itself was created via the java.nio.channels.SocketChannel.open()
        // or java.nio.channels.ServerSocketChannel.accept() methods.
        ss.getChannel();

        //有accept()方法，而没有connect()方法；
        // 因为ServerSocket都是等待客户端Socket连接，而不会去主动连接其它的Socket
        ss.accept();

    }

    public static void tetsSocket() throws Exception{
        /**
         * Various states of this socket.

        private boolean created = false;
        private boolean bound = false;
        private boolean connected = false;
        private boolean closed = false;
        private Object closeLock = new Object();
        private boolean shutIn = false;
        private boolean shutOut = false;
         */
        //Socket implements java.io.Closeable
        Socket s = new Socket();

        //A socket is an endpoint for communication between two machines.
        //Socket才是真正进行数据通信的入口，所以一个连接需要用到两个端口（在NIO的Selector中使用Socket自己和自己相连的方式实现interrupt）
        //remote port
        s.getPort();
        //local port
        s.getLocalPort();
        //将代码层面的ServerSocket绑定到指定的端口上IP:PORT；
        s.bind(new SocketAddress() {
        });

        //A socket will have a channel if, and only if,
        // the channel itself was created via the java.nio.channels.SocketChannel.open()
        // or java.nio.channels.ServerSocketChannel.accept() methods.
        s.getChannel();


        //连接服务器的ServerSocket（在Linux底层实现中，都是socket）
        s.connect(new SocketAddress() {});

        //输入字节流
        s.getInputStream();
        //输出字节流
        s.getOutputStream();

    }
}
