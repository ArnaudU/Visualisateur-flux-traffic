package viewer;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import traitement.FileReader;
import protocole.FacadeTrame;


public class TableMode{
    
    private JTable table;
    private FenetreEnCours fenetre;
    private FileReader file;
    JButton[] mesBoutons;
    String[][] data;
    private JTextArea txt;
    public TableMode(FileReader file, FenetreEnCours fenetre){
        this.fenetre=fenetre;
        this.file=file;
        table=fenetre.getTable();
        txt = new JTextArea();
        initTable();
        fenetre.setResult(txt.getText());
    }

    /*
     * Initialise un tableau lorsque le bouton fenetre.btnDetail est en mode essential
     */
    private void initTable(){
        try{
            fenetre.setResult("");
            int i=1;
            data= new String[file.getOctet().size()][];
            System.out.println("La");
            for(ArrayList<String> octet :file.getOctet()){
                FacadeTrame ft = new FacadeTrame(i,octet);
                data[i-1]=ft.getData(i);
                txt.append(ft.getEssential(i));
                i++;
            }
            //System.out.println(fenetre.getResult());
            String[] title = {"No","IP Src","IP Dest","Protocol","Length","Info"};  
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
