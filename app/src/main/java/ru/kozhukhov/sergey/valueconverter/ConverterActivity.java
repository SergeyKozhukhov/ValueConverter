package ru.kozhukhov.sergey.valueconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ru.kozhukhov.sergey.valueconverter.adapters.AdapterUnits;
import ru.kozhukhov.sergey.valueconverter.converter.ValueConverter;
import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

public class ConverterActivity extends AppCompatActivity {

    /*
     * OPEN_CATEGORY - значения для получения единиц измерения из предыдущей activity
     * REVERSE_CONVERT: false - конвертация слева направо, true - конвертация справо налево
     * */

    private final String OPEN_CATEGORY = "CATEGORY_UNITS";
    private boolean REVERSE_CONVERT = false;

    private CategoryUnits mCategoryUnits;
    private AdapterUnits mAdapterUnits;
    private ValueConverter mValueConverter;

    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;

    private EditText mEditTextFrom;
    private EditText mEditTextTo;

    private TextView mTextViewCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        /*
        * Получение категории единиц измерения от MainActivity
        * */
        mCategoryUnits = getIntent().getParcelableExtra(OPEN_CATEGORY);

        mAdapterUnits = new AdapterUnits(mCategoryUnits, this);
        mValueConverter = new ValueConverter(mAdapterUnits);

        mSpinnerFrom = findViewById(R.id.spinner_from);
        mSpinnerTo = findViewById(R.id.spinner_to);

        mEditTextFrom = findViewById(R.id.editText_from);
        mEditTextTo = findViewById(R.id.editText_to);

        mTextViewCategoryName = findViewById(R.id.textView_categoryName);
        mTextViewCategoryName.setText(mCategoryUnits.getCategoryName());

        mSpinnerFrom.setAdapter(mAdapterUnits);
        mSpinnerTo.setAdapter(mAdapterUnits);

        initSpinnersListeners();
        initEditTextsListeners();
    }

    /*
    * Обработчики нажатия на EditText и ввода в него значений
    * */
    private void initEditTextsListeners() {

        /*
         * Определении прямой или обратной конвертации при обращении соответственно к левому или правому полю ввода
         * */

        mEditTextFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    REVERSE_CONVERT = false;
                }
            }
        });

        mEditTextTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    REVERSE_CONVERT = true;
                }
            }
        });

        /*
         * Постепенное преобразование величин по мере ввода данных
         * */

        mEditTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                convertValue(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTextTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertReverseValue(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*
     * Обработчики выбора единицы измерения в Spinner
     * */
    private void initSpinnersListeners() {

        /*
        * Вычисление значений при выборе единиц измерения в Spinner
        * */

        mSpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mValueConverter.setPositionFrom(position);
                if (REVERSE_CONVERT == false) {
                    convertValue(mEditTextFrom.getText());
                } else {
                    convertReverseValue(mEditTextTo.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        mSpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mValueConverter.setPositionTo(position);
                if (REVERSE_CONVERT == false) {
                    convertValue(mEditTextFrom.getText());
                } else {
                    convertReverseValue(mEditTextTo.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    /*
    * Прямая конвертация.
    *
    * Проверка на REVERSE_CONVERT для пресечения вызова функции обратной конвертация при onTextChange,
    * вызывающегося в момент вставки текста в правое поле ввода.
    *
    * Если левое поле ввода пустое, правое поле устанавливается также пустым.
    *
    * */

    void convertValue(CharSequence s) {
        if (REVERSE_CONVERT == false) {
            if (s.length() != 0) {
                double value = mValueConverter.convert(Double.parseDouble(s.toString()));
                mEditTextTo.setText(String.valueOf(value));
            } else {
                mEditTextTo.setText("");
            }
        }
    }

    /*
     * Обратная конвертация
     *
     * Проверка на REVERSE_CONVERT для пресечения вызова функции прямой конвертация при onTextChange,
     * вызывающегося в момент вставки текста в левое поле ввода.
     *
     * Если правое поле ввода пустое, левое поле устанавливается также пустым.
     *
     * */

    void convertReverseValue(CharSequence s) {
        if (REVERSE_CONVERT == true) {
            if (s.length() != 0) {
                double value = mValueConverter.convertReverse(Double.parseDouble(s.toString()));
                mEditTextFrom.setText(String.valueOf(value));
            } else {
                mEditTextFrom.setText("");
            }
        } else {
        }
    }


}
