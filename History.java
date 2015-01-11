import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class History extends JPanel{
  private File file;
  private Scanner read;
  private PrintWriter print;
  private ArrayList<String> list;
  protected JTextArea textArea;
  protected JScrollPane scrollPane;
  private boolean started;
  private PunettSquare pSquare;
  
  public History(){
    pSquare = new PunettSquare();
    list = new ArrayList<String>();
    file = new File("res/History.txt");
    try{
      read = new Scanner(file);
      read();
      print = new PrintWriter(file);
      write();
    }catch(IOException e){
      System.err.println(e.getMessage());
      return;
    }
    
    setBackground(new Color(183,249,205));
    setLayout(new BorderLayout());
    textArea = new JTextArea(10,1);
    textArea.setSize(300,300);
    textArea.setEditable(false);
    scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                 JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    //scrollPane.setPreferredSize(new Dimension(290,270));
    add(scrollPane,BorderLayout.CENTER);
    
    
  }
  
  public void addNotify(){
    super.addNotify();
    read();
  }
  
  public void read(){
    if(textArea != null)
      textArea.setText("");
    while(true){
      if(read.hasNextLine())
        list.add(read.nextLine());
      else
        break;
    }
    for(String str: list)
      textArea.append(str+"\n\n");
  }
  
  public void write(){
    print.print("");
    for(int i = 0; i < list.size(); i++){
      print.println(list.get(i));
    }
  }
  
  public void add(Creature[] cs, String type, int gen){
    String temp = "";
    char[] g;
    for(Creature c: cs){
      g = c.getGeno().getGenotypes();
      temp += pSquare.sortCharPheno(g)[0] + "" + pSquare.sortCharPheno(g)[1]+ "" + 
        pSquare.sortCharPheno(g)[2]+ "" + pSquare.sortCharPheno(g)[3]+" ";
    }
    list.add(type + gen + ": " + temp);
    write();
  }
}