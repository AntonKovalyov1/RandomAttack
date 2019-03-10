package randomattack;

import java.util.concurrent.Phaser;

/**
 *
 * @author koval
 */
public class RandomAttack implements Runnable {

    private final int n;
    private final int r;
    private final int x;
    private final int[] vals;
    private final Phaser phaser;
    private final Thread thread = new Thread(this);
    private final RandomAttackNode[] nodes;
    
    public RandomAttack(final int n, 
                        final int r, 
                        final int x, 
                        final int[] vals) {
        this.n = n;
        this.r = r;
        this.x = x;
        this.vals = vals;
        nodes = new RandomAttackNode[n];
        phaser = new Phaser(n + 1);
    }
    
    @Override
    public void run() {
        printConfiguration();
        createProcesses();
        startProcesses();
        
        while (phaser.getRegisteredParties() > 1) {
            phaser.arriveAndAwaitAdvance();
        }
        
        printTermination();
    }
    
    public void start() {
        thread.start();
    }
    
    private void startProcesses() {
        for (int i = 0; i < n; i++) {
            nodes[i].start();
        }
    }
    
    private void createProcesses() {
        Channel[][] channels = new Channel[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                channels[i][j] = new Channel();
            }
        }
        
        for (int i = 0; i < n; i++) {
            nodes[i] = new RandomAttackNode(i, n, r, x, vals[i], phaser, 
                    channels[i], 
                    (Channel[])getColFrom2DChannelArray(channels, i));
        }
    }
    
    private Channel[] getColFrom2DChannelArray(
            final Channel[][] array, int col) {
        int m = array.length;
        int n = array[0].length;
        
        Channel[] column = new Channel[n];
        
        for (int i = 0; i < n; i++) {
            column[i] = array[i][col];
        }
        
        return column;
    }
    
    private void printConfiguration() {
        System.out.println("Config file:");
        System.out.println("# processes: " + n);
        System.out.println("# rounds: " + r);
        System.out.println("# lost message: " + x);
        System.out.println("Process' values: ");
        for (int i = 0; i < n; i++) {
            System.out.print(vals[i] + " ");
        }
        System.out.println("\n");
    }
    
    private void printTermination() {
        System.out.println("RandomAttack termination: \n");
        
        for (int i = 0; i < n; i++) {
            RandomAttackNode curr = nodes[i];
            System.out.println("Node " + (i + 1) + ":");
            System.out.println("Key: " + curr.getKey());
            System.out.println("decision value : " + curr.getDecision());
            System.out.println("Level vector: ");
            int[] L = curr.getL();
            for (int j = 0; j < n; j++) {
                System.out.print(L[j] + " ");
            }
            System.out.println("\n");
        }
    }
}
