package viewer;

import java.awt.Font;
import traitement.FileReader;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import protocole.FacadeTrame;

public class TextMode {
    
    private JTextArea txtOutput;
    private FenetreEnCours fenetre;
    private FileReader file;

    public TextMode(FileReader file, FenetreEnCours fenetre){
        this.fenetre=fenetre;
        this.file=file;
        txtOutput = new JTextArea();
        init();
        fenetre.setResult(txtOutput.getText());
    }

    /*
     * Initialise une liste des protocole detaille lorsque le bouton fenetre.btnDetail est en mode detail
    */
    private void init(){
        try {
            txtOutput.setFont(new Font("Arial",5,16));
            txtOutput.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(txtOutput);
            double w = fenetre.getW();
            double h = fenetre.getH();
            int i=1;
            fenetre.setTxtOutput(txtOutput);
            scrollPane.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
            for(ArrayList<String> octet :file.getOctet()){
                FacadeTrame ft = new FacadeTrame(i,octet);
                fenetre.appendOutput(ft.toString());
                i++;
            }
            fenetre.setScrollPane(scrollPane);
            
        }
        catch(Exception e){
            fenetre.createError(e.getMessage());
        }
    }
}
