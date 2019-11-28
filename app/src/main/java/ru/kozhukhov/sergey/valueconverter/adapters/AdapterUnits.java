package ru.kozhukhov.sergey.valueconverter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;
import ru.kozhukhov.sergey.valueconverter.models.Units;

/*
 * Адаптер для отображения наименований единий измерения в Spinner
 * */

public class AdapterUnits extends BaseAdapter {

    /*
    * contex требуется для загрузки данных из файла ресуров,
    * чтобы в spinner записать не id названий, а текстовое представление
    *
    * categoryData - список единиц измерений
    * */

    private Context context;

    private final List<Units> categoryData;


    /*
    * Конструктор адаптера
    * */

    public AdapterUnits(CategoryUnits categoryUnits, Context context) {
        categoryData = categoryUnits.getCategoryData();
        this.context = context;
    }

    public List<Units> getCategoryData() {
        return categoryData;
    }

    /*
    * Возвращение колличество единиц измерения
    * */

    @Override
    public int getCount() {
        return categoryData.size();
    }

    /*
    * Возврашение текстового представления наименования единицы измерения из файла ресурсов
    * */

    @Override
    public String getItem(int position) {
        return context.getString(categoryData.get(position).getUnitName());
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*
    * Возвращение наглядного представления ячейкм spinner c соответствующими данными
    * simple_list_item_1 - встроенный xml файл c TextView
    * */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mUnitName.setText(getItem(position));
        return convertView;
    }


    private class ViewHolder {

        /*
         * mUnitName - TextView для отображения наименования единицы измерения
         * для simple_list_item_1 следует обратиться к text1
         * */

        private final TextView mUnitName;

        private ViewHolder(View view) {
            mUnitName = view.findViewById(android.R.id.text1);
        }
    }



}
