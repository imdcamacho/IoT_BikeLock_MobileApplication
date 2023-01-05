package com.example.iot_bikelock_mobileapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.iot_bikelock_mobileapplication.R;
import com.example.iot_bikelock_mobileapplication.data.LoginSharedPreferenceDataSource;
import com.example.iot_bikelock_mobileapplication.data.user.UserBackendDataSource;
import com.example.iot_bikelock_mobileapplication.databinding.ActivityCompleteRegistrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CompleteRegistrationActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 200;

    ActivityCompleteRegistrationBinding binding = null;
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompleteRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        retrieveStudentAndPopulate();
    }

    protected void setOnClickListeners() {
        binding.buttonSelectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromGallery();
            }
        });
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        binding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Exiting Application.", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    public void retrieveStudentAndPopulate(){
        UserBackendDataSource userDataSource = UserBackendDataSource.getInstance(getApplicationContext());
        LoginSharedPreferenceDataSource loginSharedPrefsDataSource = LoginSharedPreferenceDataSource.getInstance(getApplicationContext());
        String backendToken = loginSharedPrefsDataSource.getBackendAuthToken();
        try {
            userDataSource.retrieveUserFromBackend(
                    backendToken,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject userJson = response.getJSONObject("user");
                                JSONObject studentJson = userJson.getJSONObject("student");
                                String firstName = studentJson.getString("first_name");
                                String lastName = studentJson.getString("last_name");
                                String email = userJson.getString("email");
                                String fullName = firstName + " " + lastName;
                                binding.editEmail.setText(email);
                                binding.editTextTextPersonName.setText(fullName);
                                //    TODO finalize whether to allow editing name. remove if name editing is allowed.
                                binding.editTextTextPersonName.setEnabled(false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    null
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                int height = binding.imgBikePreview.getHeight();
                int width = binding.imgBikePreview.getWidth();
                binding.imgBikePreview.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, width,height,false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void submitForm() {
        UserBackendDataSource userDataSource = UserBackendDataSource.getInstance(getApplicationContext());
        LoginSharedPreferenceDataSource sharedPrefsDataSource = LoginSharedPreferenceDataSource.getInstance(getApplicationContext());
        String backendToken =sharedPrefsDataSource.getBackendAuthToken();
        String section = binding.editSection.getText().toString();
        String phone = binding.editPhoneNumber.getText().toString();
        if(selectedImage == null) {
            Toast.makeText(getApplicationContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
            return;
        }
        String imageBase64 = getImageBase64(selectedImage);
        try {
            userDataSource.submitCompleteRegistration(
                    backendToken,
                    section,
                    phone,
                    imageBase64,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent = new Intent(CompleteRegistrationActivity.this, AwaitingVerificationActivity.class);
                            startActivity(intent);
                            CompleteRegistrationActivity.this.finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private String getImageBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

}