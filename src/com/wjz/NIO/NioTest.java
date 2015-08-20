package com.wjz.NIO;

import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * Created by wujiazhi on 15/7/7.
 */
public class NioTest {

    /**
     * Selector——》SelectionKey——》SocketChannel——》Socket
     *                        |——》ServerSocketChannel——》ServerSocket
     * @param args
     */
    public static void main(String []args){

    }

    public static void testSelector() throws Exception{

        /**A selector may be created by invoking the {@link #open open} method of
         * this class, which will use the system's default {@link
         * java.nio.channels.spi.SelectorProvider </code>selector provider<code>} to
         * create a new selector.
         *
         * 使用的时候，一般会用一个专门的线程来管理Selector，让它监听注册在Selector之上的多个SocketChannel；
         * 一旦有端口有消息需要进行读写，Selector就会调用SelectionKey连接的SocketChannel来读写数据；
         */
        Selector s = Selector.open();

        /**Selects a set of keys whose corresponding channels are ready for I/O
         * operations.
         * 查找是否有端口可以读写，如果没有则阻塞；
         * A thread blocked in one of the select() or select(long) methods may be interrupted by some other thread in one of three ways:
         1、By invoking the selector's wakeup method,
         2、By invoking the selector's close method, or
         3、By invoking the blocked thread's interrupt method,
            in which case its interrupt status will be set and the selector's wakeup method will be invoked.
         */
        s.select();
        /**
         *和select()相比，如果时间到了也会打破阻塞状态
         */
        s.select(111);
        /**
         * 立马返回，不阻塞
         */
        s.selectNow();

        //返回所有的SelectionKey
        s.keys();
        //有数据进行I/O的SelectionKey
        s.selectedKeys();

        //Returns the provider that created this channel
        s.provider();

        //唤醒被阻塞的Selector线程，当有端口有数据需要读写时就会调用这个方法；具体可以参看子类中的实现部分
        s.wakeup();

        /**
         * 关键分析：thread.interrupt()和selector.wakeup()的关系；
         *
         * interrupt()的实现是：
         * public void interrupt() {
         if (this != Thread.currentThread())
         checkAccess();

         synchronized (blockerLock) {
         Interruptible b = blocker;
         if (b != null) {
         interrupt0();           // Just to set the interrupt flag
         b.interrupt(this);//这个Interruptible b就会调用wakeup()方法；
         return;
         }
         }
         interrupt0();
         }
         *
         * 在AbstractSelector中，设定了一个private Interruptible interruptor = null;
         * begin()方法在doSelect()中被调用：
         *
         * Marks the beginning of an I/O operation that might block indefinitely.
         This method should be invoked in tandem with the end method, using a try ... finally block as shown above,
         in order to implement interruption for this selector.
         Invoking this method arranges for the selector's java.nio.channels.Selector.wakeup() method
         to be invoked if a thread's java.lang.Thread.interrupt() method is invoked
         while the thread is blocked in an I/O operation upon the selector.
         * protected final void More ...begin() {
         210        if (interruptor == null) {
         211            interruptor = new Interruptible() {
         212                    public void More ...interrupt(Thread ignore) {
         213                        AbstractSelector.this.wakeup();
         214                    }};
         215        }
         216        AbstractInterruptibleChannel.blockedOn(interruptor);
         217        Thread me = Thread.currentThread();
         218        if (me.isInterrupted())
         219            interruptor.interrupt(me);
         220    }


         protected int More ...doSelect(long timeout) throws IOException {
         74         if (closed)
         75             throw new ClosedSelectorException();
         76         processDeregisterQueue();
         77         try {
         78             begin();
         79             pollWrapper.poll(timeout);
         80         } finally {
         81             end();
         82         }
         83         processDeregisterQueue();
         84         int numKeysUpdated = updateSelectedKeys();
         85         if (pollWrapper.interrupted()) {
         86             // Clear the wakeup pipe
         87             pollWrapper.putEventOps(pollWrapper.interruptedIndex(), 0);
         88             synchronized (interruptLock) {
         89                 pollWrapper.clearInterrupted();
         90                 IOUtil.drain(fd0);
         91                 interruptTriggered = false;
         92             }
         93         }
         94         return numKeysUpdated;
         95     }
         */
        Thread t = new Thread();
        t.interrupt();
    }


    public static void testSelectionKey() throws Exception{

        Selector s = Selector.open();
        SocketChannel sc = SocketChannel.open();

        SelectionKey sk = sc.register(s, SelectionKey.OP_READ);

        sk.isReadable();
        sk.isAcceptable();
        sk.channel();
        sk.selector();
        sk.attachment();
        sk.attach(new Object());
        sk.interestOps();
    }

//    public static void testSocketChannel() throws Exception{
//        SocketChannel sc = SocketChannel.open();
//
//        sc.write(new ByteBuffer[1]);
//        sc.read(new ByteBuffer[1]);
//
//        sc.socket();
//        sc.provider();
//
//        sc.register();
//        sc.bind();
//        sc.connect()
//        sc.configureBlocking();
//        sc.getLocalAddress();
//        sc.getRemoteAddress();
//    }

    public static void testServerSocketChannel() throws Exception{
        ServerSocketChannel ssc = ServerSocketChannel.open();

        ssc.socket();
        //只能得到本地Address，因为ServerSocketChannel只负责监听，而不需要去连接，所以没有connect()
        ssc.getLocalAddress();
        ssc.accept();
    }
}
