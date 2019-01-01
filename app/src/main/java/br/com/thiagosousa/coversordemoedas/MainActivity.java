package br.com.thiagosousa.coversordemoedas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //    Views
    @BindView(R.id.button_calculate)
    MaterialButton btnCalculate;
    @BindView(R.id.textview_dollarvalue)
    TextView textDollar;
    @BindView(R.id.textview_eurovalue)
    TextView textEuro;
    @BindView(R.id.edit_value)
    EditText editValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        clearValues(textDollar, textEuro);
        activateOnUpdateListener(true);

    }

    //    Limpa os textviews passados em parametro
    void clearValues(TextView... texts) {
        for (TextView text : texts) {
            text.setText("");
        }
    }

    //Definindo o que fazer quando o botao calculate for pressionado
    @OnClick(R.id.button_calculate)
    public void calculateAction(View view) {

        if (!(editValue.getText().toString().equals(""))) {
            calculate();
        } else {
            editValue.setError(getString(R.string.erro_campo_vazio));
        }
    }

    //    Faz as conversoes de valores e exibe nos textviews
    @SuppressLint("DefaultLocale")
    void calculate() {
        double value = Double.valueOf(editValue.getText().toString());

        textDollar.setText(String.format("%.2f", value * 3.88));
        textEuro.setText(String.format("%.2f", value * 4.45));
    }

    //    Ativa ou desativa o recurso que atualiza o valor da cotacao a medida que o usuario digita
    void activateOnUpdateListener(boolean activate) {
        if (activate) {
            editValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    btnCalculate.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!(editValue.getText().toString().equals(""))) {
                        calculate();
                    } else {
                        editValue.setError(getString(R.string.erro_campo_vazio));
                        clearValues(textDollar, textEuro);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    btnCalculate.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Log.i(TAG, "activateOnUpdateListener: not self updating");
        }
    }
}
