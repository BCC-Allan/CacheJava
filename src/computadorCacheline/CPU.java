package computadorCacheline;

public class CPU {

    public IO io;
    public Memorizavel memory;
    public int PC;

    public CPU(Memorizavel memory, IO io) {
        this.io = io;
        this.memory = memory;
        PC = 0;
    }

    public void run(int endereco) throws Exception {
        PC = endereco;
        int valueA = memory.read(PC);
        System.out.println("\nLendo valor");
        System.out.println("valueA = " + valueA);
        System.out.println();


        int valueB = memory.read(PC + 1);

        System.out.println("\nLendo valor");
        System.out.println("valueB = " + valueB);
        System.out.println();

        int initialAddress = valueA;
        int finalAddress = valueB;

        for (int i = initialAddress; i < finalAddress; i++) {

            int value = i - initialAddress + 1;
            memory.write(i, value);
            io.output("ESCREVENDO: " + value + "\n");
        }
        PC++;
    }
}
