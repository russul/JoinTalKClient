package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: create by kevinYang
 * @version: v1.0
 * @description: ui
 * @date:2019/2/21 0021
 */
public class Client_Single_Frame extends JFrame {
    //时间显示格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //窗口宽度
    final int WIDTH = 400;
    //窗口高度
    final int HEIGHT = 500;

    //创建发送按钮
    JButton btnSend = new JButton("send");


//
//    //创建消息接收者标签
//    JLabel lblReceiver = new JLabel("对谁说？");

    //创建文本输入框, 参数分别为行数和列数
    JTextArea jtaSay = new JTextArea();

    //创建聊天消息框
    JTextArea jtaChat = new JTextArea("消息框\n");


    //创建聊天消息框的滚动窗
    JScrollPane jspChat = new JScrollPane(jtaChat);


    //设置默认窗口属性，连接窗口组件
    public Client_Single_Frame() {
        //标题
        setTitle("聊天室");
        //大小
        setSize(WIDTH, HEIGHT);
        //不可缩放
        setResizable(false);
        //设置布局:不适用默认布局，完全自定义
        setLayout(null);

        //设置按钮大小和位置
        btnSend.setBounds(300, 370, 60, 60);


        //设置按钮文本的字体
        btnSend.setFont(new Font("宋体", Font.BOLD, 10));


        //添加按钮
        this.add(btnSend);


        //设置文本输入框大小和位置
        jtaSay.setBounds(50, 370, 200, 60);
        //设置文本输入框字体
        jtaSay.setFont(new Font("楷体", Font.BOLD, 12));
        //添加文本输入框
        this.add(jtaSay);

        //聊天消息框自动换行
        jtaChat.setLineWrap(true);
        //聊天框不可编辑，只用来显示
        jtaChat.setEditable(false);
        //设置聊天框字体
        jtaChat.setFont(new Font("楷体", Font.BOLD, 12));


        //设置滚动窗的水平滚动条属性:不出现
        jspChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //设置滚动窗的垂直滚动条属性:需要时自动出现
        jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //设置滚动窗大小和位置
        jspChat.setBounds(50, 20, 300, 300);
        //添加聊天窗口的滚动窗
        this.add(jspChat);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    OutputStream out = Client.socket.getOutputStream();
                    String receiver = Client.receiverAddress + "/" + Client.receiverName;
                    System.out.println(receiver);
//                  //自己将自己发送的内容显示在自己的聊天消息框中
                    Client.sf.jtaChat.append(jtaSay.getText()+"\n");
                    out.write(("SingleChat/" + receiver + "/" + sdf.format(new Date()) + "/" + Client_Single_Frame.this.jtaSay.getText()).getBytes());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }finally {
                    jtaSay.setText("");
                }
            }
        });

    }
}
