package computadorCacheline;

public interface Memorizavel {
    void write(int addr, int data) throws Exception;
    int read(int addr) throws Exception;
}
