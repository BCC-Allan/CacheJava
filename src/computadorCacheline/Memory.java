package computadorCacheline;

import java.util.Arrays;

public abstract class Memory implements Memorizavel {
    public int[] internalMemory;
    public int tamanho;

    @Override
    public String toString() {
        return "{" + Arrays.toString(internalMemory) + '}';
    }

    protected void validarAcesso(int addr) throws Exception {
        if (addr < 0 || addr > tamanho - 1) throw new Exception("Endereço invalido " + addr);
    }

}
