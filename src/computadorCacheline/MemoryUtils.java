package computadorCacheline;

public class MemoryUtils {
    public static int contaBits(int binario) {
        return (int) (Math.log(binario) / Math.log(2) + 1);
    }
}
