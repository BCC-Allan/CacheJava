package computadorCacheline;

public class Main {
    public static void main(String[] args) throws Exception {
        IO es = new IO(System.out);
        RAM ram = new RAM(24); // 16M
        Cache cache = new Cache(8192, 64, ram);
        CPU cpu = new CPU(cache, es);

//        ex02(cpu, ram);
        ex03(cache);
    }

    public static void ex02(CPU cpu, RAM ram)  {
        try {
            final int start = 82000;
            ram.write(start, 1000);
            ram.write(start+1, 1023);
            cpu.run(start);

        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }
    }

    public static void ex03(Cache cache) {
        try {
            System.out.println();
            System.out.println("cache.write(16640, 1024)");
            cache.write(16640, 1024); // miss
            System.out.println();
            System.out.println("cache.read(16640) = " + cache.read(16640));
            System.out.println();
            System.out.println("cache.read(49408) = " + cache.read(49408));
            System.out.println();
            System.out.println("cache.read(16640) = " + cache.read(16640));



        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }

    }
}
