package ru.course.cursesortoutbiphasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHelper {
    //Класс с произвольным доступом к файлу. Позволяет читать и писать в любое место, а не только в конец
    private RandomAccessFile raf;
    //Конструктор, который принимает имя файла
    public FileHelper(String nameFile) {
        try {
            //Создаём объект, указываем ему файл с которым он работает и режим "чтение и запись". r - read, w - write
            raf = new RandomAccessFile(new File(nameFile), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //Метод для чтения числа для метода поглощения. Принимает позицию числа в файле
    public int readNumber(long pos) {
        try {
            //если позиция меньше нуля или превышает число чисел в файле, то возвращаем огромное число (pos*5L) - костыль, помогающий представлять числа как ячейки массива. В числе 4 символа цифр и пробел = 5 символов. L - приведение числа к long
            if (pos < 0 || raf.length() - 1 < pos * 5L) {
                return 10000;
            }
            //Перемещаем курсор к позиции
            raf.seek(pos * 5L);
            //Создаём продвинутый объект для хранения строк
            StringBuilder buffer = new StringBuilder();
            //переменная для очередного символа
            int ch;
            //Читаем символ и записываем в переменную, после чего сразу проверяем его на то пробел ли это
            while ((ch = raf.read()) != 32) {
                //Если не пробел, то добавляем символ к строке
                buffer.append((char) ch);
            }
            //Если пробел, то приводим к числу и возвращаем
            return Integer.parseInt(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Если случилась какая-то жесть с файлом во время выполнения, то ломаем алгоритм и возвращаем -1
        return -1;
    }
    //Метод для записи числа в файл для метода поглощения. Принимает позицию и само число
    public void writeNumber(long pos, int num){
        try {
            //если позиция меньше нуля или превышает число чисел в файле, то прерываем запись. В целом при полностью правильном алгоритме никогда не сработает, но если что-то пойдёт не так в алгоритме, то если это убрать, то программа сломается с концами
            if (pos < 0 || raf.length() - 1 < pos * 5L) {
                return;
            }
            //Перемещаемся на позицию
            raf.seek(pos * 5L);
            //Записываем, сохраняя 4 значный формат числа
            raf.writeBytes(String.format("%04d", num) +" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Метод для освобождения ресурсов
    public void close() {
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
