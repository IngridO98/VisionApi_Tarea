package com.example.visionapi_tarea;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void hacercuadro(View view){
        //Hacer cuadrados
        ImageView imageView= findViewById(R.id.imgImgToProcess);
        //Button btnProcesar=(Button) findViewById(R.id.btnProcesar);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable=true;
        Bitmap bitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.amigos3,
                options);

        Paint rectanpaint=new Paint();
        rectanpaint.setStrokeWidth(8);
        rectanpaint.setColor(Color.MAGENTA);
        rectanpaint.setStyle(Paint.Style.STROKE);

        Bitmap temporal=Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas=new Canvas(temporal);
        canvas.drawBitmap(bitmap,0,0,null);

        FaceDetector faceDetector=new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .build();

        if(!faceDetector.isOperational()){
            Toast.makeText(MainActivity.this,"No se pudo configurar el detector facial en su dispositivo",Toast.LENGTH_SHORT).show();
            return;
        }

        Frame frame= new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faceSparseArray=faceDetector.detect(frame);

        for(int i=0;i<faceSparseArray.size();i++){
            Face face=faceSparseArray.valueAt(i);
            float x1=face.getPosition().x;
            float y1=face.getPosition().y;
            float x2=x1+face.getWidth();
            float y2=y1+face.getHeight();
            RectF rectF= new RectF(x1,y1,x2,y2);
            canvas.drawRoundRect(rectF,2,2,rectanpaint);
        }
        imageView.setImageDrawable(new BitmapDrawable(getResources(), temporal));
    }

}