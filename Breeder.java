
public class Breeder {
  
  private String[] g, gg;
  
  public Breeder() { 
    /* YOUR CONSTRUCTOR CODE HERE*/
  }
  
  public Creature[] breed(Creature c, Creature cc){
    Creature[] re;
    g = c.getGamete();
    gg = cc.getGamete();
    String[] phenos = new PunettSquare(0, c, c).getNewPheno(g,gg);
    re = getOffSpring(phenos);
    return re;
  }
  
  private Creature[] getOffSpring(String[] phenos){
    int numOS = randomize(4,2);
    int[] rand = new int[numOS];
    Creature[] re = new Creature[numOS];
    for(int i = 0; i < numOS; i++){
      rand[i] = randomize(numOS,0);
    }
    for(int i = 0; i < numOS; i++){
      phenos[i] += "";
      System.out.println("Phenos["+i+"] is "+ phenos[i]);
      re[i] = new Creature(new char[]{phenos[i].charAt(0),phenos[i].charAt(2),phenos[i].charAt(1),phenos[i].charAt(3)}, randomize(450,0),randomize(500,0));
    }
    return re;
  }
  
  private int randomize(int max, int min){
    return (int)(min + (Math.random() * ((max-min)+1)));
  }
  
}
