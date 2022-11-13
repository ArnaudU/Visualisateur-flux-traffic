package InterfaceGraphique;

import javax.swing.*;


public class FenetreInit extends JFrame {
    public JPanel container;
    public JButton btnFind;
    private JTextField txt_path;
    private JTextArea txt_output;
    public JButton btnDetail;
    public JButton btnStart;
    public JButton btnSave;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 650;
    private double w=(double)WIDTH/600.0;
    private double h=(double)HEIGHT/400.0;

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
        container.setLayout(null);
    }

    private void initInputTextFiled(){
        txt_path = new JTextField();
        txt_path.setBounds((int)(30*w), (int)(20*h), (int)(440*w), (int)(30*h));
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
        btnFind.setBounds((int)(480*w), (int)(17*h), (int)(100*w), (int)(35*h));
        btnFind.setText("Path");
        container.add(btnFind);
    }

    private void initOutputArea(){
        txt_output = new JTextArea();
        txt_output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txt_output);
        scrollPane.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
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
        btnDetail.setBounds((int)(400*w), (int)(310*h), (int)(100*w), (int)(35*h));
        btnDetail.setText("Details");
        container.add(btnDetail);
    }

    private void initBtnStart(){
        btnStart = new JButton();
        btnStart.setBounds((int)(100*w), (int)(310*h), (int)(100*w), (int)(35*h));
        btnStart.setText("Decode");
        container.add(btnStart);
    }

    private void initBtnSave(){
        btnSave = new JButton();
        btnSave.setBounds((int)(250*w), (int)(310*h), (int)(100*w), (int)(35*h));
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