package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: create by kevinYang
 * @version: v1.0
 * @description: ui
 * @date:2019/2/20 0020
 */
public class Client_Talk_Frame extends JFrame {

    //时间显示格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //窗口宽度
    final int WIDTH = 700;
    //窗口高度
    final int HEIGHT = 700;
//
//    public Client_Single_Frame sf;

    //创建发送按钮
    JButton btnSend = new JButton("发送");
    //创建清除按钮
    JButton btnClear = new JButton("清屏");
    //创建退出按钮
    JButton btnExit = new JButton("退出");

    JButton btnSingle = new JButton("单人聊天");
//
//    //创建消息接收者标签
//    JLabel lblReceiver = new JLabel("对谁说？");

    //创建文本输入框, 参数分别为行数和列数
    JTextArea jtaSay = new JTextArea();

    //创建聊天消息框
    JTextArea jtaChat = new JTextArea("消息框\n");

    JTextArea jtaOnline = new JTextArea("在线用户\n");

    //当前在线列表的列标题
    String[] colTitles = {"在线用户"};
    //当前在线列表的数据
    String[][] rowData = null;
    //创建当前在线列表
    public JTable jtbOnline = new JTable
            (
                    new DefaultTableModel(rowData, colTitles) {
                        //表格不可编辑，只可显示
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    }
            );

    //创建聊天消息框的滚动窗
    JScrollPane jspChat = new JScrollPane(jtaChat);

    //创建当前在线列表的滚动窗
    JScrollPane jspOnline = new JScrollPane(jtbOnline);

    //设置默认窗口属性，连接窗口组件
    public Client_Talk_Frame() {
        //标题
        setTitle("聊天室");
        //大小
        setSize(WIDTH, HEIGHT);
        //不可缩放
        setResizable(false);
        //设置布局:不适用默认布局，完全自定义
        setLayout(null);

        //设置按钮大小和位置
        btnSend.setBounds(20, 600, 100, 60);
        btnClear.setBounds(140, 600, 100, 60);
        btnExit.setBounds(260, 600, 100, 60);
        btnSingle.setBounds(380, 600, 200, 60);

        //设置按钮文本的字体
        btnSend.setFont(new Font("宋体", Font.BOLD, 18));
        btnClear.setFont(new Font("宋体", Font.BOLD, 18));
        btnExit.setFont(new Font("宋体", Font.BOLD, 18));
        btnSingle.setFont(new Font("宋体", Font.BOLD, 18));

        //添加按钮
        this.add(btnSend);
        this.add(btnClear);
        this.add(btnExit);
        this.add(btnSingle);


        //设置文本输入框大小和位置
        jtaSay.setBounds(20, 460, 360, 120);
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
        jspChat.setBounds(20, 20, 360, 400);
        //添加聊天窗口的滚动窗
        this.add(jspChat);

        jtaOnline.setFont(new Font("楷体", Font.BOLD, 12));
        //设置滚动窗的水平滚动条属性:不出现
        jspOnline.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //设置滚动窗的垂直滚动条属性:需要时自动出现
        jspOnline.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //设置当前在线列表滚动窗大小和位置
        jspOnline.setBounds(420, 20, 250, 400);
        //添加当前在线列表
        this.add(jspOnline);


        //添加发送按钮的响应事件
        btnSend.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                //显示最新消息
//                                jtaChat.setCaretPosition(jtaChat.getDocument().getLength());
                                try {
                                    OutputStream out = Client.socket.getOutputStream();
//                                    System.out.println(Client.socket);
//                                    System.out.println(out);
                                    String msg = "WeChat/" + sdf.format(new Date()) + "/" + jtaSay.getText();
                                    System.out.println("out:" + msg);
                                    out.write(msg.getBytes());
                                } catch (Exception e) {
                                } finally {
                                    //文本输入框清除
                                    jtaSay.setText("");
                                }
                            }
                        }
                );
        //添加清屏按钮的响应事件
        btnClear.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                //聊天框清屏
                                jtaChat.setText("");
                            }
                        }
                );
        //添加退出按钮的响应事件
        btnExit.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    //向服务器发送退出信息
                                    OutputStream out = Client.socket.getOutputStream();
                                    out.write("Exit/".getBytes());
                                    //退出
                                    System.exit(0);
                                } catch (Exception e) {
                                }
                            }
                        }
                );

        btnSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Client.receiverAddress != null) {

//                        OutputStream out = Client.socket.getOutputStream();
//                        String receiver = Client.receiverAddress + "/" + Client.receiverName;
//                        System.out.println(receiver);
//                        out.write(("SingleChat/" + receiver).getBytes());

                    Client.sf  = new Client_Single_Frame();
                    Client.sf .setTitle(Client.name + " TO " + Client.receiverName);
                    System.out.println(Client.sf);
                    Client.sf.setVisible(true);

                }
            }
        });

        //添加在线列表项被鼠标选中的相应事件
        jtbOnline.addMouseListener
                (
                        new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent event) {
                                //取得在线列表的数据模型
                                DefaultTableModel tbm = (DefaultTableModel) jtbOnline.getModel();
                                //提取鼠标选中的行作为消息目标，最少一个人，最多全体在线者接收消息
                                int selectedIndex = jtbOnline.getSelectedRow();
                                String name = (String) tbm.getValueAt(selectedIndex, 0);
                                Client.receiverName = name;
                                try {
                                    OutputStream outputStream = Client.socket.getOutputStream();
                                    outputStream.write(("getIP/" + name).getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                                int[] selectedIndex = jtbOnline.getSelectedRows();
                                //将所有消息目标的uid拼接成一个字符串, 以逗号分隔
//                                Client.uidReceiver = new StringBuilder("");
//                                for(int i = 0; i < selectedIndex.length; i++)
//                                {
//                                    Client.uidReceiver.append((String) tbm.getValueAt(selectedIndex[i], 1));
//                                    Client.uidReceiver.append(":");
//                                    Client.uidReceiver.append((String) tbm.getValueAt(selectedIndex[i], 2));
//                                    if(i != selectedIndex.length - 1)
//                                        Client.uidReceiver.append(",");
//                                }
//                                lblReceiver.setText("发给：" + Client.uidReceiver.toString());
                            }

                            @Override
                            public void mousePressed(MouseEvent event) {
                            }

                            ;

                            @Override
                            public void mouseReleased(MouseEvent event) {
                            }

                            ;

                            @Override
                            public void mouseEntered(MouseEvent event) {
                            }

                            ;

                            @Override
                            public void mouseExited(MouseEvent event) {
                            }

                            ;
                        }
                );

    }
}
