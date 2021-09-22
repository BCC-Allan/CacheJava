package computador;

public class CPU {

    public IO io;
    public Memory memory;
    public int PC;

    public CPU(Memory memory, IO io) {
        this.io = io;
        this.memory = memory;
        PC = 0;
    }

    public void run(int endereco) throws Exception {
        PC = endereco;
        int valueA = memory.read(PC);
        int valueB = memory.read(PC + 1);

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
