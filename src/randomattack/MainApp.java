package randomattack;

public class MainApp {
    
    public static void main(String[] args) {
        
        Parser parser = new Parser();
        parser.parse(args[0]);
        
        RandomAttack randomAttack = new RandomAttack(parser.getN(), 
                                                     parser.getR(), 
                                                     parser.getX(), 
                                                     parser.getVals());
        
        randomAttack.start();
    }
}
