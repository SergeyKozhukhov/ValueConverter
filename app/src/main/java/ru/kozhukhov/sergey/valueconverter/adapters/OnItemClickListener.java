package ru.kozhukhov.sergey.valueconverter.adapters;

import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

/*
* Интерфейс для обработки нажатия на ячейку RecyclerView
*
* Параметры функции:
* @param categoryUnits - категория единиц измерения, зафиксированная за нажатой ячейкой
*
* */

public interface OnItemClickListener {
    void itemClick(CategoryUnits categoryUnits);
}
