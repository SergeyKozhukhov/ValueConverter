package ru.kozhukhov.sergey.valueconverter.converter;

import java.util.List;

import ru.kozhukhov.sergey.valueconverter.adapters.AdapterUnits;
import ru.kozhukhov.sergey.valueconverter.models.Units;

/*
* Конвертер величин
* */
public class ValueConverter {

    /*
    * categoryData - список единиц измерений
    * positionTo - выбранная позиция "ИЗ" (единица измерения)
    * positionTo - выбранная позиция "В" (единица измерения)
    * */
    List<Units> categoryData;
    private int positionTo;
    private int positionFrom;

    public ValueConverter(AdapterUnits adapterUnits) {
        this.categoryData = adapterUnits.getCategoryData();
        positionFrom = 0;
        positionTo = 0;
    }


    /*
    * Конвертация единиц измерения
    *
    * @param value - значение для перевода величин (например, 10 км, 100 м и т.д.)
    * */
    public double convert(double value)
    {
        return value*categoryData.get(positionTo).getProportionTo()*
                categoryData.get(positionFrom).getProportionFrom();
    }

    /*
     * Обратная конвертация единиц измерения
     *
     * @param value - значение для перевода величин (например, 10 км, 100 м и т.д.)
     * */
    public double convertReverse(double value)
    {
        return value*categoryData.get(positionFrom).getProportionTo()*
                categoryData.get(positionTo).getProportionFrom();

    }

    public void setPositionFrom(int positionFrom) {
        this.positionFrom = positionFrom;
    }

    public void setPositionTo(int positionTo) {
        this.positionTo = positionTo;
    }
}
