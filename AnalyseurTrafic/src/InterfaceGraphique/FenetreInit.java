package InterfaceGraphique;

import javax.swing.*;

public class FenetreInit extends JFrame {
    public JPanel container;
    public JButton btnFind;
    private JTextField txtPath;
    private JTextArea txtOutput;
    public JButton btnDetail;
    public JButton btnStart;
    public JButton btnSave;
    public JTable table;
    private JScrollPane scrollPane;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 650;
    private double w=(double)WIDTH/600.0;
    private double h=(double)HEIGHT/400.0;

    public FenetreInit(){
        super();
        initWindow();
        initInputTextFiled();
        initBtnPath();
        //initOutputArea();
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
        txtPath = new JTextField();
        txtPath.setBounds((int)(30*w), (int)(20*h), (int)(440*w), (int)(30*h));
        container.add(txtPath);
    }

    public void setPath(String path){
        this.txtPath.setText(path);
    }

    public String getPath(){
        return this.txtPath.getText();
    }

    private void initBtnPath(){
        btnFind = new JButton();
        btnFind.setBounds((int)(480*w), (int)(17*h), (int)(100*w), (int)(35*h));
        btnFind.setText("Path");
        container.add(btnFind);
    }

    public void clearOutput(){
        this.txtOutput.setText("");
    }

    public FenetreInit appendOutput(String text){
        this.txtOutput.append(text);
        return this;
    }

    public String getResult(){
        return this.txtOutput.getText();
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

    public JTable getTable(){
        return table;
    }

    public JScrollPane getScrollPane(){
        return scrollPane;
    }

    public JTextArea getTxtOutput(){
        return txtOutput;
    }

    public void setTxtOutput(JTextArea t){
        txtOutput=t;
    }
    
    public void removeScrollPaneFromeContainer(){
        container.remove(scrollPane);
    }
    public void start(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public double getW(){
        return w;
    }

    public double getH(){
        return h;
    }

    public void setScrollPane(JScrollPane scrollPane2) {
        scrollPane=scrollPane2;
    }
}