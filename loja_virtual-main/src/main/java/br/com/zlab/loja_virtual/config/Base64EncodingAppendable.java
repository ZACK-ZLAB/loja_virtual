package br.com.zlab.loja_virtual.config;

import java.io.IOException;

public class Base64EncodingAppendable implements Appendable {
    private StringBuilder sb;

    public Base64EncodingAppendable(StringBuilder sb) {
        this.sb = sb;
    }

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        sb.append(csq);
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        sb.append(csq, start, end);
        return this;
    }

    @Override
    public Appendable append(char c) throws IOException {
        sb.append(c);
        return this;
    }
}
