package computador;

import java.util.Arrays;

public abstract class Memory {
    public int[] internalMemory;
    public int tamanho;

    abstract public void write(int addr, int data) throws Exception;
    abstract public int read(int addr) throws Exception;

    @Override
    public String toString() {
        return "{" + Arrays.toString(internalMemory) + '}';
    }

    protected void validarAcesso(int addr) throws Exception {
        if (addr < 0 || addr > tamanho - 1) throw new Exception("Endereço invalido " + addr);
    }

}
