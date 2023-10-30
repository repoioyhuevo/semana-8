package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button btnDescargar;
    ImageView imagen;
    Button sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDescargar = findViewById(R.id.descargarImagen);
        imagen = findViewById(R.id.imagen);
        sensores = findViewById(R.id.sensorRotacion);

        sensores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorRotacion.class);
                startActivity(intent);
            }
        });

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = loadImageFromNetwork("https://www.bing.com/images/search?view=detailV2&ccid=DBwU%2frDU&id=C893ED626A8682FC9AF860374F39DF50CA7B5A69&thid=OIP.DBwU_rDUPkrBoukidKV6jAHaEo&mediaurl=https%3a%2f%2fi.pinimg.com%2foriginals%2ffd%2fde%2fb0%2ffddeb0ead5a0837214ac0c9ab5616990.jpg&cdnurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.0c1c14feb0d43e4ac1a2e92274a57a8c%3frik%3daVp7ylDfOU83YA%26pid%3dImgRaw%26r%3d0&exph=2400&expw=3840&q=imagen+tanke&simid=608015345027846143&FORM=IRPRST&ck=CAAB0561B8B802547E12AEA8F19AD0BE&selectedIndex=0");
                        imagen.post(new Runnable() {
                            @Override
                            public void run() {
                                imagen.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private Bitmap loadImageFromNetwork (String imageURL){
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}