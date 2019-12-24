package ru.kozhukhov.sergey.valueconverter.models;

import androidx.annotation.StringRes;

import ru.kozhukhov.sergey.valueconverter.R;

/*
 * Информация о единицах измерения.
 * */
public enum Units {

    KILOMETER(R.string.Units_KILOMETER, 1000.0, 0.001),
    METER(R.string.Units_METER, 1.0, 1.0),
    CENTIMETER(R.string.Units_SENTIMETR, 0.01, 100),
    MILLIMETER(R.string.Units_MILLIMETER, 0.001, 1000.0),

    HECTARE(R.string.Units_HECTARE, 10000.0, 0.0001),
    SQUARE_METER(R.string.Units_SQUARE_METER, 1.0, 1.0),
    SQUARE_CENTIMETER(R.string.Units_SQUARE_CENTIMETER, 0.0001, 10000.0),

    TON(R.string.Units_TON, 1000, 0.001),
    KILOGRAM(R.string.Units_KILOGRAM, 1.0, 1.0),
    GRAMM(R.string.Units_GRAM, 0.001, 1000.0),
    MILIGRAM(R.string.Units_MILIGRAM, 0.000001, 1000000.0),

    HOUR(R.string.Units_HOUR, 1.0, 1.0),
    MINUTE(R.string.Units_MINUTE, 0.06, 60.0),
    SECOND(R.string.Units_SECOND, 0.0036, 3600.0);


    /*
    * unitName - наименование единицы измерения,
    * proportionFrom - коэффециент конвертации из данной единицы измерения,
    * proportionTo - коэффициент конвертация в данную единицу измерения.
    * */

    @StringRes
    private int unitName;

    private double proportionTo;
    private double proportionFrom;

    Units(@StringRes int unitName, double proportionFrom, double proportionTo) {
        this.unitName = unitName;
        this.proportionFrom = proportionFrom;
        this.proportionTo = proportionTo;
    }

    public int getUnitName() {
        return unitName;
    }

    public double getProportionTo() {
        return proportionTo;
    }

    public double getProportionFrom() {
        return proportionFrom;
    }


}
