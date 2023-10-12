package com.example.semana8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SensorRotacion extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private float[] rotationMatrix = new float[9];
    private float[] orientationValues = new float[3];
    private ImageView pelota;
    private float currentX = 0; // Posición X de la pelota
    private float currentY = 0; // Posición Y de la pelota

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_rotacion);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        pelota = findViewById(R.id.pelotaRoja);

        if (rotationSensor != null) {
            sensorManager.registerListener(sensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Handle the case where the rotation sensor is not available on the device.
        }
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                SensorManager.getOrientation(rotationMatrix, orientationValues);

                float rotationX = orientationValues[0];
                float rotationY = orientationValues[1];

                // Actualizar la posición de la pelota en función de la rotación
                updateBallPosition(rotationX, rotationY);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Handle accuracy changes if necessary.
        }
    };

    private void updateBallPosition(float rotationX, float rotationY) {
        // Ajusta la velocidad de desplazamiento según tus necesidades
        float speed = 5;

        // Calcula la nueva posición de la pelota en función de la rotación
        currentX += rotationX * speed;
        currentY += rotationY * speed;

        // Limita la posición dentro de los límites de la pantalla
        float maxX = getWindowManager().getDefaultDisplay().getWidth() - pelota.getWidth();
        float maxY = getWindowManager().getDefaultDisplay().getHeight() - pelota.getHeight();
        currentX = Math.max(0, Math.min(currentX, maxX));
        currentY = Math.max(0, Math.min(currentY, maxY));

        // Actualiza la posición de la pelota
        pelota.setX(currentX);
        pelota.setY(currentY);
    }
}