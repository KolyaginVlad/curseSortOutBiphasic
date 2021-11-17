package ru.course.cursesortoutbiphasic;

public class Algoritms {
    private long simpleSort(long count) {
        return 1 * count;
    }

    private long naturalSort(long count) {
        return 2 * count;
    }

    private long absorbSort(long count) {
        return 3 * count;
    }

    public Base time(long count) {

        long simpleTime = simpleSort(count);
        long naturalTime = naturalSort(count);
        long absorbTime = absorbSort(count);
        //Создаём строчку и заполняем её данными
        Base base = new Base(count, simpleTime, naturalTime, absorbTime);
        return base;
    }
}
