
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;
import java.io.IOException;

public class Creature{
  Genotype geno;
  Phenotype pheno;
  
  private final int SIZE;
  private BufferedImage img;
  private final int SPEED = 1, LIFE = 10;
  private int x,y,theta;
  private boolean moveUp, moveLeft, canBreed, canMove, created, death, dead;
  private long startTime;
  
  public Creature(){
    this(new char[] { 'b','b','r','r'}, 0 +(int) (Math.random() * ((450-0)+1)),  0 + (int)(Math.random() * ((450-0)+1)));
  }
  
  public Creature(char[] genotypes, int newX, int newY){
    theta = 0;
    geno = new Genotype((char[])genotypes);
    createPheno();
    x = newX;
    y = newY;
    canBreed = true;
    canMove = true;
    created = true;
    try{
      img = ImageIO.read(getClass().getResource("Graphics/"+getSize()+"/"+getColour()+".png"));
    }catch(IOException e){
      System.err.println(e.getMessage());
      
    }
    SIZE = img.getWidth();
    switch(randomize(1,0)){
      case 1:
      moveLeft = true;
    }
    switch(randomize(1,0)){
      case 1:
        moveUp = true;
    }
  }
  
  private void createPheno(){
    char[] sizes = new char[] {geno.getGenotypes()[0], geno.getGenotypes()[1]};
    char[] cols = new char[] {geno.getGenotypes()[2], geno.getGenotypes()[3]};
    System.err.println("GOT COLOURS = " + cols[0] + " , " + cols[1]);
    System.err.println("GOT SIZES = " + sizes[0] + " , " + sizes[1]);
    char pcol, psize;
    System.out.println("S: " + sizes[0]);
    System.out.println(sizes[1]);
    //get the sizes
    if(sizes[0] == sizes[1])
      psize = sizes[0];
    else if (Character.isUpperCase(sizes[0]) && Character.isLowerCase(sizes[1]))
      psize = sizes[0];
    else if (Character.isUpperCase(sizes[1]) && Character.isLowerCase(sizes[0]))
      psize = sizes[1];
    else
      return;
    
    pcol = 'a';
    System.out.println("psize = " + psize);
    //get the colours
    if(cols[0] == cols[1])
      pcol = cols[0];
    else if (Character.isUpperCase(cols[0]) && Character.isLowerCase(cols[1]))
      pcol = cols[0];
    else if (Character.isUpperCase(cols[1]) && Character.isLowerCase(cols[0]))
      pcol = cols[1];
    else{
      System.exit(0);
    }
    if(pcol == 'b'|| pcol == 'B'){
      char temp = pcol;
      pcol = psize;
      psize = temp;
    }
    if(psize == 'r' || psize == 'R'){
      char temp = psize;
      psize = pcol;
      pcol = temp;
    }
    System.out.println("pcol = " + pcol);
    
    //create the phenotype object
    pheno = new Phenotype(psize,pcol);
  }
  
  //-----paint function-----//
  public void draw(Graphics2D g){
    if(!created)
      return;
    if(!death)
      calcMovement();
    else
      calcDeath();
    /*AffineTransform at = new AffineTransform();
    at.setToRotation(Math.toRadians(theta+=SPEED),x+(img.getWidth()/2),y+(img.getHeight()/2));
    at.translate(x,y);
    Graphics2D g2d = img.createGraphics();
    g.setTransform(at);*/
    g.drawImage(img,null,x,y);
  }
  //-----end of paint function-----//
  
  //-----movement-----//
  public void calcMovement(){
    if(!moveLeft && (x+SIZE) >= GeneticsAI.PWIDTH-5)
      moveLeft = true;
    if(moveLeft && x <= 0)
      moveLeft = false;
    if(!moveUp && (y+SIZE) >= GeneticsAI.PHEIGHT-25)
      moveUp = true;
    if(moveUp && y <= 0)
      moveUp = false;
    
    move();
  }
  
  public void calcDeath(){
    if(System.currentTimeMillis()- startTime >= 10)
      dead = true;
  }
  
  public void move(){
    if(canMove){
      if(!moveLeft)
        x += SPEED;
      else
        x -= SPEED;
      
      if(!moveUp)
        y += SPEED;
      else
        y -= SPEED;
    }
  }
  //-----end of movement-----//
  
  //-----set functions-----//
  public void setBreedable(boolean br){
    canBreed = br;
  }
  public void setMovable(boolean mv){
    canMove = mv;
  }
  public void setDestroy(){
    startTime = System.currentTimeMillis();
    death = true;
  }
  //-----end of set functions-----//
  
  //------Return functions------//
  public boolean isDead(){
    return dead;
  }
  public String getColour(){
    //System.out.println("pheno col: "+pheno.getColour());
    try{
    switch(pheno.getColour()){
      case Phenotype.COL_RED:
        return "Red";
      case Phenotype.COL_BLUE:
        return "Blue";
      default:
        System.err.println("ERROR");
    }
    }catch(NullPointerException e){
      System.err.println("getColour(): " +e.getMessage());
    }
    return "Pink";
  }  
  public String getSize(){
    switch(pheno.getSize()){
      case Phenotype.SIZE_BIG:
        return "Big";
      case Phenotype.SIZE_SMALL:
        return "Small";
      default:
        return "Big";
    }
  }  
  public Genotype getGeno(){
    return geno;
  }
  public Phenotype getPheno(){
    return pheno;
  }
  
  public String[] getGamete(){
    String[] re = new String[]{"","","",""};
    //System.out.println(""+geno.getGenotypes()[0]+""+ geno.getGenotypes()[1]);
    re[0] += ""+geno.getGenotypes()[0] + ""+geno.getGenotypes()[2];
    re[1] += ""+geno.getGenotypes()[0] + ""+geno.getGenotypes()[3];
    re[2] += ""+geno.getGenotypes()[1] + ""+geno.getGenotypes()[2];
    re[3] +=""+ geno.getGenotypes()[1] +""+ geno.getGenotypes()[3];
    return re;
  }  
  public boolean canBreed(){
    return canBreed;
  }  
  public boolean isClicked(int mouseX, int mouseY){
    if(mouseX >= x && mouseX <= x+SIZE&&
       mouseY >= y && mouseY <= y+SIZE)
      return true;
    return false;
  }
  //-----end of return functions-----//
  private int randomize(int max, int min){
    return (int)(min + (Math.random() * ((max-min)+1)));
  }
}