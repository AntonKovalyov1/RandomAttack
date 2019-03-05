package randomattack;

public class Message {
    
    public static final int UNDEFINED = -1;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    
    private final int key;
    private final int[] V;
    private final int[] L;
    
    public Message(final int key, final int n) {
        this.key = key;
        this.V = new int[n];
        this.L = new int[n];
    }
    
    public void init(int id, int val) {
        for (int i = 0; i < V.length; i++) {
            V[i] = UNDEFINED;
            L[i] = UNDEFINED;
        }
        V[id] = val;
        L[id] = 0;
    }
    
    public void setV(int[] newV) {
        System.arraycopy(newV, 0, V, 0, V.length);
    }
    
    public void setL(int[] newL) {
        System.arraycopy(newL, 0, L, 0, L.length);
    }
}
