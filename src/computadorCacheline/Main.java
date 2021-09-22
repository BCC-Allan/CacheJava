package computadorCacheline;

public class Main {
    public static void main(String[] args) throws Exception {
        IO es = new IO(System.out);
        RAM ram = new RAM(24); // 16M
        Cache cache = new Cache(8192, 64, ram);
        CPU cpu = new CPU(cache, es);

        ex02(cpu, ram);
    }

    public static void ex01(CPU cpu, RAM ram) throws Exception {
        ram.write(0, 120);
        ram.write(1, 127);

        cpu.run(0);
    }

    public static void ex02(CPU cpu, RAM ram)  {
        try {
            final int start = 82000;
            ram.write(start, 1000);
            ram.write(start+1, 1023);
            System.out.println("ram.read(start) = " + ram.read(start));
            cpu.run(start);
            System.out.println("ram.read(start) = " + ram.read(start));

        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }
    }
}
