package randomattack;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

public class RandomAttackNode implements Runnable {
    
    public static final int UNDEFINED = -1;
    
    private final int id;
    private final int n;
    private final int r;
    private final int x;
    
    private int key = UNDEFINED;
    
    private final Phaser phaser;
    private final Thread thread = new Thread(this);
    
    private final int[] V;
    private final int[] L;
    
    private final Channel[] sendChannel;
    private final Channel[] receiveChannel;
    
    private int currRound = 0;
    private int messageCounter;
    
    private int decision = 0;

    public RandomAttackNode(final int id, 
                        final int n, 
                        final int r, 
                        final int x, 
                        final int val,
                        final Phaser phaser) {
        this.id = id;
        this.n = n;
        this.r = r;
        this.x = x;
        
        this.phaser = phaser;
        
        V = new int[n];
        L = new int[n];
        
        sendChannel = new Channel[n];
        receiveChannel = new Channel[n];
        
        init(val);
    }
    
    private void init(int val) {
        for (int i = 0; i < V.length; i++) {
            V[i] = UNDEFINED;
            L[i] = UNDEFINED;
        }
        V[id] = val;
        L[id] = 0;
    }
    
    @Override
    public void run() {
        if (id == 0) {
            setRandomKey();
        }
        
        while (currRound < r) {
            nextRound();
        }
        
        decide();
        
        phaser.arriveAndDeregister();
    }
    
    public void start() {
        thread.start();
    }
    
    private void nextRound() {
        phaser.arriveAndAwaitAdvance();
        broadcast();
        phaser.arriveAndAwaitAdvance();
        receive();
    }
    
    private void decide() {
        if (key != UNDEFINED && L[id] >= key && allValuesAreOne()) {
            decision = 1;
        }
    }
    
    private void broadcast() {
        Message msg = buildMessage();
        
        messageCounter = currRound * n * (n - 1) + id * (n - 1) + 1;
        for (int i = 0; i < n; i++) {
            if (i != id) {
                
                if (messageCounter != x) {
                    sendChannel[i].add(msg);
                }
                
                messageCounter++;
            }
        }
    }
    
    private void receive() {
        currRound++;
        
        for (int i = 0; i < n; i++) {
            Message msg = receiveChannel[i].poll();
            if (msg != null) {
                if (msg.key != UNDEFINED) {
                    key = msg.key;
                }

                for (int j = 0; j < n; j++) {
                    if (msg.V[j] != UNDEFINED) {
                        V[j] = msg.V[j];
                    }

                    if (msg.L[j] != UNDEFINED) {
                        L[j] = Math.max(L[j], msg.L[j]);
                    }
                }
            }
        }
        
        L[id] = Collections.min(Arrays.asList(L[id])) + 1;
    }
    
    private void setRandomKey() {
        key = ThreadLocalRandom.current().nextInt(1, r + 1);
    }
    
    private Message buildMessage() {
        Message msg = new Message(key, n);
        msg.setV(V);
        msg.setL(L);
        return msg;
    }
    
    private boolean allValuesAreOne() {
        for (int i = 0; i < n; i++) {
            if (V[i] != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * @return the values vector
     */
    public int[] getV() {
        return V;
    }

    /**
     * @return the levels vector
     */
    public int[] getL() {
        return L;
    }

    /**
     * @return the decision
     */
    public int getDecision() {
        return decision;
    }
}
