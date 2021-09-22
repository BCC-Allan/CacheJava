package computadorCacheline;

import static computadorCacheline.MemoryUtils.contaBits;

// cache com cache lines
public class Cache implements Memorizavel {

    CacheLine[] lines;
    RAM memoriaRam;
    int palavrasNaCacheLineK; // 64
    int qtdDeCacheLinesM;

    // k = qtd palavras na cache line
    public Cache(int totalPalavras, int k, RAM memoriaRam) {
        this.palavrasNaCacheLineK = k;
        this.qtdDeCacheLinesM = calcQtdLines(totalPalavras, k);
        this.lines = criarCacheLines(qtdDeCacheLinesM, k);
        this.memoriaRam = memoriaRam;
    }

    private int calcQtdLines(int totalPalavras, int k) {
        return totalPalavras / k;
    }

    private CacheLine[] criarCacheLines(int qtd, int k) {
        CacheLine[] cacheLines = new CacheLine[qtd];
        for (int i = 0; i < qtd; i++) {
            cacheLines[i] = new CacheLine(k);
        }
        return cacheLines;
    }

    public void write(int addr, int data) throws Exception {
        int w = pegaPosicaoCacheLineW(addr);
        CacheLine line = hitOrMiss(addr, w);
        line.write(w, data);
    }

    public int read(int addr) throws Exception {
        int w = pegaPosicaoCacheLineW(addr);
        CacheLine line = hitOrMiss(addr, w);
        return line.read(w);
    }

    private CacheLine hitOrMiss(int addr, int w) {
        int r = pegaIndiceCacheLineR(addr);
        int t = pegaTagDaCacheLineT(addr);

        CacheLine line = lines[r];
        if (line.estaNaCache(t)) {
            System.out.println("Cache Hit");
            System.out.println(addr);
        } else {
            System.out.println("Cache Miss");
            System.out.println(addr);
            tratarCacheMiss(line, t, r);
        }

        return line;
    }

    private void tratarCacheMiss(CacheLine line, int t, int r) {

        try {
            if (line.foiModificada()) {
                System.out.println("modificada");
                int enderecoInicialRam = pegaEnderecoComecoBloco(line.getTag(), r);
                line.copiarParaRam(enderecoInicialRam, memoriaRam);
            }

            int novoEnderecoInicialRam = pegaEnderecoComecoBloco(t, r);
            line.copiarDaRam(novoEnderecoInicialRam, memoriaRam);
            line.setTag(t);
            System.out.println("t = " + t);

        } catch (Exception e) {
            System.out.println("erro em tratarCacheMiss");
        }
    }

    private int pegaPosicaoCacheLineW(int endereco) {
        int mascara = mascaraDoW();
        return endereco & mascara;
    }

    private int pegaIndiceCacheLineR(int endereco) {
        // quantidade de bits que ja foram extraidos pelo w
        int qtdZerosFinal = contaBits(mascaraDoW());// 6
        int mascara = mascaraDoR();
        int mascaraComPadding = mascara << qtdZerosFinal; // 1111111000000
        int r = endereco & mascaraComPadding;
        return r >> qtdZerosFinal; // tirando padding do r
    }

    private int pegaTagDaCacheLineT(int endereco) {
        // quantidade de bits que ja foram extraidos pelo w e pelo r
        int qtdBitsZeroFinal = contaBits(mascaraDoW()) +
                contaBits(mascaraDoR()); // 13 bits
        int qtdBitsRemanecentesEsquerda = contaBits(endereco) - qtdBitsZeroFinal;

        int mascara = (1 << qtdBitsRemanecentesEsquerda) - 1; // 11111111111
        int mascaraComPadding = mascara << qtdBitsZeroFinal; // 111111111110000000000000
        int t = endereco & mascaraComPadding;

        return t >> qtdBitsZeroFinal;// tirando padding do t
    }

    private int mascaraDoW() {
        return palavrasNaCacheLineK - 1;  // 111111
    }

    private int mascaraDoR() {
        return qtdDeCacheLinesM - 1; // 1111111
    }


    private int pegaEnderecoComecoBloco(int t, int r) {
        int tamanhoR = contaBits(mascaraDoR()); // 4
        int tamanhoW = contaBits(mascaraDoW()); // 2 elevado a n

        return (((t << tamanhoR) | r) << tamanhoW);
    }
}
