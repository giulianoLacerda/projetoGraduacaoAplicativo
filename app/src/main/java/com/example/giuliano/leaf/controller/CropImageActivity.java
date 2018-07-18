package com.example.giuliano.leaf.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.giuliano.leaf.R;
import com.example.giuliano.leaf.api.Client;
import com.example.giuliano.leaf.api.Service;
import com.example.giuliano.leaf.model.Classification;
import com.example.giuliano.leaf.model.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import me.echodev.resizer.Resizer;
import me.echodev.resizer.util.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropImageActivity extends AppCompatActivity {

    Uri uri;
    File file;
    private String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        file = (File) getIntent().getSerializableExtra("FILE");
        cookie = (String) getIntent().getSerializableExtra("COOKIE");
        System.out.println(cookie);


        // Start pick image activity with chooser.
        uri = Uri.fromFile(new File(file.getAbsolutePath()));
        Log.e("URI",uri.toString());
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(28,28)
                .setAutoZoomEnabled(false)
                .setOutputUri(uri)
                .start(this);

    }

    /*
    public void onSelectImageClick(View view) {
        CropImage.activity(null)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());

                //Log.d("Path",result.getUri().getPath());
                //Toast.makeText(
                  //      this, "resultPath" + result.getUri().getPath(), Toast.LENGTH_LONG)
                    //    .show();


                //File fileImage = new File(result.getUri().getPath());
                //Log.d("Path",fileImage.getAbsolutePath());

                //Log.e("Redimensionando","redimensionando");
                    /*File resizedImage = new Resizer(this)
                            .setTargetLength(28)
                            .setQuality(100)
                            .setOutputFormat("PNG")
                            .setOutputDirPath("/storage/emulated/0/Android/data/com.example.giuliano.leaf/files/")
                            .setSourceImage(fileImage)
                            .getResizedFile();*/


                File file = new File(result.getUri().getPath());
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("newPicture", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "filename");

                Client Client = new Client();
                Service apiService = Client.getClient().create(Service.class);
                Call<Classification> req = apiService.postImage(name,body,cookie);
                req.enqueue(new Callback<Classification>() {
                    @Override
                    public void onResponse(Call<Classification> call, Response<Classification> response) {
                        Log.e("Respondeu","RESPONDEU");
                        Intent it = new Intent(CropImageActivity.this,MainActivity.class);
                        //it.putExtra("FILE",mFile);
                        startActivity(it);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Classification> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


                /*Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();*/
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
