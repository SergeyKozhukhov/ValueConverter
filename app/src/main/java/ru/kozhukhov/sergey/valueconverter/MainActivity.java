package ru.kozhukhov.sergey.valueconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import java.util.Arrays;

import ru.kozhukhov.sergey.valueconverter.adapters.AdapterCategoryUnits;
import ru.kozhukhov.sergey.valueconverter.adapters.OnItemClickListener;
import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

public class MainActivity extends AppCompatActivity {

    /*
    * SAVE_CATEGORY - значения для передачи единиц измерения выбранной категории в следующую activity
    * */
    private final String SAVE_CATEGORY = "CATEGORY_UNITS";

    private OnItemClickListener mOnItemClickListener;

    private RecyclerView mRecycleView_CategoryUnits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOnItemClickListener = new OnItemClickListener(){
            @Override
            public void itemClick(CategoryUnits categoryUnits) {
                /*
                * Передача единиц измерения выбранной категории в ConverterActivity
                * */
                Intent i = new Intent(getApplicationContext(), ConverterActivity.class);
                i.putExtra(SAVE_CATEGORY, (Parcelable) categoryUnits);
                startActivity(i);
            }
        };

        mRecycleView_CategoryUnits = findViewById(R.id.activity_main_RecycleView_CategoryUnits);
        mRecycleView_CategoryUnits.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView_CategoryUnits.setAdapter(new AdapterCategoryUnits(Arrays.asList(CategoryUnits.values()), mOnItemClickListener));
    }
}
