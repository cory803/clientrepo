package org.chaos.client;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStream extends FilterInputStream {

    private final double maxbytes;
    private long current = 0;

    public ProgressInputStream(InputStream in, long bytestoexpect) {
        super(in);
        maxbytes = (double)bytestoexpect;
    }

    /**
     * return a value between 0.0 and 1.0 to represent the progress.
     * should do some division-by-zero checking here too.
     */
    public double getProgress() {
        return current / maxbytes;
    }

    @Override
    public int read() throws IOException {
        final int ret = super.read();
        if (ret >= 0) {
            current++;
        }
        return ret;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        final int ret = super.read(b, off, len);
        current += ret;
        return ret;
    }

    @Override
    public int read(byte[] b) throws IOException {
        // TODO Auto-generated method stub
        final int ret = super.read(b);
        current += ret;
        return ret;
    }

    @Override
    public long skip(long n) throws IOException {
        final long ret = super.skip(n);
        current += ret;
        return ret;
    }

}