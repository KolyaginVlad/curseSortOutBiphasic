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

    public void swap(long pos1, long pos2) {
        try {
            raf.seek(pos1 * 5L);
            char[] first = new char[4];
            for (int i = 0; i < 4; i++) {
                first[i] = (char) raf.read();
            }
            raf.seek(pos2 * 5L);
            char[] second = new char[4];
            for (int i = 0; i < 4; i++) {
                second[i] = (char) raf.read();
            }
            raf.seek(pos2 * 5L);
            raf.writeBytes(first[0] + "" + first[1] + "" + first[2] + "" + first[3]);
            raf.seek(pos1 * 5L);
            raf.writeBytes(second[0] + "" + second[1] + "" + second[2] + "" + second[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readNumber(long pos) {
        try {
            if (pos < 0 || raf.length() - 1 < pos * 5L) {
                return -1;
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
