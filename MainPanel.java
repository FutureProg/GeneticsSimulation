/**
 * GeneticsAI JPanel
 * R = red
 * r = blue
 * B = big
 * b = small
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Genotypes will be kept in an object called Genotype
 * Phenotypes will be kept in an object called Phenotype
 * Creature will be the object
 * The objects will be drawn on a white panel
 * Press the mate JButton to create two offspring
 * the new offsprings shall be kept in an Array
 * The total creatures will be kept in an ArrayList
 */
public class MainPanel extends JPanel implements ActionListener{
  public static int PWIDTH, PHEIGHT;
  
  JButton breedButton, punettButton, historyButton,resButton;
  Timer timer;
  ArrayList<Creature> creatures;
  Creature[] sCreatures;
  int selNum;//number of creatures in sCreatures.
  Breeder breeder;
  History hist;
  private int gen;
  
  public MainPanel() { 
    gen = 1;
    selNum = 0;
    setBackground(new Color(183,249,205));
    PWIDTH = getWidth();
    PHEIGHT = getHeight();
    breeder = new Breeder();
    hist = new History();
    sCreatures = new Creature[2];
    sCreatures[0] = null;
    sCreatures[1] = null;
    resButton = new JButton(new ImageIcon(getClass().getResource("Graphics/RestartButton.png")));
    resButton.setBorderPainted(false);
    resButton.setFocusPainted(false);
    resButton.setContentAreaFilled(false);
    
    breedButton = new JButton(new ImageIcon(getClass().getResource("Graphics/BreedButton.png")));
    breedButton.setBorderPainted(false);
    breedButton.setFocusPainted(false);
    breedButton.setContentAreaFilled(false);
    
    punettButton = new JButton(new ImageIcon(getClass().getResource("Graphics/PunettButton.png")));
    punettButton.setBorderPainted(false);
    punettButton.setFocusPainted(false);
    punettButton.setContentAreaFilled(false);
    
    historyButton = new JButton(new ImageIcon(getClass().getResource("Graphics/HistoryButton.png")));
    historyButton.setBorderPainted(false);
    historyButton.setFocusPainted(false);
    historyButton.setContentAreaFilled(false);
    
    add(resButton);
    add(punettButton);
    add(historyButton);
    add(breedButton);
    
    resButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        restart();
      }
    });
    breedButton.addActionListener(this);
    punettButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        if(sCreatures[1] == null)
          return;
        JFrame frame = new JFrame("Punett Square");
        frame.setContentPane(new PunettSquare(gen, sCreatures[0], sCreatures[1]));
        frame.setSize(300,300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }
      
    });
    
    historyButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        JFrame frame = new JFrame("History");
        frame.setContentPane(hist);
        frame.setSize(300,300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }});
    creatures = new ArrayList<Creature>();
    creatures.add(new Creature());
    creatures.add(new Creature(new char[]{'B','B','R','R'},300,90));
    //-----timer-----//
    timer = new Timer(30, new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        repaint();
      }
    });
    timer.start();
    //-----end timer-----//
    
    //-----mouse listener-----//
    addMouseListener(new MouseAdapter(){
      public void mouseReleased(MouseEvent evt){
        int count = 1;
        for(Creature cr: creatures){
          if(cr.isClicked(evt.getX(),evt.getY())){
            if(selNum > 1){
              selNum = 0;
              if(sCreatures[0] != null)
                sCreatures[0].setMovable(true);
              if(sCreatures[1] != null)
                sCreatures[1].setMovable(true);
              sCreatures[1] = null;
              sCreatures[0] = null;
            }
            cr.setMovable(false);
            sCreatures[selNum] = cr;
            System.out.println("sCreature["+selNum+"] taken");
            selNum++;
            if(sCreatures[1] == sCreatures[0]){
              selNum = 0;
              sCreatures[0].setMovable(true);
              sCreatures[1].setMovable(true);
              sCreatures[1] = null;
              sCreatures[0] = null;
            }
            break;
          }else if((sCreatures[1] == null ||
                    (sCreatures[1] != null && sCreatures[0] != null &&
                     !sCreatures[1].isClicked(evt.getX(),evt.getY())))&&
                   count >= creatures.size()){
            for(Creature ccr: creatures)
              ccr.setMovable(true);
            sCreatures[0] = null;
            sCreatures[1] = null;
            selNum = 0;
          }
          count++;
        }
      }
    });
    //-----end mouse listener-----//
    
  }
  
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g; 
    //calcDeads();
    for(Creature cret : creatures)
      cret.draw(g2d);
  }
  
  public void calcDeads(){
    for(Creature cret: creatures){
      if(cret.isDead())
        creatures.remove(cret);
    }
  }
  public void actionPerformed(ActionEvent evt){
    Creature[] newCreatures;
    if(sCreatures != null &&sCreatures[1] != null && sCreatures[0] != null){
      if(sCreatures[0].canBreed() && sCreatures[1].canBreed()){
        Creature[]temp =  breeder.breed(sCreatures[0],sCreatures[1]);
        newCreatures = new Creature[temp.length];
        creatures.clear();
        for(int i = 0; i < temp.length; i++){
          creatures.add(temp[i]);
          newCreatures[i] = temp[i];
        }
        hist.add(sCreatures,"P",gen);
        //creatures.remove(sCreatures[0]);
        //creatures.remove(sCreatures[1]);
        sCreatures[0] = null;
        sCreatures[1] = null;
        selNum = 0;
        hist.add(newCreatures, "F", gen);
        gen++;
      }
    }
  }
  
  public void restart(){
    gen = 1;
    selNum = 0;
    setBackground(new Color(183,249,205));
    PWIDTH = getWidth();
    PHEIGHT = getHeight();
    breeder = new Breeder();
    hist = new History();
    sCreatures = new Creature[2];
    sCreatures[0] = null;
    sCreatures[1] = null;
    creatures = new ArrayList<Creature>();
    creatures.add(new Creature());
    creatures.add(new Creature(new char[]{'B','B','R','R'},300,90));
  }
}