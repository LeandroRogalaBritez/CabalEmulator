package cabal.captcha;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CaptchaReader {
    private byte[] data;
    private int dataLength;
    private String name;

    public String getName() {
        return name;
    }

    public void readCaptchaFile(String fileName) throws IOException {
        File file = new File(fileName);
        dataLength = (int) file.length();
        data = new byte[dataLength];

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int bytesRead = fileInputStream.read(data);
            if (bytesRead != dataLength) {
                throw new IOException("Couldn't read the entire file: " + fileName);
            }
        }
    }

    public byte[] getData() {
        return data;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setName(String name) {
        this.name = name;
    }
}
