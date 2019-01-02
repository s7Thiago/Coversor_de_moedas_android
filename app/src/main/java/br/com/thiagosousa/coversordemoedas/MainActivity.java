package br.com.thiagosousa.coversordemoedas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //valor das moedas
    double dollar = 3.88,
    euro = 4.45;

//    anim
    static final  int ANIMATIONS_DURATION = 550;

    //    Views
    @BindView(R.id.textview_dollarvalue)
    TextView textDollar;
    @BindView(R.id.textview_eurovalue)
    TextView textEuro;
    @BindView(R.id.edit_value)
    EditText editValue;
    @BindView(R.id.converterResultsContainer)
    View converterResultsContainer;

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


    //    Faz as conversoes de valores e exibe nos textviews
    @SuppressLint("DefaultLocale")
    void calculate() {
        double value = Double.valueOf(editValue.getText().toString());

        textDollar.setText(String.format("%.2f", value * dollar));
        textEuro.setText(String.format("%.2f", value * euro));
    }

    //    Ativa ou desativa o recurso que atualiza o valor da cotacao a medida que o usuario digita
    void activateOnUpdateListener(boolean activate) {
        if (activate) {
            editValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!(editValue.getText().toString().equals(""))) {

                        if (editValue.getText().toString().length() <= 19) {
                            calculate();

//                        Reposicionando campo de texto
                            float textSize = (float) editValue.getText().toString().length();
                            editValue.setX((float) (editValue.getX() + (editValue.getX() * 0.5)));

                            float containerY = converterResultsContainer.getY();


//                        Adaptando o tamanho do texto

                            if (textSize <= 9) {
                                textDollar.animate()
                                        .scaleX((float) (8 - (textSize * 0.67)))
                                        .scaleY((float) (8 - (textSize * 0.67)))
                                        .setDuration(ANIMATIONS_DURATION)
                                        .setInterpolator(new AnticipateOvershootInterpolator())
                                        .start();

                                textEuro.animate()
                                        .scaleX((float) (8 - (textSize * 0.67)))
                                        .scaleY((float) (8 - (textSize * 0.67)))
                                        .setDuration(ANIMATIONS_DURATION)
                                        .setInterpolator(new AnticipateOvershootInterpolator())
                                        .start();
                            }

                        } else {
                            editValue.setSelected(false);
                        }

                    } else {
                        editValue.setError(getString(R.string.erro_campo_vazio));
                        clearValues(textDollar, textEuro);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            Log.i(TAG, "activateOnUpdateListener: not self updating");
        }
    }
}
