package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HcfCalculatorActivity extends Activity {
    int numberCounter = 2;
    TextView textViewResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hcf_calculator);
        textViewResult = findViewById(R.id.textViewResult);
        addBannerAd();
    }

    private void addBannerAd()
    {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        mAdView.loadAd(adRequest);
    }

    public void onClickAddNumber(View view)
    {
        numberCounter += 1;
        switch (numberCounter)
        {
            case 3:
                findViewById(R.id.editTextNumber3).setVisibility(View.VISIBLE);
                findViewById(R.id.imageButtonAddNumberGrey).setVisibility(View.INVISIBLE);
                findViewById(R.id.imageButtonAddNumber).setVisibility(View.VISIBLE);
                break;

            case 4:
                findViewById(R.id.editTextNumber4).setVisibility(View.VISIBLE);
                findViewById(R.id.imageButtonAddNumberGrey).setVisibility(View.INVISIBLE);
                findViewById(R.id.imageButtonAddNumber).setVisibility(View.VISIBLE);
                break;

            case 5:
                findViewById(R.id.editTextNumber5).setVisibility(View.VISIBLE);
                findViewById(R.id.imageButtonAddNumberGrey).setVisibility(View.VISIBLE);
                findViewById(R.id.imageButtonAddNumber).setVisibility(View.INVISIBLE);
                break;

            default:
                break;
        }
    }

    private boolean validateMinimumInputNumbers()
    {
        int inputCount = 0;
        EditText editText = findViewById(R.id.editTextNumber1);
        if(!editText.getText().toString().trim().isEmpty())
        {
            inputCount += 1;
        }

        editText = findViewById(R.id.editTextNumber2);
        if(!editText.getText().toString().trim().isEmpty())
        {
            inputCount += 1;
        }

        editText = findViewById(R.id.editTextNumber3);
        if(!editText.getText().toString().trim().isEmpty())
        {
            inputCount += 1;
        }

        editText = findViewById(R.id.editTextNumber4);
        if(!editText.getText().toString().trim().isEmpty())
        {
            inputCount += 1;
        }

        editText = findViewById(R.id.editTextNumber5);
        if(!editText.getText().toString().trim().isEmpty())
        {
            inputCount += 1;
        }

        if(inputCount < 2)
        {
            return false;
        }
        return true;
    }

    public void onClickButtonFindLcm(View view)
    {
        if(!validateMinimumInputNumbers())
        {
            textViewResult.setText("You should input minimum 2 numbers");
            textViewResult.setTextColor(Color.RED);
            textViewResult.setVisibility(View.VISIBLE);
            return;
        }
        else
        {
            textViewResult.setTextColor(Color.BLUE);
            textViewResult.setVisibility(View.INVISIBLE);
        }
        int num1 = getNumber((EditText)findViewById(R.id.editTextNumber1), 1);
        int num2 = getNumber((EditText)findViewById(R.id.editTextNumber2), 1);
        int num3 = getNumber((EditText)findViewById(R.id.editTextNumber3), num1);
        int num4 = getNumber((EditText)findViewById(R.id.editTextNumber4), num1);
        int num5 = getNumber((EditText)findViewById(R.id.editTextNumber5), num1);

        int r = LcmCalculator.calculateLcm(num1, num2, num3, num4, num5);
        String result = "";
        if(r == -1)
        {
            result = "Error calculating LCM.";
            textViewResult.setTextColor(Color.RED);
        }
        else
        {
            result = "LCM of [" + getAllInputNumbers() + "] is: " + r;
            textViewResult.setTextColor(Color.BLUE);
        }

        textViewResult.setText(result);
        textViewResult.setVisibility(View.VISIBLE);
    }

    public void onClickButtonFindHcf(View view)
    {
        if(!validateMinimumInputNumbers())
        {
            textViewResult.setText("You should input minimum 2 numbers");
            textViewResult.setTextColor(Color.RED);
            textViewResult.setVisibility(View.VISIBLE);
            return;
        }
        else
        {
            textViewResult.setTextColor(Color.BLUE);
            textViewResult.setVisibility(View.INVISIBLE);
        }

        int num1 = getNumber((EditText)findViewById(R.id.editTextNumber1), 1);
        int num2 = getNumber((EditText)findViewById(R.id.editTextNumber2), 1);
        int num3 = getNumber((EditText)findViewById(R.id.editTextNumber3), num1);
        int num4 = getNumber((EditText)findViewById(R.id.editTextNumber4), num1);
        int num5 = getNumber((EditText)findViewById(R.id.editTextNumber5), num1);

        int r = HcfCalculator.calculateHcf(num1, num2, num3, num4, num5);
        String result="";

        result = "HCF of [" + getAllInputNumbers() + "] is: " + r;

        textViewResult.setText(result);
        textViewResult.setVisibility(View.VISIBLE);
    }

    private int getNumber(EditText editText, int defaultNumber)
    {
        String temp = editText.getText().toString().trim();
        int num;

        if(TextUtils.isEmpty(temp))
        {
            num = defaultNumber;
        }
        else
        {
            num = Integer.parseInt(temp);
        }

        return num;
    }

    private String getAllInputNumbers()
    {
        String allInputNumbers = "";
        EditText editText = findViewById(R.id.editTextNumber1);
        String text =editText.getText().toString().trim();

        if(!TextUtils.isEmpty(text))
        {
            allInputNumbers += text;
        }

        editText = findViewById(R.id.editTextNumber2);
        text = editText.getText().toString().trim();

        if(!TextUtils.isEmpty(text))
        {
            allInputNumbers += "," + text;
        }

        editText = findViewById(R.id.editTextNumber3);
        text = editText.getText().toString().trim();

        if(!TextUtils.isEmpty(text))
        {
            allInputNumbers += "," + text;
        }

        editText = findViewById(R.id.editTextNumber4);
        text = editText.getText().toString().trim();

        if(!TextUtils.isEmpty(text))
        {
            allInputNumbers += "," + text;
        }

        editText = findViewById(R.id.editTextNumber5);
        text = editText.getText().toString().trim();

        if(!TextUtils.isEmpty(text))
        {
            allInputNumbers += "," + text;
        }

        if(allInputNumbers.startsWith(","))
        {
            allInputNumbers = allInputNumbers.substring(1);
        }
        if(allInputNumbers.endsWith(","))
        {
            allInputNumbers = allInputNumbers.substring(0, allInputNumbers.length() - 2);
        }
        return allInputNumbers;
    }
}
