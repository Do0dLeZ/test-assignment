package com.example.kolomiiets.technicalassignment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kolomiiets.technicalassignment.model.LoadPersons;
import com.example.kolomiiets.technicalassignment.model.Person;
import com.example.kolomiiets.technicalassignment.presenter.MainPresenter;
import com.example.kolomiiets.technicalassignment.view.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView {

    //Useful data
    private static final String TAG = "PERMISSION";

    //UI
    Button loadBtn;
    TextView successLabel;
    TextView unSuccessLabel;
    LinearLayout infoSection;
    TextView infoTV;
    Button matchBtn;
    TextView resultView;
    //For file
    private String filePath;
    final int REQUEST_CODE_FOR_FILE_PATH = 1;

    //Presenters
    MainPresenter mainPresenter;

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted!");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked!");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //*******************************Btn click listeners****************************************
    private View.OnClickListener loadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isReadStoragePermissionGranted()) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, REQUEST_CODE_FOR_FILE_PATH);
            }
        }
    };

    private View.OnClickListener matchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainPresenter.matchPersons(null);
        }
    };
    //*****************************Btn c. l. ***************************************************


    //***************************Open dialog to select file and get results*********************
    //*************************************Start load Persons***********************************
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_FOR_FILE_PATH && data != null) {
            this.filePath = data.getDataString();
            mainPresenter.loadPersons();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //********************************End of loading persons************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mainPresenter = new MainPresenter(this);

        loadBtn = findViewById(R.id.load_file_btn);
        loadBtn.setOnClickListener(loadClickListener);

        matchBtn = findViewById(R.id.count_matches);
        matchBtn.setOnClickListener(matchClickListener);

        successLabel = findViewById(R.id.success_counter);
        unSuccessLabel = findViewById(R.id.unsuccess_counter);

        infoSection = findViewById(R.id.info_layout);
        infoTV = findViewById(R.id.info_text_result_download);

        resultView = findViewById(R.id.results);
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public Person getPerson() {
        return null;
    }

    @Override
    public void showInfo(int successfullyLoaded) {
        successLabel.setText(String.valueOf(successfullyLoaded));
        if (successfullyLoaded > 0) {
            matchBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showResults(String result) {
        resultView.setText(result);
    }
}
