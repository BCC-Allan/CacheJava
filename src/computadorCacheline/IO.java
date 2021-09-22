package computadorCacheline;

import java.io.IOException;
import java.io.OutputStream;

public class IO {

    private final OutputStream out;

    public IO(OutputStream output) {
        this.out = output;
    }

    public void output(String text) throws IOException {
        out.write(text.getBytes());
        out.flush();
    }
}
