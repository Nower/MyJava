package com.wjz.RMI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Stub其实就是远程对象的代理；
 * 它负责将参数marshals给远程的JVM，等待远程调用的结果，然后unmarshals回调结果，并返回的本地调用者；
 *
 * Created by wujiazhi on 15/7/6.
 */
public class PersonStub implements Person{
    private Socket socket;

    public PersonStub() throws Throwable {
        // connect to skeleton
        socket = new Socket("computer_name", 9000);
    }

    public int getAge() throws Throwable {
        // pass method name to skeleton
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("age");
        outStream.flush();
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

        return inStream.readInt();
    }

    public String getName() throws Throwable {
        // pass method name to skeleton
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("name");
        outStream.flush();
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

        return (String) inStream.readObject();
    }
}
