package computadorCacheline;

import java.util.Arrays;

public class RAM extends Memory {

    public RAM(int k) {
        this.tamanho = (int) Math.pow(2, k);
        internalMemory = new int[tamanho];
    }

    public void write(int addr, int data) throws Exception {
        validarAcesso(addr);
        internalMemory[addr] = data;
    }

    public int read(int addr) throws Exception {
        validarAcesso(addr);
        return internalMemory[addr];
    }

}
