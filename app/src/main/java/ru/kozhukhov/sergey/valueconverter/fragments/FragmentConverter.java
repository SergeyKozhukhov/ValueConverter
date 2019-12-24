package ru.kozhukhov.sergey.valueconverter.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.kozhukhov.sergey.valueconverter.R;
import ru.kozhukhov.sergey.valueconverter.adapters.AdapterUnits;
import ru.kozhukhov.sergey.valueconverter.converter.ValueConverter;
import ru.kozhukhov.sergey.valueconverter.models.CategoryUnits;

/*
* Фрагмент, содержащий конвертер единиц измерения
* */
public class FragmentConverter extends Fragment {
    /*
     * CATEGORY_UNITS - значения для получения единиц измерения
     * REVERSE_CONVERT: false - конвертация слева направо, true - конвертация справо налево
     * */
    private static final String CATEGORY_UNITS = "CATEGORY_UNITS";
    private boolean REVERSE_CONVERT = false;

    private CategoryUnits mCategoryUnits;
    private AdapterUnits mAdapterUnits;
    private ValueConverter mValueConverter;

    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;

    private EditText mEditTextFrom;
    private EditText mEditTextTo;

    private TextView mTextViewCategoryName;

    /*
    * @param categoryUnits - категория единиц измерения, на основе которой предлагаются единицы измерения для конвертации
    * */
    public static FragmentConverter newInstance(@NonNull CategoryUnits categoryUnits) {
        FragmentConverter fragmentConverter = new FragmentConverter();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_UNITS, categoryUnits);
        fragmentConverter.setArguments(args);
        return fragmentConverter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCategoryUnits = getCategoryUnitsFromArgs();

        mSpinnerFrom = view.findViewById(R.id.spinner_from);
        mSpinnerTo = view.findViewById(R.id.spinner_to);

        mEditTextFrom = view.findViewById(R.id.editText_from);
        mEditTextTo = view.findViewById(R.id.editText_to);

        mTextViewCategoryName = view.findViewById(R.id.textView_categoryName);
        mTextViewCategoryName.setText(mCategoryUnits.getCategoryName());


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapterUnits = new AdapterUnits(mCategoryUnits, requireContext());
        mValueConverter = new ValueConverter(mAdapterUnits);

        mSpinnerFrom.setAdapter(mAdapterUnits);
        mSpinnerTo.setAdapter(mAdapterUnits);

        initSpinnersListeners();
        initEditTextsListeners();

    }

    /*
    * Получение категории единицы измерения для конвертации
    * */
    @NonNull
    private CategoryUnits getCategoryUnitsFromArgs() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalStateException("Arguments must be set");
        }
        CategoryUnits categoryUnits = arguments.getParcelable(CATEGORY_UNITS);
        if (categoryUnits == null) {
            throw new IllegalStateException("Lecture must be set");
        }
        return categoryUnits;
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
                if (!REVERSE_CONVERT) {
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
     * */
    private void convertValue(CharSequence s) {
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
     * */
    private void convertReverseValue(CharSequence s) {
        if (REVERSE_CONVERT == true) {
            if (s.length() != 0) {
                double value = mValueConverter.convertReverse(Double.parseDouble(s.toString()));
                mEditTextFrom.setText(String.valueOf(value));
            } else {
                mEditTextFrom.setText("");
            }
        }
    }

}
