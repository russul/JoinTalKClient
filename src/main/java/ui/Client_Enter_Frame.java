package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

/**
 * @author: create by kevinYang
 * @version: v1.0
 * @description: ui
 * @date:2019/2/20 进入聊天室界面
 */
public class Client_Enter_Frame extends JFrame {


    //窗口宽度
    final int WIDTH = 300;
    //窗口高度
    final int HEIGHT = 360;

    JLabel labUser = new JLabel("用户名:");
    JLabel labIP = new JLabel("服务器IP:");
    JLabel labPort = new JLabel("端口号:");

    //创建文本输入框, 输入用户名
    JTextArea jtaUser = new JTextArea();

    //创建文本域，输入服务器地址
    JTextArea jtaIP = new JTextArea();
    //创建文本输入框, 输入端口号
    JTextArea jtaPort = new JTextArea();


    //创建进入按钮
    JButton btnEnter = new JButton("进入聊天室");


    public Client_Enter_Frame() throws HeadlessException {


        //标题
        setTitle("聊天室");
        //大小
        setSize(WIDTH, HEIGHT);
        //不可缩放
        setResizable(false);
        //设置布局:不适用默认布局，完全自定义
        setLayout(null);


        //
        labUser.setBounds(60, 30, 60, 30);
        labIP.setBounds(60, 100, 60, 30);
        labPort.setBounds(60, 170, 60, 30);


        jtaUser.setBounds(150, 30, 120, 30);
        jtaIP.setBounds(150, 100, 120, 30);
        jtaPort.setBounds(150, 170, 120, 30);

        btnEnter.setBounds(90, 250, 120, 60);

        this.add(labUser);
        this.add(labIP);
        this.add(labPort);
        this.add(jtaIP);
        this.add(jtaPort);
        this.add(jtaUser);
        this.add(btnEnter);

        btnEnter.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                try {

                    String s1 = jtaUser.getText();
                    String s2 = jtaIP.getText();
                    String s3 = jtaPort.getText();


                    if (s1 != "" && s2 != "" && s3 != "") {


//                        连接服务器
                        Client.socket = new Socket(s2, new Integer(s3));



                    }


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    //文本输入框清除
//                    jtaUser.setText("");
//                    jtaIP.setText("");
//                    jtaPort.setText("");
                }
            }
        });


    }
}
