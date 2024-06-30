package com.liepu.finalassignment.first;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.liepu.finalassignment.R;

import java.util.Random;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    TextView textNum1;
    TextView textNum2;
    TextView textMathSign;
    EditText editTextResult;
    Button validate;
    LinearLayout closeButton;

    View.OnClickListener listener;

    public int result = 0;

    private OnCalculationListener mListener;


    public BottomSheetDialog(OnCalculationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);

        textNum1 = v.findViewById(R.id.tv_num_1);
        textNum2 = v.findViewById(R.id.tv_num_2);
        textMathSign = v.findViewById(R.id.tv_math_sign);
        editTextResult = v.findViewById(R.id.editTextResult);
        validate = v.findViewById(R.id.btn_validate);
        closeButton = v.findViewById(R.id.lL_close);

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };

        setupMath();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput;
                userInput = editTextResult.getText().toString();

                if (userInput.equals(String.valueOf(result))) {
                    mListener.onValidateClicked(true);
                    dismiss();
                }
                else {
                    setupMath();
                    mListener.onValidateClicked(false);
                }
            }
        });

        closeButton.setOnClickListener(listener);
        return v;
    }

    private void setupMath() {
        Random random = new Random();
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int mathSign = random.nextInt(3); // 0+,1-,2*,3/

        textNum1.setText(String.valueOf(num1));
        textNum2.setText(String.valueOf(num2));

        switch (mathSign) {
            case 0:
                textMathSign.setText("+");
                result = num1 + num2;
                break;
            case 1:
                textMathSign.setText("-");
                result = num1 - num2;
                break;
            case 2:
                textMathSign.setText("*");
                result = num1 * num2;
                break;
            case 3:
                textMathSign.setText("/");
                result = num1 / num2;
                break;
        }
    }

    interface OnCalculationListener {
        void onValidateClicked(boolean clicked);
    }
}
