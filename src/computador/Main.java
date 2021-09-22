package computador;

public class Main {
    public static void main(String[] args) throws Exception {
        IO es = new IO(System.out);
        RAM ram = new RAM(7);
        Cache cache = new Cache(8, ram);
        CPU cpu = new CPU(cache, es);

//        ex01(cpu, ram);
        ex02(cpu, ram);
    }

    public static void ex01(CPU cpu, RAM ram) throws Exception {
        ram.write(0, 120);
        ram.write(1, 127);

//        System.out.println(cpu.memory.toString());
        cpu.run(0);
//        System.out.println(cpu.memory.toString());
    }

    public static void ex02(CPU cpu, RAM ram)  {
        try {
            final int start = 10;
            ram.write(start, 118);
            ram.write(start+1, 130);
            cpu.run(start);
//            System.out.println(cpu.memory);
        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }
    }

}
