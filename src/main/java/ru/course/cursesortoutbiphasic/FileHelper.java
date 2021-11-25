package ru.course.cursesortoutbiphasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHelper {
    private RandomAccessFile raf;

    public FileHelper(String nameFile) {
        try {
            raf = new RandomAccessFile(new File(nameFile), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int readNumber(long pos) {
        try {
            if (pos < 0 || raf.length() - 1 < pos * 5L) {
                return 10000;
            }
            raf.seek(pos * 5L);
            StringBuilder buffer = new StringBuilder();
            int ch;
            while ((ch = raf.read()) != 32 && raf.getFilePointer() != raf.length()) {
                buffer.append((char) ch);
            }
            return Integer.parseInt(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void writeNumber(long pos, int num){
        try {
            if (pos < 0 || raf.length() - 1 < pos * 5L) {
                return;
            }
            raf.seek(pos * 5L);
            raf.writeBytes(String.format("%04d", num) +" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
