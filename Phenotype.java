public class Phenotype{
  
  private int size, colour;
  protected static final int SIZE_BIG = 0, SIZE_SMALL = 1;
  protected static final int COL_RED = 0, COL_BLUE = 1;
  
  public Phenotype(char size, char colour){
    
    System.out.println("Got size : " + size);
    System.out.println("Got colour: "+ colour);
    switch(size){
      case 'B':
        this.size = SIZE_BIG;
        System.out.println("Size is Big");
        break;
      case 'b':
        this.size = SIZE_SMALL;
        System.out.println("Size is Small");
        break;
      default:
        System.err.println("Size find Error");
        System.out.println("Size found was: " + size);
    }
    
    switch(colour){
      case 'R':
        this.colour = COL_RED;
        System.out.println("Color is Red");
        break;
      case 'r':
        this.colour = COL_BLUE;
        System.out.println("Color is Blue");
        break;
      default:
        System.err.println("Colour find Error");
        System.out.println("Colour found was: " + colour);
    }
          
  }
  
  public int getSize(){
    return size;
  }
  public int getColour(){
    return colour;
  }
  
}