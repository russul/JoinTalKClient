package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author: create by kevinYang
 * @version: v1.0
 * @description: ui
 * @date:2019/2/20 0020
 */
public class Client {
    public static Socket socket;


    public static String receiverAddress;

    public static String receiverName;

    public static String name;

    public static Client_Single_Frame sf;
    public static Client_Talk_Frame tf;
    public static Client_Enter_Frame ef;
    public static void main(String[] args) throws IOException, InterruptedException {


        ef = new Client_Enter_Frame();

//        Client_Talk_Frame tf = null;

//        Client_Single_Frame sf;
//
//        sf.setVisible(true);

//        //窗口关闭键无效，必须通过退出键退出客户端以便善后
//        ef.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //获取本机屏幕横向分辨率
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        //获取本机屏幕纵向分辨率
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        //将窗口置中
        ef.setLocation((w - ef.WIDTH) / 2, (h - ef.HEIGHT) / 2);
        //设置客户端窗口为可见
        ef.setVisible(true);

        byte[] buff = new byte[1024];
        int len = 0;


        while (true) {


//
            //给主线程休眠一段时间，以便给进入页面的按钮触发连接的时间
            Thread.sleep(50);
//            System.out.println(socket);
            //接收连接成功的信息
            if (socket != null) {


                InputStream in = socket.getInputStream();
                len = in.read(buff);
                String msgInfo = new String(buff, 0, len);

                System.out.println("in:" + msgInfo);


                String type = msgInfo.substring(0, msgInfo.indexOf("/"));
                System.out.println("in:" + type);
//              客户端接收到连接成功的信息
                if (msgInfo.equals("连接成功/")) {
                    name = ef.jtaUser.getText();
                    tf = new Client_Talk_Frame();
                    tf.setTitle(ef.jtaUser.getText());
                    //将窗口置中
                    tf.setLocation((w - tf.WIDTH) / 2, (h - tf.HEIGHT) / 2);

                    //设置客户端窗口为可见
                    tf.setVisible(true);

                    OutputStream out = socket.getOutputStream();
                    out.write(ef.jtaUser.getText().getBytes());
                    out.write(("Enter/" + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort() + "/" + ef.jtaUser.getText()).getBytes());
//                  客户端接收到服务器的更新列表信息

                } else if (type.equals("OnlineListUpdate")) {


//                    String[] users = msgInfo.split(",");


                    if (tf != null) {
                        // 接收服务器广播的在线列表
//                        tf.jtaOnline.setText("在线列表" + "\n");

                        //提取在线列表的数据模型
                        DefaultTableModel tbm = (DefaultTableModel) tf.jtbOnline.getModel();
                        //清除在线名单列表
                        tbm.setRowCount(0);
                        //更新在线名单
                        String[] users = msgInfo.split(",");

                        for (int i = 0; i < users.length; i++) {
                            String username = users[i].substring(users[i].lastIndexOf("/") + 1);
                            String[] usernames = new String[]{username};

                            tbm.addRow(usernames);
//                            System.out.println(username);
//                            tf.jtaOnline.append(username + "\n");
                        }


                        //提取在线列表的渲染模型
                        DefaultTableCellRenderer tbr = new DefaultTableCellRenderer();
                        //表格数据居中显示
                        tbr.setHorizontalAlignment(JLabel.CENTER);
                        tf.jtbOnline.setDefaultRenderer(Object.class, tbr);
                    }
//                  客户端接收的是群聊的信息
                } else if (type.equals("WeChat")) {
                    if (tf != null) {
//                        System.out.println(tf);
                        String word = msgInfo.substring(msgInfo.lastIndexOf("/") + 1);
                        System.out.println("word:" + word);
                        tf.jtaChat.append(word + "\n");
                    }

//                    客户端会接收到一个对方（待通信方）的IP地址
                } else if (type.equals("getIP")) {
                    receiverAddress = msgInfo.substring(msgInfo.indexOf("/") + 1);

//                    System.out.println("receiverAddress:" + receiverAddress);

 //                    客户端接收到服务上单点通信的信息，显示在自己的界面上
                } else if (type.equals("SingleChat")) {
//                    System.out.println(tf);


                    if (tf != null ) {
                        String word = msgInfo.substring(msgInfo.lastIndexOf("/") + 1);
                        System.out.println("singword:" + word);
                        sf.jtaChat.append(word + "\n");
                    }


                }
            }
        }


    }
}
