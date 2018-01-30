package com.example.roshanantonjohn.homepage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button benter,eenter,reset;
    EditText et_bud,et_exp;
    TextView totalbud,remaining,per;
    int budget,remain;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        progressBar=(ProgressBar)findViewById(R.id.progress);
        benter=(Button)findViewById(R.id.benter);
        eenter=(Button)findViewById(R.id.eenter);
        reset=(Button)findViewById(R.id.reset);

        et_bud=(EditText)findViewById(R.id.et_bud);
        et_exp=(EditText)findViewById(R.id.et_exp);

        totalbud=(TextView)findViewById(R.id.total);
        remaining=(TextView)findViewById(R.id.left);
        per=(TextView)findViewById(R.id.per);

        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);


        int bud=sharedPreferences.getInt("total",0);
        if(bud>0)
        {
            et_bud.setEnabled(true);
            benter.setEnabled(true);
            totalbud.setText(""+bud);
        }

        int rem=sharedPreferences.getInt("remain",0);
        remaining.setText(""+rem);

        progressBar.setProgress(Percentage(rem,bud));
        per.setText(Percentage(rem,bud)+"%");

        benter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_bud.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "enter budget", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    budget=Integer.valueOf(et_bud.getText().toString());
                    totalbud.setText(""+budget);
                    remaining.setText(""+budget);
                    et_bud.setEnabled(false);
                    benter.setEnabled(false);
                    et_bud.setHint("Enter Budget");
                    et_bud.setText("");
                    SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("total",budget);
                    editor.apply();

                    progressBar.setProgress(Percentage(budget,budget));
                    per.setText(Percentage(budget,budget)+"%");
                }

            }
        });

        eenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_exp.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "enter expense", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(Integer.valueOf(remaining.getText().toString())>=Integer.valueOf(et_exp.getText().toString()))
                    {
                        remain=Integer.valueOf(remaining.getText().toString())-Integer.valueOf(et_exp.getText().toString());

                        remaining.setText(""+remain);

                        progressBar.setProgress(Percentage(remain,budget));
                        per.setText(Percentage(remain,budget)+"%");
                        et_exp.setHint("Enter Expense");
                        et_exp.setText("");

                        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt("remain",remain);
                        editor.apply();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Your Amount exceeded from your budget", Toast.LENGTH_SHORT).show();
                    }

                    if(remaining.getText().toString().equals("0"))
                    {
                        Toast.makeText(MainActivity.this, "No remaining amount left", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_bud.setEnabled(true);
                benter.setEnabled(true);
                totalbud.setText("0");
                remaining.setText("0");
                per.setText("0%");
                progressBar.setProgress(0);
            }
        });


    }

    public int Percentage(int remain,int total)
    {
        int minu=total-remain;

        int x = (int)(((double)minu/(double)total) * 100);

        return x;
    }


}
