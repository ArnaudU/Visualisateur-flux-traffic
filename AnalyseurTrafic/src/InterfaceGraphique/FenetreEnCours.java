package InterfaceGraphique;


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import TraitementFichier.FileReader;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class FenetreEnCours extends FenetreInit{
    private FileReader filereader;

    public FenetreEnCours() {
        super();
        addAction();
       
    }

    public void addAction(){
        btnFind.addActionListener(e -> {
            try {
                pathButton(e);
            } catch (Exception e1) {
                createError(e1.getMessage());
            }
        });
        btnStart.addActionListener(e -> {
            try {
                startButton(e);
            } catch (Exception e1) {
                createError(e1.getMessage());
            }
        });
        btnSave.addActionListener(this::saveButton);
        btnDetail.addActionListener(e -> {
            try {
                detailsButton(e);
            } catch (Exception e1) {
                createError(e1.getMessage());
            }
        });
    }

    private void pathButton(ActionEvent e) throws Exception {
        //JOptionPane.showMessageDialog(null, "Ton message");
        JFileChooser fileChooser = new JFileChooser();
        //file name filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file","txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            setPath(fileChooser.getSelectedFile().getAbsolutePath());
            startButton(e);
        }
    }

    private void createTable(FileReader file){
        new TableMode(file, this);
    }

    private void createDetails(FileReader file){
        new TextMode(file, this);
    }

    private void startButton(ActionEvent e) throws Exception{
        String path = getPath();
        if(!path.isEmpty()){
            //clearOutput(); 
            filereader = new FileReader(path);
            createTable(filereader);
        }
        else{
            createError("File path - Error");
        }
}

    private void detailsButton(ActionEvent e) throws Exception{
        if(btnDetail.getText().equals("Details")){
            if(filereader!=null){
                if(getTxtOutput()!=null){
                    clearOutput();
                }
                createDetails(filereader);
                btnDetail.setText("Essential");
            }
            else{
                createError("Select a File first of all");
            }
        }

        else{
            if(btnDetail.getText().equals("Essential")){
                if(filereader!=null){
                    createTable(filereader);
                    btnDetail.setText("Details");
                }
                else{
                    createError("Select a File first of all");
                }
            }
        }
    }

    private void saveButton(ActionEvent e){
        JFileChooser fileChooser = new JFileChooser();
        //file name filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file","txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            path = getPathWithExtensionName(path, "txt");
            try{
                BufferedWriter out = new BufferedWriter(new FileWriter(path));
                out.write(getResult());
                out.close();
                JOptionPane.showMessageDialog(this, "Success");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Can't write in the file");
            }
        }
    }

    public FileReader getFile() {
        return filereader;
    } 

    private String getPathWithExtensionName(String path, String extension){
        String[] pathSplit = path.split("\\.");
        if(pathSplit[pathSplit.length-1].trim().equals(extension)){
            return path;
        }
        return path+"."+extension;
    }
    
    public void start(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
