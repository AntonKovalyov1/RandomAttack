package randomattack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Parser {
    
    private int n;
    private int r;
    private int x;
    private int[] vals;
    
    public void parse(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    new File(path)));
            n = getNumber(br);
            r = getNumber(br);
            x = getNumber(br);
            vals = getIntArray(br, n);
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private String readLine(final BufferedReader br) {
        String s = null;
        try {
        s = br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return s;
    }
    
    private int getNumber(final BufferedReader br) {
        return Integer.parseInt(readLine(br));
    }
    
    private int[] getIntArray(final BufferedReader br, final int n) {
        int[] array = new int[n];
        String s = readLine(br);
        String[] tokens = s.trim().split("\\s+");
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(tokens[i]);
        }
        return array;
    }
    
    /**
     * @return the number of processes
     */
    public int getN() {
        return n;
    }

    /**
     * @return the number of rounds
     */
    public int getR() {
        return r;
    }

    /**
     * @return the message number that will be dropped
     */
    public int getX() {
        return x;
    }

    /**
     * @return the decision value of all processes
     */
    public int[] getVals() {
        return vals;
    }
}
