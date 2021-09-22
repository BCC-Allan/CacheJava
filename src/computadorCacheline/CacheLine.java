package computadorCacheline;

public class CacheLine extends Memory {
    private int tag; // t
    private boolean vazia = true;
    private boolean ocorreuModificacoes = false;

    public CacheLine(int k) {
        this.tamanho = k; // K palavras
        internalMemory = new int[tamanho];
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean foiModificada() {
        return this.ocorreuModificacoes;
    }

    public boolean estaNaCache(int t) {
        if (vazia) {
            System.out.println("Cache vazia");
            return false;
        }
        System.out.printf("Comparando tag interna %d com tag %d%n", this.tag, t);
        return t == this.tag;
    }

    public void copiarParaRam(int enderecoInicial, RAM ram) throws Exception {
        for (int i = 0; i < tamanho; i++) {
            int dadoNaCache = read(i);
            ram.write(enderecoInicial + i, dadoNaCache);
        }
    }

    public void copiarDaRam(int enderecoInicial, RAM ram) throws Exception {
        for (int i = 0; i < tamanho; i++) {
            int dadoNaRam = ram.read(enderecoInicial + i);
            write(i, dadoNaRam);
        }
        ocorreuModificacoes = false;
    }

    @Override
    public void write(int w, int data) throws Exception {
        internalMemory[w] = data;
        vazia = false;
        ocorreuModificacoes = true;
    }

    @Override
    public int read(int w) throws Exception {
        return internalMemory[w];
    }
}
