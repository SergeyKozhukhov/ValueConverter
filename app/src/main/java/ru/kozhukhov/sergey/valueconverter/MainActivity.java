package ru.kozhukhov.sergey.valueconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.kozhukhov.sergey.valueconverter.fragments.FragmentCategories;
import ru.kozhukhov.sergey.valueconverter.fragments.FragmentConverter;
import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

public class MainActivity extends AppCompatActivity implements FragmentCategories.ValueConverterHolder {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * Добавление фрагмента со списком категорий при первоначальном запуске приложения
        * */
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fl_root, FragmentCategories.newInstance())
                    .commit();
        }
    }

    /*
    * Реализация интерфейса для открытия фрагмента с конвертером единиц измерений
    *
    * В документации от google сказано, что коммуникацию фрагментов следует проводить через activity,
    * никакого прямого общения друг с другом
    * http://blog.harrix.org/article/7521#h2_1
    * */
    @Override
    public void showValueConverter(@NonNull CategoryUnits categoryUnits) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fl_root, FragmentConverter.newInstance(categoryUnits))
                .addToBackStack(FragmentConverter.class.getSimpleName())
                .commit();
    }
}
