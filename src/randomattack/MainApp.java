package randomattack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author koval
 */
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
