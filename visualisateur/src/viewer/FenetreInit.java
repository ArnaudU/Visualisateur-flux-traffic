package viewer;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.Dimension;

/*
 * Classe qui initialise notre application
 */
public class FenetreInit extends JFrame{
    public JPanel container;
    public JButton btnFiltre;
    private JTextField txtPath;
    protected JTextField txtFilter;
    protected JTextArea txtOutput;
    public String save="";
    public JMenuItem btnFind, btnSave, btnSaveDetail;
    public JTable table;
    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double WIDTH = dimension.getWidth()/1.25;
    private final double HEIGHT = dimension.getHeight()/1.25;
    private JMenuBar menubar;
    private double w=WIDTH/600.0;
    private double h=HEIGHT/400.0;
    private final ColorUIResource colorBleuPastel = new ColorUIResource(190,190,242);
    protected final ColorUIResource colorVertPastel = new ColorUIResource(190,242,190);
    protected final ColorUIResource colorJaunePastel = new ColorUIResource(253, 253, 200);

    public FenetreInit(){
        super();
        initWindow();
        initInputTextFiled();
        initBtnPath();
        initBtnFiltrer();
        initBtnSave();
        initMenu();
        initOutputArea();
    }

    /*
     * Creer la fenetre
     */
    private void initWindow(){
        setSize((int)WIDTH,(int)HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(true); 
        this.setTitle("AnalyseurTrafic"); 
        container = new JPanel();
        this.setContentPane(container);
        container.setLayout(null);
        container.setBackground(colorBleuPastel);
    }

    private void initMenu(){
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu menu = new JMenu("Find");
        JMenu menu2 = new JMenu("Save");
        menu.add(btnFind);
        menu2.add(btnSave);
        menu2.add(btnSaveDetail);
        menubar.add(menu);
        menubar.add(menu2);
    }

    private void initOutputArea(){
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setBackground(colorVertPastel);
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBounds((int)(10*w), (int)(10*h), (int)(300*w), (int)((350*h)));
        container.add(scrollPane);
    }

    /*
     * Creer le champ où il faut appuyer le bouton path 
     */
    private void initBtnPath(){
        btnFind = new JMenuItem();
        btnFind.setText("File");
        container.add(btnFind);
    }

     /*
     * Creer le champ où il faut mettre le fitre et le path
     */
    private void initInputTextFiled(){
        txtPath = new JTextField();
        txtFilter = new JTextField();
        txtFilter.setBounds((int)(350*w), (int)(35*h), (int)(150*w), (int)(20*h));
        txtFilter.setEditable(false);
        container.add(txtPath);
        container.add(txtFilter);
    }

    private void initBtnFiltrer(){
        btnFiltre = new JButton();
        btnFiltre.setBorderPainted(true);
        btnFiltre.setIgnoreRepaint(false);
        btnFiltre.setBounds((int)(525*w), (int)(35*h), (int)(35*w), (int)(20*h));
        btnFiltre.setText("Filtrer");
        container.add(btnFiltre);
    }

    private void initBtnSave(){
        btnSaveDetail = new JMenuItem();
        btnSaveDetail.setText("Save Details");
        container.add(btnSaveDetail);
        btnSave = new JMenuItem();
        btnSave.setText("Save");
        container.add(btnSave);
    }

    public void createError(String message){
        JOptionPane.showMessageDialog(this,
        message,
        "Erreur",
        JOptionPane.ERROR_MESSAGE);
    }

    /*
     * Enleve le chaine de caractere du path
     */
    public void clearOutput(){
        this.txtOutput.setText("");
    }


    public void setPath(String path){
        this.txtPath.setText(path);
    }

    public String getPath(){
        return this.txtPath.getText();
    }

    public String getFilter(){
        return this.txtFilter.getText();
    }

    public JTable getTable(){
        return table;
    }

    public void setTxtOutput(String s){
        txtOutput.setText(s);
    }

    public double getW(){
        return w;
    }

    public double getH(){
        return h;
    }
}