package ru.course.cursesortoutbiphasic;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Algoritms {
    //Объект для создания рандомных чисел
    static Random random = new Random();
    //Размер буфера в методе поглощения
    final int n = 10;
    //метод, считающий время методом простого слияния
    private long simpleSort(long count) {
        //Записываем время начала
        long time = System.currentTimeMillis();
        //Используем try catch для отслеживания проблем с чтением и записью
        try {
            //i - количество элементов, котореы должны быть переведены в другой файл вместе
            //1, 2, 4, 8, 16 ...
            //Их количество не должно превышать количество элементов
            for (long i = 1; i < count; i *= 2) {
                //Разделяем файл a.txt на два файла b.txt и c.txt
                simpleSortSplit(i);
                //Сливаем файлы b.txt и c.txt в файл a.txt
                simpleSortCombine(i);
            }
        } catch (IOException e) {
            //Обрабатываем ошибки если они есть - выводим в консоль информацию о проблеме
            e.printStackTrace();
        }
        //возвращаем разницу во времени с начала сортировке и до нынешнего момента
        return System.currentTimeMillis() - time;
    }
    //метод, считающий время сортировки естественным слиянием
    private long naturalSort() {
        //Записываем время начала
        long time = System.currentTimeMillis();
        //Используем try catch для отслеживания проблем с чтением и записью
        try {
            //Цикл, в котором сначала значения разделяются на два файла, а потом обратно соединяет, возвращая нужно ли продолжать сортировку или завершить
            do {
                //разделение из a в b и c
                naturalSortSplit();
                //проверка условие и соединение обратно в a
            } while (naturalSortCombine());
        } catch (IOException e) {
            //Обрабатываем ошибки если они есть - выводим в консоль информацию о проблеме
            e.printStackTrace();
        }
        //возвращаем разницу во времени с начала сортировке и до нынешнего момента
        return System.currentTimeMillis() - time;
    }
    //Метод объединения для естественного слияния
    private boolean naturalSortCombine() throws IOException {
        //Создаём класс для записи в скобках имя файла
        PrintWriter writerToA = new PrintWriter(new FileWriter("a.txt"));
        //Создаём классы для чтения
        InputStream readerB = new FileInputStream("b.txt");
        InputStream readerC = new FileInputStream("c.txt");
        //Читаем первое число из каждого файла
        int first = getNumber(readerB);
        int second = getNumber(readerC);
        //счётчик того, сколько цепочек будет в файла после объединения
        int counter = 0;
        //Пока хотя в одном файле есть что читать, то продолжаем крутиться в цикле (-1 возвращется если файл закончился)
        while (first != -1 || second != -1) {
            //Пока в обоих файлах есть что читать и не встретилось окончание отсортированной цепочки, то продолжаем крутиться в цикле (-2 - знак окончания цепочки)
            while (first != -1 && second != -1 && first != -2 && second != -2) {
                //Сравниваем 2 числа из разных файлов
                if (first < second) {
                    //Записываем число из первого файла если оно меньше
                    writerToA.write(first + " ");
                    //Читаем новое число из первого файла
                    first = getNumber(readerB);
                } else {
                    //Записываем число из второго файла если оно меньше
                    writerToA.write(second + " ");
                    //Читаем новое число из второго файла
                    second = getNumber(readerC);
                }
            }
            //Если вышли из цикла значит либо встретилось окончание цепочки либо закончился файл. Считаем, что в "а" сформирована цепочка и добавляем единицу к счётчику цепочек
            counter++;
            //Дописываем числа из первого файла, если они остались в цепочке после сравнения со вторым файлом
            while (first != -1 && first != -2) {
                writerToA.write(first + " ");

                first = getNumber(readerB);
            }
            //Дописываем числа из второго файла, если они остались в цепочке после сравнения с числами из цепочки первого файла
            while (second != -1 && second != -2) {
                writerToA.write(second + " ");

                second = getNumber(readerC);
            }
            //Читаем новые числа т.к. сейчас в переменных хранятся либо -1 либо -2, что означают конец файла и конец цепочки
            first = getNumber(readerB);
            second = getNumber(readerC);
        }
        //Даём команду классу для записи к добавлению текста в файл (до этого хранит текст в своём внутреннем буфере и если этого не сделать, то текст в файле не появится)
        writerToA.flush();
        //Закрываем потоки чтения и записи, чтобы освободить ресурсы и не вызвать после множества повторений краш из-за недостатка памяти
        writerToA.close();
        readerB.close();
        readerC.close();
        //Если цепочек больше чем 1, то продолжаем цикл
        return counter > 1;
    }
    //Метод для разбиения данных на два файла для сортировки естественным слиянием
    private void naturalSortSplit() throws IOException {
        //Создаём классы для записи
        PrintWriter writerToB = new PrintWriter(new FileWriter("b.txt"));
        PrintWriter writerToC = new PrintWriter(new FileWriter("c.txt"));
        //Создаём класс для чтения
        InputStream readerA = new FileInputStream("a.txt");
        //Читаем первое число
        int num = getNumber(readerA);
        //Устанавливаем направление записи. Показывает куда нужно записывать: в первый файл или во второй
        int dir = 0;
        //Сохраняем предыдущее число. Ставим -1 чтобы первое число точно ушло в первый файл и не создало лишнего завершения цепочки.
        int last = -1;
        //Крутимся в цикле пока есть что читать (-1 если файл закончился см. getNumber)
        while (num != -1) {
            //Сравнниваем текущее число с предыдущим
            if (num < last) {
                //Если текущее больше предыдущего, то цепочка отсортированна (1, 3, где 1 - предыдущее, 3 - текущее)
                //Если нет, то цепочка не отсортирована и мы попадаем в этот if
                //Выбираем из направления в какой файл писать
                if (dir == 0) {
                    //Меняем направление т.к. цепочка обрывается
                    dir = 1;
                    //Ставим символ конца цепочки в файл, который мы писали
                    writerToB.write("-2 ");
                    //Записываем в другой файл число новой цепочки
                    writerToC.write(num + " ");
                } else {
                    //Меняем направление т.к. цепочка обрывается
                    dir = 0;
                    //Ставим символ конца цепочки в файл, который мы писали
                    writerToC.write("-2 ");
                    //Записываем в другой файл число новой цепочки
                    writerToB.write(num + " ");
                }
            } else {
                //Сюда заходим если цепочка продолжается
                //В зависимости от направления пишем в файл прочитанное число
                if (dir == 0) {
                    writerToB.write(num + " ");
                } else {
                    writerToC.write(num + " ");
                }
            }
            //Устанавливаем предыдущее число
            last = num;
            //Читаем новое число
            num = getNumber(readerA);
        }
        //Выходим из цикла т.к. числа в файле закончили
        //Ставим символ окончания цепочки в конец файла, в который мы последний раз писали
        if (dir == 0) {
            writerToB.write("-2 ");
        } else {
            writerToC.write("-2 ");
        }
        //Освобождаем ресурсы, даём классам записать данные в файл
        writerToB.flush();
        writerToB.close();
        writerToC.flush();
        writerToC.close();
        readerA.close();
    }
    //Метод для замерки времени сортировки методом поглощения
    private long absorbSort(long count) {
        //Сохраняем время
        long time = System.currentTimeMillis();
        //Создаём класс-обёртку для взаимодействия с файлом "a"
        FileHelper fileHelper = new FileHelper("a.txt");
        //Создаём буффер размера n
        int[] buffer = new int[n];
        //Заполняем буффер числами с конца файла
        for (int i = 0; i < n; i++) {
            buffer[i] = fileHelper.readNumber(count - 1 - i);
        }
        //Сортируем буффер (метод использует быструю сортировку, курсач про внешнюю сортировку, поэтому я думаю можно использовать системные методы сортировки)
        Arrays.sort(buffer);
        //Проверяем не больше ли размер буффера чем размер файла
        if (n>count){
            //Если размер буффера больше, то поскольку он уже отсортирован, то записываем его в файл
            //ВАЖНО fileHelper.readNumber возвращет очень большое число, которое точно больше всех остальных чисел, если файл закончился и читать нечего
            //Поэтому при сортировке эти несуществующие числа будут в конце буффера.
            //Важно увидеть, что записываем только количество чисел, которое изначально было в файле
            for (int i = 0; i < count; i++) {
                fileHelper.writeNumber(i, buffer[i]);
            }
            //Освобождаем ресурсы
            fileHelper.close();
            //Возвращаем время т.к. сортировка окончена
            return System.currentTimeMillis() - time;
        }
        //Если буффер всё же меньше размера файла, то записываем его на прежнее место, только в отсортированном виде
        for (int i = 0; i < n; i++) {
            fileHelper.writeNumber(count - n + i, buffer[i]);
        }
        //Количество прошедших повторений записи в буфер и сортировки. Нужно для определения положения чтения и записи
        int x = 1;
        //Крутимся в цикле пока количество итераций меньше либо равно числу итераций, которое должно пройти. Пример: 10/3 = 3.33(3), где 10 - размер файла, 3 - размер буффера; 3.33 - 1 = 2, где -1 нужен для учёта того, что после цикла придётся сортировать элементы, которые не занимают полностью буффер; 2 - Столько всего нужно итераций цикла для сортировки
        while (x <= count / n - 1) {
            //Заполняем буффер
            for (int i = 0; i < n; i++) {
                /*Как получили число в скобках:
                Число элементов count
                Отчёт элементов с 0, поэтому последний индекс числа в файле count -1
                n - размер буфера, x - количество итераций. При перемножении дают количество чисел, которые уже были отсортированы
                i - вычитание индекса в буффере
                 */
                buffer[i] = fileHelper.readNumber(count - 1 - (long) n * x - i);
            }
            //Сортируем буффер
            Arrays.sort(buffer);
            //Переменные для определения сколько чисел уже было записано из буффера и из файла
            int kNums = 0;
            int kFiles = 0;
            //Повторяем цикл столько раз, сколько отсортированных элементов у нас уже есть + размер буффера
            for (int i = 0; i < n + n * x; i++) {
                //Если элемент буффера меньше элемента файла, то пишем из буффера,  иначе из файла
                if (buffer[kNums] <= fileHelper.readNumber(count - (long) n * x + kFiles)) {
                    fileHelper.writeNumber(count - (long) n * (x + 1) + i, buffer[kNums]);
                    //Прибавляем единицу к счётчику чисел, которые добавили из буффера
                    kNums++;
                    //Если все числа из буффера добавлены в файл, то значит, что остальная часть отсортирована и можно завершать цикл
                    /*Пример
                    Буффер 1 2 3 - отсортирован
                    Файл 3 1 2 4 5 6, где 4 5 6 - отсортированная ранее часть
                    Проходя по циклу заменяем в файле 3 на 1, 1 на 2, 2 на 3. 4 5 6 в замене не нуждаются - выходим из цикла
                     */
                    if (kNums == n) break;
                } else {
                    /*Как получили число в скобках:
                    Число элементов count
                    -1 не нужен т.к. положение числа настраивается с помощью i
                    n - размер буфера, x+1 - количество итераций. При перемножении дают количество чисел, которые уже были отсортированы
                    i - прибавление индекса
                    */
                    fileHelper.writeNumber(count - (long) n * (x + 1) + i, fileHelper.readNumber(count - (long) n * x + kFiles));
                    //Прибавляем единицу к переменной счётчика цифр, которые добавлены из файла
                    kFiles++;
                }
            }
            //Прибавляем итерацию после её завершения
            x++;
        }
        //Количество элементов, которые останутся после сортировки, но не смогут полностью заполнить буффер. 10 по 3, останется 1 элемент
        int lost = (int) (count % n);
        //Заполняем буффер этими элементами
        for (int i = 0; i < lost; i++) {
            buffer[i] = fileHelper.readNumber(i);
        }
        //Заполняем буффер "большими числами", чтобы при сортировке они ушли в конец и не мешались
        for (int i = lost; i < n; i++) {
            buffer[i] = 10000;
        }
        //Сортируем
        Arrays.sort(buffer);
        //Переменные для определения сколько чисел уже было записано из буффера и из файла
        int kNums = 0;
        int kFiles = 0;
        //Проходим по всему файлу
        for (int i = 0; i < count - 1; i++) {
            //Сравниваем числа из буффера с числами из файла и записываем
            if (buffer[kNums] <= fileHelper.readNumber(lost + kFiles)) {
                fileHelper.writeNumber(i, buffer[kNums]);
                kNums++;
                if (kNums == lost) break;
            } else {
                fileHelper.writeNumber(i, fileHelper.readNumber(lost + kFiles));
                kFiles++;
            }
        }
        //Освобождаем ресурсы
        fileHelper.close();
        //Возвращаем время
        return System.currentTimeMillis() - time;
    }

    //Генерируем файл с данными массива в формате, где у числа всегда фиксированная длинна в 4 символа: 0001, 0010, 0100, 1000
    private void generateFile(long count) {
        try (FileWriter writer = new FileWriter("tmp.txt")) {
            for (int i = 0; i < count; i++) {
                writer.write(String.format("%04d", random.nextInt(10000)) + " ");
            }
            writer.flush();
            //Клонируем файл
            cloneFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Метод для подсчёта времени, объединения в одну структуру и возвращения на UI
    public Base time(long count) {
        try {
            //Генерируем файл
            generateFile(count);
            long simpleTime = simpleSort(count);
            //Клонируем файл из tmp в a т.к. a изменился
            cloneFile();
            long naturalTime = naturalSort();
            //Клонируем файл из tmp в a т.к. a изменился
            cloneFile();
            long absorbTime = absorbSort(count);
            //Создаём строчку и заполняем её данными
            return new Base(count, simpleTime, naturalTime, absorbTime);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //Метод для разбиения чисел на 2 файла в методе простого слияния
    private void simpleSortSplit(long len) throws IOException {
        //Создаём классы для записи
        PrintWriter writerToB = new PrintWriter(new FileWriter("b.txt"));
        PrintWriter writerToC = new PrintWriter(new FileWriter("c.txt"));
        //Создаём класс для чтения
        InputStream readerA = new FileInputStream("a.txt");
        //Создаём переменную, которая содержит сколько ещё чисел должно быть в цепочке
        long curLen = len;
        //Переменная для определения файла для записи
        int dir = 0;
        //Читаем пока не закончатся числа
        while (readerA.available() != 0) {
            //Читаем новый элемент
            int elem = getNumber(readerA);
            //Если в цепочке есть место, то не меняем файл для записи, а если нет, то меняем
            if (curLen > 0) {
                curLen--;
            } else {
                //Тернарный оператор для смены направления: если dir =0, то станет 1, иначе 0
                dir = dir == 0 ? 1 : 0;
                //Уменьшаем длину новой цепочки на 1, потому что мы уже добавим в неё первое число
                curLen = len - 1;
            }
            //Записываем число в файл в зависимости от направления
            if (dir == 0) {
                writerToB.write(elem + " ");
            } else {
                writerToC.write(elem + " ");
            }
        }
        //Освобождаем ресурсы и говорим классам для записи напечатать в файлы текст
        writerToB.flush();
        writerToC.flush();
        writerToB.close();
        writerToC.close();
        readerA.close();
    }
    //Метод для клонирования файлов
    private void cloneFile() throws IOException {
        //Объект для записи
        PrintWriter writerToA = new PrintWriter(new FileWriter("a.txt"));
        //Объект для чтения
        InputStream reader = new FileInputStream("tmp.txt");
        //Читаем число
        int num = getNumber(reader);
        //Пока не достигнут конец файла крутимся в цикле
        while (num != -1) {
            //пишем число, сохраняя 4 значный формат
            writerToA.write(String.format("%04d", num) + " ");
            //Читаем новое число
            num = getNumber(reader);
        }
        //Освобождаем ресурсы и говорим классу для записи напечатать в файл текст
        writerToA.flush();
        writerToA.close();
        reader.close();
    }
    //Метод для объединения данных из файлов в один
    private void simpleSortCombine(long len) throws IOException {
        //Объект для записи
        PrintWriter writerToA = new PrintWriter(new FileWriter("a.txt"));
        //Объекты для чтения
        InputStream readerB = new FileInputStream("b.txt");
        InputStream readerC = new FileInputStream("c.txt");
        //Читаем первые числа из 2 файлов
        int first = getNumber(readerB);
        int second = getNumber(readerC);
        //Крутимся в цикле, пока не достигнем конца в обоих файлах
        while (first != -1 || second != -1) {
            //Выставляем длину для цепочек
            long curB = len;
            long curC = len;
            //Крутимся в цикле, пока не закончится место в цепочке или пока не достигнем конца файла
            while (curB != 0 && curC != 0 && first != -1 && second != -1) {
                //Сравниваем 2 числа из 2 файлов
                if (first < second) {
                    //Если первое меньше, то записываем его в файл a
                    writerToA.write(first + " ");
                    //Уменьшаем доступное место в цепочке для первого файла
                    curB--;
                    //Читаем новое число из первого файла
                    first = getNumber(readerB);
                } else {
                    //Если второе меньше, то пишем его в файл а
                    writerToA.write(second + " ");
                    //Уменьшаем доступное место в цепочке для второго файла
                    curC--;
                    //Читаем новое число из второго файла
                    second = getNumber(readerC);
                }
            }
            //Либо закончилась цепочка для одного из файлов, либо данные в файле
            //Дописываем данные из первого файла, если второй либо закончился, либо достигнут лимит чисел в цепочке из второго
            while (curB != 0 && first != -1) {
                writerToA.write(first + " ");
                curB--;
                first = getNumber(readerB);
            }
            //Либо дописываем из второго
            while (curC != 0 && second != -1) {
                writerToA.write(second + " ");
                curC--;
                second = getNumber(readerC);
            }
        }
        //Освобождаем ресурсы и говорим классу для записи напечатать в файл текст
        writerToA.flush();
        readerB.close();
        readerC.close();
    }

    //Метод для получения числа из файла для методов простого слияния и естественного слияния
    //Принимает объект, который хранит информацию из какого файла читать
    private int getNumber(InputStream reader) throws IOException {
        //Переменная для чтения символа
        int character;
        //Это класс обёртка для строки. По факту тот же String, просто умеет нормально добавлять в себе элементы
        StringBuilder currentElem = new StringBuilder();
        //Читаем пока не закончится файл
        while (reader.available() != 0) {
            //Читаем символ
            character = reader.read();
            //Проверяем пробел ли это
            if (character != 32) {
                //Если не пробел, то продолжаем записывать число
                currentElem.append((char) character);
            } else {
                //Если пробел, то приводим в целому числу и возвращаем
                return Integer.parseInt(currentElem.toString());
            }
        }
        //Если читать нечего, то возвращаем -1
        return -1;
    }


}
