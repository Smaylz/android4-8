package com.arworld.przanyatie_4;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // константы для ID пунктов меню
    final int MENU_ALPHA_ID = 1;
    final int MENU_SCALE_ID = 2;
    final int MENU_TRANSLATE_ID = 3;
    final int MENU_ROTATE_ID = 4;
    final int MENU_COMBO_ID = 5;

    ImageView imageView1;
    ImageView imageView2;
    Button buttonAlpha;
    Button buttonCombo;
    Button buttonRotate;
    Button buttonScale;
    Button buttonTrans;
    Button buttonComboGeneral;
    Button buttonComboInSeries;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        // регистрируем контекстное меню для компонентов
        registerForContextMenu(imageView1);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Read", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);
        }

        buttonAlpha = (Button)findViewById(R.id.buttonAlpha);
        buttonCombo = (Button)findViewById(R.id.buttonCombo);
        buttonRotate = (Button)findViewById(R.id.buttonRotate);
        buttonComboGeneral = (Button)findViewById(R.id.buttonComboGeneral);
        buttonScale = (Button)findViewById(R.id.buttonScale);
        buttonTrans = (Button)findViewById(R.id.buttonTrans);
        buttonComboInSeries = (Button)findViewById(R.id.buttonComboInSeries);

        buttonTrans.setOnClickListener(this);
        buttonScale.setOnClickListener(this);
        buttonCombo.setOnClickListener(this);
        buttonRotate.setOnClickListener(this);
        buttonAlpha.setOnClickListener(this);

        buttonComboGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mycombo);
                imageView1.startAnimation(anim);
                imageView2.startAnimation(anim);
            }
        });

        buttonComboInSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mycombo);
                final Animation animFor2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mycombo);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imageView2.startAnimation(animFor2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView1.startAnimation(anim);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.imageView1:
                // добавляем пункты
                menu.add(0, MENU_ALPHA_ID, 0, "alpha");
                menu.add(0, MENU_SCALE_ID, 0, "scale");
                menu.add(0, MENU_TRANSLATE_ID, 0, "translate");
                menu.add(0, MENU_ROTATE_ID, 0, "rotate");
                menu.add(0, MENU_COMBO_ID, 0, "combo");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation anim = null;
        // определяем какой пункт был нажат
        switch (item.getItemId()) {
            case MENU_ALPHA_ID:
                // создаем объект анимации из файла anim/myalpha
                anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case MENU_SCALE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
                break;
            case MENU_TRANSLATE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.mytrans);
                break;
            case MENU_ROTATE_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                break;
            case MENU_COMBO_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;
        }
        // запускаем анимацию для компонента tv
        imageView1.startAnimation(anim);
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Animation anim = null;
        switch (v.getId()) {
            case R.id.buttonAlpha:
                // создаем объект анимации из файла anim/myalpha
                anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case R.id.buttonCombo:
                anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;
            case R.id.buttonRotate:
                anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                break;
            case R.id.buttonScale:
                anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
                break;
            case R.id.buttonTrans:
                anim = AnimationUtils.loadAnimation(this, R.anim.mytrans);
                break;
            case R.id.buttonComboGeneral:
                anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;
        }
        imageView2.startAnimation(anim);
    }
}