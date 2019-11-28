package ru.kozhukhov.sergey.valueconverter.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

import java.util.Arrays;
import java.util.List;

import ru.kozhukhov.sergey.valueconverter.R;


/*
 * Информация о категориях единиц измерений.
 * */

public enum CategoryUnits implements Parcelable{

    LENGTH (R.string.CategoryUnits_LENGTH, Arrays.asList(Units.KILOMETER, Units.METER, Units.CENTIMENTR, Units.MILLIMETER)),
    AREA(R.string.CategoryUnits_AREA, Arrays.asList(Units.HECTARE, Units.SQUARE_METER, Units.SQUARE_CENTIMETER)),
    MASS(R.string.CategoryUnits_MASS, Arrays.asList(Units.TON, Units.KILOGRAM, Units.GRAMM, Units.MILIGRAM)),
    TIME(R.string.CategoryUnits_TIME, Arrays.asList(Units.HOUR, Units.MINUTE, Units.SECOND));


    /*
    * categoryName - наименование категории единиц измерения,
    * categoryData - список единиц измерения данной категории
    * */

    @StringRes
    private int categoryName;

    private List<Units> categoryData;

    CategoryUnits(@StringRes int categoryName, List <Units> categoryData) {
        this.categoryName = categoryName;
        this.categoryData = categoryData;
    }

    public int getCategoryName() {
        return categoryName;
    }

    public List<Units> getCategoryData() {
        return categoryData;
    }


    public static final Parcelable.Creator<CategoryUnits> CREATOR = new Creator<CategoryUnits>() {

        @Override
        public CategoryUnits[] newArray(int size) {
            return new CategoryUnits[size];
        }

        @Override
        public CategoryUnits createFromParcel(Parcel source) {
            return CategoryUnits.values()[source.readInt()];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ordinal());
    }


}
