package ru.kozhukhov.sergey.valueconverter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import ru.kozhukhov.sergey.valueconverter.R;
import ru.kozhukhov.sergey.valueconverter.adapters.AdapterCategoryUnits;
import ru.kozhukhov.sergey.valueconverter.adapters.OnItemClickListener;
import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

/*
* Фрагмент для отображения списка категорий
* */
public class FragmentCategories extends Fragment {

    private OnItemClickListener mOnItemClickListener; // обработчик нажатия на ячейку списка

    private RecyclerView mRecycleView_CategoryUnits;

    public static Fragment newInstance() {
        return new FragmentCategories();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView_CategoryUnits = view.findViewById(R.id.fragment_categories_RecycleView_CategoryUnits);
        mRecycleView_CategoryUnits.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerViewCategoryUnits();
    }

    private void initRecyclerViewCategoryUnits(){
        mOnItemClickListener = new OnItemClickListener(){
            @Override
            public void itemClick(CategoryUnits categoryUnits) {

                FragmentActivity activity = getActivity();
                if (activity instanceof ValueConverterHolder){
                    ((ValueConverterHolder)activity).showValueConverter(categoryUnits);
                }
            }
        };

        mRecycleView_CategoryUnits.setAdapter(new AdapterCategoryUnits(Arrays.asList(CategoryUnits.values()), mOnItemClickListener));
    }

    /*
    * Интерфейс для взаимодействия с другим фрагментом
    * */
    public interface ValueConverterHolder{
        void showValueConverter(@NonNull CategoryUnits categoryUnits);
    }

}
