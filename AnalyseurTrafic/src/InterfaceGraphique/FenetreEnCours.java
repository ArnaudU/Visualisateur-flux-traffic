package InterfaceGraphique;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.Font;
import Protocole.FacadeTrame;
import Protocole.OctetInvalidException;
import Protocole.ProtocoleInvalidException;
import TraitementFichier.FileReader;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class FenetreEnCours {
    private FenetreInit window;
    private FileReader filereader;

    public FenetreEnCours() {
        window = new FenetreInit();
        window.btnFind.addActionListener(this::pathButton);
        window.btnStart.addActionListener(this::startButton);
        window.btnSave.addActionListener(this::saveButton);
        window.btnDetail.addActionListener(this::detailsButton);
    }

    private void pathButton(ActionEvent e) {
        //JOptionPane.showMessageDialog(null, "Ton message");
        JFileChooser fileChooser = new JFileChooser();
        //file name filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file","txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(window);
        if(result == JFileChooser.APPROVE_OPTION){
            window.setPath(fileChooser.getSelectedFile().getAbsolutePath());
            startButton(e);
        }
    }

    private void createTable(FileReader file){
        try {window.removeScrollPaneFromeContainer();}
        catch(NullPointerException e){}
        int i=1;
        try{
            String[][] data= new String[file.getOctet().size()][];
            for(ArrayList<String> octet :file.getOctet()){
                FacadeTrame ft = new FacadeTrame(i,octet);
                data[i-1]=ft.getData(i);
                i++;
            }
            String[] title = {"No","Src","Dest","Protocol","Length","Info"};  
            //initTableArea(data,title);
            JTable table = window.getTable();
            table = new JTable(data,title);
            table.setEnabled(false);
            table.setRowHeight(30);
            JScrollPane scrollPane = window.getScrollPane();
            scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            double w = window.getW();
            double h = window.getH();
            window.setScrollPane(scrollPane);
            scrollPane.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
            int cpt=0;
            for(int j=0;j<title.length-1;j++){
                cpt+=table.getColumnModel().getColumn(j).getWidth();
            }
            table.getColumnModel().getColumn(title.length-1).setPreferredWidth((int)(556*w-(w*22+cpt)));
            window.container.add(scrollPane);
        }
        catch(Exception exc){
            window.createError(exc.getMessage());
        }
    }

    private void createDetails(FileReader file) throws ProtocoleInvalidException, OctetInvalidException{
        try {window.removeScrollPaneFromeContainer();}
        catch(NullPointerException e){}
        JTextArea txtOutput = new JTextArea();
        txtOutput.setFont(new Font("Arial",5,16));
        txtOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        double w = window.getW();
        double h = window.getH();
        window.setTxtOutput(txtOutput);
        scrollPane.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
        window.setScrollPane(scrollPane);
        window.container.add(scrollPane);
        int i=1;
        for(ArrayList<String> octet :filereader.getOctet()){
            FacadeTrame ft = new FacadeTrame(i,octet);
            window.appendOutput(ft.toString());
            i++;
        }
    }

    private void startButton(ActionEvent e){
        String path = window.getPath();
        try{
            if(!path.isEmpty()){
                //window.clearOutput(); 
                filereader = new FileReader(path);
                createTable(filereader);
            }
            else{
                window.createError("File path - Error");
            }
        }
        catch(Exception exc){
            window.createError(exc.getMessage());
        }
    }

    private void detailsButton(ActionEvent e){
        try{
            if(window.btnDetail.getText().equals("Details")){
                if(filereader!=null){
                    if(window.getTxtOutput()!=null){
                        window.clearOutput();
                    }
                    createDetails(filereader);
                    window.btnDetail.setText("Essential");
                }
                else{
                    window.createError("Select a File first of all");
                }
            }

            else{
                if(window.btnDetail.getText().equals("Essential")){
                    if(filereader!=null){
                        createTable(filereader);
                        window.btnDetail.setText("Details");
                    }
                    else{
                        window.createError("Select a File first of all");
                    }
                }
            }
        }
        catch(Exception exc){
            window.createError(exc.getMessage());
        }
    }

    private void saveButton(ActionEvent e){
        JFileChooser fileChooser = new JFileChooser();
        //file name filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file","txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(window);
        if(result == JFileChooser.APPROVE_OPTION){
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            path = getPathWithExtensionName(path, "txt");
            try{
                BufferedWriter out = new BufferedWriter(new FileWriter(path));
                out.write(window.getResult());
                out.close();
                JOptionPane.showMessageDialog(window, "Success");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(window, "Can't write in the file");
            }
        }
    }

    private String getPathWithExtensionName(String path, String extension){
        String[] pathSplit = path.split("\\.");
        if(pathSplit[pathSplit.length-1].trim().equals(extension)){
            return path;
        }
        return path+"."+extension;
    }

    public void start() {
        window.start(); 
    }
}
