public class OperacoesParaAcharMemoria {

    static int capacidadeTotalCache = 8 * 1024;
    static int capacidadeTotalRam = 16 * 1024 * 1024;
    static int palavrasNaCacheLineK = 64;
    static int numeroDeCacheLines_m = capacidadeTotalCache / palavrasNaCacheLineK;

    public static void main(String[] args) {
        int enderecoPedidoX = 10560325;
        mostraInformacoesSobreEndereco(enderecoPedidoX);
    }

    public static void mostraInformacoesSobreEndereco(int endereco) {
        int w = pegaPosicaoCacheLineW(endereco);
        int r = pegaIndiceCacheLineR(endereco);
        int t = pegaTagDaCacheLineT(endereco);
        int enderecoComecoBlocoRam = pegaEnderecoComecoBloco(endereco, t, r);
        int enderecoFinalBlocoRam = enderecoComecoBlocoRam + palavrasNaCacheLineK - 1;

        System.out.println("Endereco em binario = " + Integer.toBinaryString(endereco));
        System.out.println("w em binario " + Integer.toBinaryString(w));
        System.out.println("r em binario " + Integer.toBinaryString(r));
        System.out.println("t em binario " + Integer.toBinaryString(t));
        System.out.println("endereco inicial RAM em binario " + Integer.toBinaryString(enderecoComecoBlocoRam));
        System.out.println("endereco final RAM em binario " + Integer.toBinaryString(enderecoFinalBlocoRam));
    }

    public static int pegaPosicaoCacheLineW(int endereco) {
        int mascara = palavrasNaCacheLineK - 1; // 111111
        return endereco & mascara;
    }

    public static int pegaIndiceCacheLineR(int endereco) {
        // quantidade de bits que ja foram extraidos pelo w
        int qtdZerosFinal = contaBits(palavrasNaCacheLineK - 1);// 6
        int mascara = numeroDeCacheLines_m - 1;// 1111111
        int mascaraComPadding = mascara << qtdZerosFinal; // 1111111000000
        int r = endereco & mascaraComPadding;
        return r >> qtdZerosFinal; // tirando padding do r
    }

    public static int pegaTagDaCacheLineT(int endereco) {
        // quantidade de bits que ja foram extraidos pelo w e pelo r
        int qtdBitsZeroFinal = contaBits(palavrasNaCacheLineK - 1) + contaBits(numeroDeCacheLines_m - 1); // 13 bits
        int qtdBitsRemanecentesEsquerda = contaBits(endereco) - qtdBitsZeroFinal;

        int mascara = (1 << qtdBitsRemanecentesEsquerda) - 1; // 11111111111
        int mascaraComPadding = mascara << qtdBitsZeroFinal; // 111111111110000000000000
        int t = endereco & mascaraComPadding;

        return t >> qtdBitsZeroFinal;// tirando padding do t
    }

    public static int pegaEnderecoComecoBloco(int endereco, int t, int r) {
        int tamanhoEndereco = contaBits(capacidadeTotalRam) - 1; // 23
        int tamanhoR = contaBits(r); // 4
        // novo t = 10100001001 1101
        int tComR = (t << tamanhoR) | r; //10100001001110100000000000 + 64 -1
        int qtdBitsParaCompletar = tamanhoEndereco - contaBits(tComR);

        return tComR << qtdBitsParaCompletar;
    }

    private static int contaBits(int binario) {
        // log 2 10
        // 2^x = 10 + 1
        return (int) (Math.log(binario) / Math.log(2) + 1);
    }

}
