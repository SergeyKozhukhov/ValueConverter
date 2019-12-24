package ru.kozhukhov.sergey.valueconverter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.kozhukhov.sergey.valueconverter.R;
import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

/*
* Адаптер для отображения списка категорий единий измерения в RecyclerView
* */
public class AdapterCategoryUnits extends RecyclerView.Adapter<AdapterCategoryUnits.ViewHolder> {


    /*
    * mCategoryUnits - список категорий
    * mOnItemClickListener - обработчик нажатия на ячейку списка
    * */
    private final List<CategoryUnits> mCategoryUnits;
    private OnItemClickListener mOnItemClickListener;

    /*
    * Конструктор адаптера
    * */
    public AdapterCategoryUnits(final List<CategoryUnits> categoryUnits, OnItemClickListener onItemClickListener) {
        this.mCategoryUnits = categoryUnits;
        this.mOnItemClickListener = onItemClickListener;
    }

    /*
    * Реализация вида ячейки RecyclerView
    * inflate позволяет из xml файла вернуть наглядное привычное представление файла
    * */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.recycle_view_category_units_item, parent, false);
        return new ViewHolder(view);
    }

    /*
    * Определение категории единиц измерения для данной ячейки и
    * обновление в соответствии с ней данных
    * */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CategoryUnits categoryUnits = mCategoryUnits.get(position);
        holder.bindView(categoryUnits, mOnItemClickListener);
    }

    /*
    * Возвращение колличества категорий
    * */
    @Override
    public int getItemCount() {
        return mCategoryUnits.size();
    }

    /*
    * Класс, отчечающий за:
    * - кэширование findViewById,
    * - привязку данных к соответствующей ячейке,
    * - установку обработчика нажатия на ячейку.
    * */
    static class ViewHolder extends RecyclerView.ViewHolder {

        /*
        * mRecycleView_CategoryUnits_item_TextView_Category -
        * - TextView для отображения наименования категории.
        * */
        private TextView mRecycleView_CategoryUnits_item_TextView_Category;

        /*
        * Реализация привязки TextView к соответствующим данным их файла ресурсов
        * */
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            mRecycleView_CategoryUnits_item_TextView_Category = itemView.findViewById(R.id.RecycleView_CategoryUnits_item_TextView_Category);
        }

        /*
         * Реализация заполнения TextView соответствующим текстом (наименованием категории) и
         * установки обработчика нажатия на ячейку с последующим выполнении функции интерейса OnItemClickListener
         * */
        void bindView(final CategoryUnits categoryUnits, final OnItemClickListener listener) {
            mRecycleView_CategoryUnits_item_TextView_Category.setText(categoryUnits.getCategoryName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(categoryUnits);
                }
            });
        }
    }
}
