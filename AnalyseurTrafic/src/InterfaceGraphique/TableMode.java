package InterfaceGraphique;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Protocole.FacadeTrame;
import TraitementFichier.FileReader;


public class TableMode{
    
    private JTable table;
    private FenetreEnCours fenetre;
    private FileReader file;
    JButton[] mesBoutons;
    String[][] data;

    public TableMode(FileReader file, FenetreEnCours fenetre){
        this.fenetre=fenetre;
        this.file=file;
        table=fenetre.getTable();
        initTable();
    }

    /*
     * Initialise un tableau lorsque le bouton fenetre.btnDetail est en mode essential
     */
    private void initTable(){
        try{
            int i=1;
            data= new String[file.getOctet().size()][];
            for(ArrayList<String> octet :file.getOctet()){
                FacadeTrame ft;
                ft = new FacadeTrame(i,octet);
                data[i-1]=ft.getData(i);
                i++;
            }
            String[] title = {"No","Src","Dest","Protocol","Length","Info"};  
            table = new JTable(data,title);
            table.setEnabled(false);
            table.setRowHeight(30);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            double w = fenetre.getW();
            double h = fenetre.getH();
            fenetre.setScrollPane(scrollPane);
            scrollPane.setBounds((int)(22*w), (int)(60*h), (int)(556*w), (int)(240*h));
            int cpt=0;
            for(int j=0;j<title.length-1;j++){
                cpt+=table.getColumnModel().getColumn(j).getWidth();
            }
            table.getColumnModel().getColumn(title.length-1).setPreferredWidth((int)(556*w-(w*22+cpt)));
         }
        catch (Exception e) {
            fenetre.createError(e.getMessage());
        }
    }
}
