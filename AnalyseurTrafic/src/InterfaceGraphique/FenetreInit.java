package InterfaceGraphique;


import java.awt.Color;

import javax.swing.*;


public class FenetreInit extends JFrame {
    public JPanel container;
    public JButton btnFind;
    private JTextField txt_path;
    private JTextArea txt_output;
    public JButton btnDetail;
    public JButton btnStart;
    public JButton btnSave;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public FenetreInit(){
        super();
        initWindow();
        initInputTextFiled();
        initBtnPath();
        initOutputArea();
        initBtnDetails();
        initBtnStart();
        initBtnSave();
    
    }

    private void initWindow(){
        setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false); 
        this.setTitle("AnalyseurTrafic"); 
        container = new JPanel();
        this.setContentPane(container);
        this.setBackground(Color.BLACK);
        container.setLayout(null);
    }

    private void initInputTextFiled(){
        txt_path = new JTextField();
        txt_path.setBounds(20, 20, 440, 30);
        container.add(txt_path);
    }

    public void setPath(String path){
        this.txt_path.setText(path);
    }

    public String getPath(){
        return this.txt_path.getText();
    }

    private void initBtnPath(){
        btnFind = new JButton();
        btnFind.setBounds(480,17,100,35);
        btnFind.setText("Path");
        container.add(btnFind);
    }

    private void initOutputArea(){
        txt_output = new JTextArea();
        txt_output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txt_output);
        scrollPane.setBounds(22, 60, 556, 240);
        container.add(scrollPane);
    }

    public void clearOutput(){
        this.txt_output.setText("");
    }

    public FenetreInit appendOutput(String text){
        this.txt_output.append(text);
        return this;
    }

    public String getResult(){
        return this.txt_output.getText();
    }

    private void initBtnDetails(){
        btnDetail = new JButton();
        btnDetail.setBounds(100,310,100,35);
        btnDetail.setText("details");
        container.add(btnDetail);
    }

    private void initBtnStart(){
        btnStart = new JButton();
        btnStart.setBounds(250,310,100,35);
        btnStart.setText("Decode");
        container.add(btnStart);
    }

    private void initBtnSave(){
        btnSave = new JButton();
        btnSave.setBounds(400,310,100,35);
        btnSave.setText("Save");
        container.add(btnSave);
    }

    public void createError(String message){
        JOptionPane.showMessageDialog(this,
        message,
        "Erreur",
        JOptionPane.ERROR_MESSAGE);
    }

    public void start(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}