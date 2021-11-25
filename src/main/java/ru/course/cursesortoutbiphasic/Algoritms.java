package ru.course.cursesortoutbiphasic;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Algoritms {
    static Random random = new Random();
    final int n = 10;

    private long simpleSort(long count) {
        long time = System.currentTimeMillis();
        try {
            for (long i = 1; i < count; i *= 2) {
                simpleSortSplit(i);
                simpleSortCombine(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - time;
    }

    private long naturalSort() {
        long time = System.currentTimeMillis();
        try {
            do {
                naturalSortSplit();
            } while (naturalSortCombine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - time;
    }

    private boolean naturalSortCombine() throws IOException {
        PrintWriter writerToA = new PrintWriter(new FileWriter("a.txt"));
        InputStream readerB = new FileInputStream("b.txt");
        InputStream readerC = new FileInputStream("c.txt");
        int first = getNumber(readerB);
        int second = getNumber(readerC);
        int counter = 0;
        while (first != -1 || second != -1) {
            while (first != -1 && second != -1 && first != -2 && second != -2) {
                if (first < second) {
                    writerToA.write(first + " ");
                    first = getNumber(readerB);
                } else {
                    writerToA.write(second + " ");
                    second = getNumber(readerC);
                }
            }
            counter++;
            while (first != -1 && first != -2) {
                writerToA.write(first + " ");

                first = getNumber(readerB);
            }
            while (second != -1 && second != -2) {
                writerToA.write(second + " ");

                second = getNumber(readerC);
            }
            first = getNumber(readerB);
            second = getNumber(readerC);
        }
        writerToA.flush();
        readerB.close();
        readerC.close();
        return counter > 1;
    }

    private void naturalSortSplit() throws IOException {
        PrintWriter writerToB = new PrintWriter(new FileWriter("b.txt"));
        PrintWriter writerToC = new PrintWriter(new FileWriter("c.txt"));
        InputStream readerA = new FileInputStream("a.txt");
        int num = getNumber(readerA);
        int dir = 0;
        int last = -1;
        while (num != -1) {
            if (num < last) {
                if (dir == 0) {
                    dir = 1;
                    writerToB.write("-2 ");
                    writerToC.write(num + " ");
                } else {
                    dir = 0;
                    writerToC.write("-2 ");
                    writerToB.write(num + " ");
                }
            } else {
                if (dir == 0) {
                    writerToB.write(num + " ");
                } else {
                    writerToC.write(num + " ");
                }
            }
            last = num;
            num = getNumber(readerA);
        }
        if (dir == 0) {
            writerToB.write("-2 ");
        } else {
            writerToC.write("-2 ");
        }
        writerToB.flush();
        writerToB.close();
        writerToC.flush();
        writerToC.close();
        readerA.close();
    }

    private long absorbSort(long count) {
        long time = System.currentTimeMillis();
        FileHelper fileHelper = new FileHelper("a.txt");
        int[] buffer = new int[n];
        for (int i = 0; i < n; i++) {
            buffer[i] = fileHelper.readNumber(count - 1 - i);
        }
        Arrays.sort(buffer);
        for (int i = 0; i < n; i++) {
            fileHelper.writeNumber(count - n + i, buffer[i]);
        }
        int x = 1;
        while (x <= count / n - 1) {
            for (int i = 0; i < n; i++) {
                buffer[i] = fileHelper.readNumber(count - 1 - (long) n * x - i);
            }
            Arrays.sort(buffer);
            int kNums = 0;
            int kFiles = 0;
            for (int i = 0; i < n + n * x; i++) {
                if (buffer[kNums] <= fileHelper.readNumber(count - (long) n * x + kFiles)) {
                    fileHelper.writeNumber(count - (long) n * (x + 1) + i, buffer[kNums]);
                    kNums++;
                    if (kNums == n) break;
                } else {
                    fileHelper.writeNumber(count - (long) n * (x + 1) + i, fileHelper.readNumber(count - (long) n * x + kFiles));
                    kFiles++;
                }
            }
            x++;
        }
        int lost = (int) (count % n);

        for (int i = 0; i < lost; i++) {
            buffer[i] = fileHelper.readNumber(i);
        }
        for (int i = lost; i < n; i++) {
            buffer[i] = 10000;
        }
        Arrays.sort(buffer);
        int kNums = 0;
        int kFiles = 0;
        for (int i = 0; i < count - 1; i++) {
            if (buffer[kNums] <= fileHelper.readNumber(lost + kFiles)) {
                fileHelper.writeNumber(i, buffer[kNums]);
                kNums++;
                if (kNums == lost) break;
            } else {
                fileHelper.writeNumber(i, fileHelper.readNumber(lost + kFiles));
                kFiles++;
            }
        }
        fileHelper.close();
        return System.currentTimeMillis() - time;
    }


    private void generateFile(long count) {
        try (FileWriter writer = new FileWriter("tmp.txt")) {
            for (int i = 0; i < count; i++) {
                writer.write(String.format("%04d", random.nextInt(10000)) + " ");
            }
            writer.flush();
            cloneFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Base time(long count) {
        try {
            generateFile(count);
            long simpleTime = simpleSort(count);
            cloneFile();
            long naturalTime = naturalSort();
            cloneFile();
            long absorbTime = absorbSort(count);
            //Создаём строчку и заполняем её данными
            return new Base(count, simpleTime, naturalTime, absorbTime);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void simpleSortSplit(long len) throws IOException {
        PrintWriter writerToB = new PrintWriter(new FileWriter("b.txt"));
        PrintWriter writerToC = new PrintWriter(new FileWriter("c.txt"));
        InputStream readerA = new FileInputStream("a.txt");
        long curLen = len;
        int dir = 0;
        while (readerA.available() != 0) {
            int elem = getNumber(readerA);
            if (curLen > 0) {
                curLen--;
            } else {
                dir = dir == 0 ? 1 : 0;
                curLen = len - 1;
            }
            if (dir == 0) {
                writerToB.write(elem + " ");
            } else {
                writerToC.write(elem + " ");
            }
        }
        writerToB.flush();
        writerToC.flush();
        writerToB.close();
        writerToC.close();
        readerA.close();
    }

    private void cloneFile() throws IOException {
        PrintWriter writerToA = new PrintWriter(new FileWriter("a.txt"));
        InputStream reader = new FileInputStream("tmp.txt");
        int num = getNumber(reader);
        while (num != -1) {
            writerToA.write(String.format("%04d", num) + " ");
            num = getNumber(reader);
        }
        writerToA.flush();
        writerToA.close();
        reader.close();
    }

    private void simpleSortCombine(long len) throws IOException {
        PrintWriter writerToA = new PrintWriter(new FileWriter("a.txt"));
        InputStream readerB = new FileInputStream("b.txt");
        InputStream readerC = new FileInputStream("c.txt");
        int first = getNumber(readerB);
        int second = getNumber(readerC);
        while (first != -1 || second != -1) {
            long curB = len;
            long curC = len;
            while (curB != 0 && curC != 0 && first != -1 && second != -1) {
                if (first < second) {
                    writerToA.write(first + " ");
                    curB--;
                    first = getNumber(readerB);
                } else {
                    writerToA.write(second + " ");
                    curC--;
                    second = getNumber(readerC);
                }
            }
            while (curB != 0 && first != -1) {
                writerToA.write(first + " ");
                curB--;
                first = getNumber(readerB);
            }
            while (curC != 0 && second != -1) {
                writerToA.write(second + " ");
                curC--;
                second = getNumber(readerC);
            }
        }
        writerToA.flush();
        readerB.close();
        readerC.close();
    }


    private int getNumber(InputStream reader) throws IOException {
        int character;
        StringBuilder currentElem = new StringBuilder();
        while (reader.available() != 0) {
            character = reader.read();
            if (character != 32) {
                currentElem.append((char) character);
            } else {
                return Integer.parseInt(currentElem.toString());
            }
        }
        return -1;
    }


}
