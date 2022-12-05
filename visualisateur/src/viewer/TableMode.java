package viewer;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.plaf.ColorUIResource;

import traitement.FileReader;
import protocole.Trame;
import protocole.MultiTrame;
import protocole.OctetInvalidException;
import protocole.ProtocoleInvalidException;

/*
 * Classe qui traite un tableau d'octets
 */
public class TableMode{
    
    private JTable table,tableFiltrer;
    private FenetreEnCours fenetre;
    private final ColorUIResource color = new ColorUIResource(253, 253, 200);
    private String[][] data;
    private JTextArea txt;
    JScrollPane scrollPane ,scroll2, scrollgraph, scrollgraph2;
    private double w;
    private double h;
    private int cpt;
    private ArrayList<String[]> arrayfiltre;
    private MultiTrame trames;
    String stock;
    private String[] title = {"No","IP Src","IP Dest","Protocol","Length","Info"}; ;
    public TableMode(FileReader file, FenetreEnCours fenetre) throws ProtocoleInvalidException, OctetInvalidException{
        this.fenetre=fenetre;
        w=fenetre.getW();
        h=fenetre.getH();
        txt = new JTextArea();
        trames = new MultiTrame(file);
        initTable();
        stock = trames.getGraphe();
        fenetre.setResult(txt.getText());
    }

    /*
     * Initialise un tableau lorsque le bouton fenetre.btnDetail est en mode essential
     */
    private void initTable(){
        fenetre.setResult("");
        int i=1;
        data= new String[trames.size()][];
        for(Trame ft : trames.getTrames()){
            data[i-1]=ft.getData(i);
            txt.append(ft.getEssential(i));
            i++;
        } 
        table = new JTable(data,title);
        table.setEnabled(false);
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setBackground(color);
        scrollPane.setBounds((int)(325*w), (int)(80*h), (int)(260*w), (int)((280*h)));
        fenetre.add(scrollPane);
        cpt=0;
        for(int j=0;j<title.length-1;j++){
            cpt+=table.getColumnModel().getColumn(j).getWidth();
        }
        table.getColumnModel().getColumn(title.length-1).setPreferredWidth((int)(556*w-(w*22+cpt)));
    }

    private void defiltrer(){
        scroll2.setVisible(false);
        scrollPane.setVisible(true);
        fenetre.setTxtOutput(stock);
    }

    private boolean accept(String filter,String[] data) throws Exception{
        if(!filter.contains("&&") && !filter.contains("||")){
            return data[3].equalsIgnoreCase(filter) || data[1].equalsIgnoreCase(filter);
        }
        else{
            String saveFilter=filter;
            String tab[] = saveFilter.split(" && ");
            if(tab.length>1){
                String s="";
                for(int i=1;i<tab.length;i++){
                    s+=tab[i];
                }
                boolean b = accept(tab[0], data) && accept(s, data);
                return b;
            }
            else{
                String tab2[] = filter.split(" \\|\\| ");
                if(tab2.length>1){
                    String s="";
                    for(int i=1;i<tab2.length;i++){
                        s+=tab2[i];
                    }
                    return accept(tab2[0], data) || accept(s, data);
                }
                return false;
            }
        }
    }

    private boolean accept(String filter,Trame trame) throws Exception{
        if(trame.getIPv4()==null){
            return false;
        }
        if(!filter.contains("&&") && !filter.contains("||")){
            return trame.getIPv4().getSrc().equalsIgnoreCase(filter) || trame.getLastProtocol().equalsIgnoreCase(filter);
        }
        else{
            String saveFilter=filter;
            String tab[] = saveFilter.split(" && ");
            if(tab.length>1){
                String s="";
                for(int i=1;i<tab.length;i++){
                    s+=tab[i];
                }
                boolean b = accept(tab[0], trame) && accept(s, trame);
                return b;
            }
            else{
                String tab2[] = filter.split(" \\|\\| ");
                if(tab2.length>1){
                    String s="";
                    for(int i=1;i<tab2.length;i++){
                        s+=tab2[i];
                    }
                    return accept(tab2[0], trame) || accept(s, trame);
                }
                return false;
            }
        }
    }

    private void filtreTab(String s) throws Exception{
        arrayfiltre = new ArrayList<>();
        for(int i=0;i<data.length;i++){
            if(accept(s,data[i])){
                arrayfiltre.add(data[i]);
            }
        }
        if(arrayfiltre.size()==0){
            throw new Exception();
        }
        String[][] datafiltre= new String[arrayfiltre.size()][];
        int j=0;
        for(String[] data : arrayfiltre){
            datafiltre[j]=data;
            j++;
        }
        tableFiltrer= new JTable(datafiltre,title);
        tableFiltrer.setEnabled(false);
        tableFiltrer.setRowHeight(30);
        tableFiltrer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableFiltrer.getColumnModel().getColumn(title.length-1).setPreferredWidth((int)(556*w-(w*22+cpt)));
        tableFiltrer.setBackground(color);
        scrollPane.setVisible(false);
        scroll2 = new JScrollPane(tableFiltrer,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll2.setBounds((int)(325*w), (int)(80*h), (int)(260*w), (int)((280*h)));
        fenetre.add(scroll2);
    }

    private void filtreGraphe(String s) throws Exception{
        ArrayList<Trame> res = new ArrayList<>();
        for(Trame trame : trames.getTrames()){
            if(accept(s, trame)){
                res.add(trame);
            }
        }
        MultiTrame tmp = new MultiTrame(res);
        fenetre.setTxtOutput(tmp.getGraphe());
    }


    public void filtreButton(ActionEvent e) {
        try{
            String s = fenetre.getFilter();
            if(s.length()==0){
                defiltrer();
                return;
            }
            if(scroll2!=null){
                defiltrer();
            }
            filtreTab(s);
            filtreGraphe(s);
        }
        catch(Exception ex){
            fenetre.createError("Ce filtre ne marche pas!");
        }
    }

    public void removePane(){
        if(scroll2!=null){
            scroll2.setVisible(false);
        }
        if(scrollPane!=null){
            scrollPane.setVisible(false);
        }
    }

    public MultiTrame getTrames(){
        return trames;
    }

    public JTextArea getTxt(){
        return txt;
    }
}
