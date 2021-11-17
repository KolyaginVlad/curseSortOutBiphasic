package ru.course.cursesortoutbiphasic;

import javafx.beans.property.SimpleLongProperty;

public class Base {
    //Создаём перемнные со специальным типом данных, которые могут отображаться в таблице
    public SimpleLongProperty size;
    public SimpleLongProperty simple;
    public SimpleLongProperty nature;
    public SimpleLongProperty absorption;
    //Создаём конструктор
    public Base(long count, long simpleMerge, long naturalMerge, long absorptionMethod) {
        size = new SimpleLongProperty(count);
        simple = new SimpleLongProperty(simpleMerge);
        nature = new SimpleLongProperty(naturalMerge);
        absorption = new SimpleLongProperty(absorptionMethod);
    }
    //Создаём getter'ы используя alt+insert и выбрав все переменные
    public long getSize() {
        return size.get();
    }

    public long getSimple() {
        return simple.get();
    }

    public long getNature() {
        return nature.get();
    }


    public long getAbsorption() {
        return absorption.get();
    }

}
