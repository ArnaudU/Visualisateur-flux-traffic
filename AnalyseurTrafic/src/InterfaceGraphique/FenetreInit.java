package InterfaceGraphique;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FenetreInit extends JFrame {
    public JPanel container;
    public JButton btnFind;
    private JTextField txtPath;
    private JTextArea txtOutput;
    public JButton btnDetail;
    public JButton btnStart;
    public JButton btnSave;
    public JTable table;
    JMenuBar menu;
    private JScrollPane scrollPane;
    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double WIDTH = dimension.getWidth()/1.25;
    private final double HEIGHT = dimension.getHeight()/1.25;
    private double w=WIDTH/600.0;
    private double h=HEIGHT/400.0;
    

    public FenetreInit(){
        super();
        initWindow();
        initInputTextFiled();
        initBtnPath();
        initBtnDetails();
        initBtnStart();
        initBtnSave();
        addResizable();
    }

    /*
     * Creer la fenetre
     */
    private void initWindow(){
        setSize((int)WIDTH,(int)HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false); 
        this.setTitle("AnalyseurTrafic"); 
        container = new JPanel();
        this.setContentPane(container);
        container.setLayout(null);
    }

    /*
     * Creer le champ où il faut mettre le champ path 
     */
    private void initInputTextFiled(){
        txtPath = new JTextField();
        txtPath.setBounds((int)(30*w), (int)(20*h), (int)(440*w), (int)(30*h));
        container.add(txtPath);
    }

    /*
     * Creer le champ où il faut appuyer le bouton path 
     */
    private void initBtnPath(){
        btnFind = new JButton();
        btnFind.setBounds((int)(480*w), (int)(17*h), (int)(100*w), (int)(35*h));
        btnFind.setText("Path");
        container.add(btnFind);
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

    private void addResizable(){
        addComponentListener(
            new ComponentAdapter(){
            public void componentResized(ComponentEvent e){
                h=(double)getHeight()/400.0;
                w=(double)getWidth()/600.0;
                btnDetail.setBounds((int)(400*w), (int)(310*h), (int)(100*w), (int)(35*h));
                btnFind.setBounds((int)(480*w), (int)(17*h), (int)(100*w), (int)(35*h));
                btnStart.setBounds((int)(100*w), (int)(310*h), (int)(100*w), (int)(35*h));
                btnSave.setBounds((int)(250*w), (int)(310*h), (int)(100*w), (int)(35*h));
                txtPath.setBounds((int)(30*w), (int)(20*h), (int)(440*w), (int)(30*h));
                if(scrollPane!=null){
                    if(table!=null){
                        table.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
                    }
                    if(txtOutput!=null){
                        table.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
                    }
                    scrollPane.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
                }
            }
        });
    }

    /*
     * Enleve le chaine de caractere du path
     */
    public void clearOutput(){
        this.txtOutput.setText("");
    }

    public FenetreInit appendOutput(String text){
        this.txtOutput.append(text);
        return this;
    }

    public void setPath(String path){
        this.txtPath.setText(path);
    }

    public String getPath(){
        return this.txtPath.getText();
    }

    public String getResult(){
        return this.txtOutput.getText();
    }

    public JTable getTable(){
        return table;
    }

    public JScrollPane getScrollPane(){
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane2) {
        scrollPane=scrollPane2;
    }

    public JTextArea getTxtOutput(){
        return txtOutput;
    }

    public void setTxtOutput(JTextArea t){
        txtOutput=t;
    }

    public double getW(){
        return w;
    }

    public double getH(){
        return h;
    }

    public void removeScrollPaneFromeContainer(){
        container.remove(scrollPane);
    }

    public void start(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    } 
}