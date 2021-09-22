package computador;

public class Cache extends Memory {
    private RAM ram;
    private int headRamAddress;
    private boolean isEmpty;

    public Cache(int k, RAM ram) {
        this.tamanho = k;
        this.ram = ram;
        internalMemory = new int[tamanho];
        isEmpty = true;
    }

    @Override
    public int read(int ramAddress) throws Exception {
        if (isAddressInCache(ramAddress)) {
            return getItemInCacheByRamAddress(ramAddress);
        }
        copyCacheToRam();
        copyRamToCache(ramAddress);
        return getItemInCacheByRamAddress(ramAddress);
    }

    @Override
    public void write(int ramAddress, int data) throws Exception {
        if (isAddressInCache(ramAddress)) {
            internalMemory[ramAddressToCacheAddress(ramAddress)] = data;
            return;
        }
        copyCacheToRam();
        copyRamToCache(ramAddress);

        internalMemory[ramAddressToCacheAddress(ramAddress)] = data;
    }

    public int finalRamAddress() {
        return headRamAddress + tamanho -1;
    }

    public void copyCacheToRam() throws Exception {
        if (isEmpty) return;
        for (int i = headRamAddress; i < finalRamAddress(); i++) {
            int data = internalMemory[ramAddressToCacheAddress(i)];
            ram.write(i, data);
        }
    }

    public void copyRamToCache(int newHeadRamAddress) throws Exception {
        headRamAddress = newHeadRamAddress;
        for (int i = newHeadRamAddress; i < newHeadRamAddress + tamanho; i++) {
            try {
                internalMemory[ramAddressToCacheAddress(i)] = ram.read(i);
            } catch (Exception e) {
                internalMemory[ramAddressToCacheAddress(i)] = 0;
            }
        }
        isEmpty = false;

    }

    public boolean isAddressInCache(int address) {
        if (isEmpty) return false;
        return address >= headRamAddress && address <= finalRamAddress();
    }

    public int getItemInCacheByRamAddress(int ramAddress) {
        return internalMemory[ramAddressToCacheAddress(ramAddress)];
    }

    private int ramAddressToCacheAddress(int ramAddress) {
        return ramAddress - headRamAddress;
    }

}
