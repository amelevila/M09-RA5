package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;

public class TextXifrat {
    final private byte[] bytes;

    public TextXifrat(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return new String(this.bytes, StandardCharsets.UTF_8);
    }

    public byte[] getBytes() {
        return this.bytes;
    }
}