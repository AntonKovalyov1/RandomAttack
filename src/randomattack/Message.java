package randomattack;

public class Message {
    
    public final int key;
    public final int[] V;
    public final int[] L;
    
    public Message(final int key, final int n) {
        this.key = key;
        this.V = new int[n];
        this.L = new int[n];
    }
    
    public void setV(int[] newV) {
        System.arraycopy(newV, 0, V, 0, V.length);
    }
    
    public void setL(int[] newL) {
        System.arraycopy(newL, 0, L, 0, L.length);
    }
}
