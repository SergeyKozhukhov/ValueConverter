package ru.kozhukhov.sergey.valueconverter.adapters;

import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

/*
* Интерфейс для обработки нажатия на ячейку RecyclerView
* */
public interface OnItemClickListener {
    /*
    * Параметры функции:
    * @param categoryUnits - категория единиц измерения, зафиксированная за нажатой ячейкой
    * */
    void itemClick(CategoryUnits categoryUnits);
}
