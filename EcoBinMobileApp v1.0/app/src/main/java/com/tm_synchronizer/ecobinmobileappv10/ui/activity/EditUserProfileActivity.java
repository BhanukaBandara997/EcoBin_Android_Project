package com.tm_synchronizer.ecobinmobileappv10.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;
import com.tm_synchronizer.ecobinmobileappv10.R;
import com.tm_synchronizer.ecobinmobileappv10.network.ServerConnection;
import com.tm_synchronizer.ecobinmobileappv10.ui.fragment.DashboardFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfileActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    CircleImageView userProfile;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private static int RESULT_LOAD_IMG = 0;
    String imgDecodableString;
    EditText txtusername, txtaddress, txtemailaddress, txtmobile;
    Button btnsave;
    private ServerConnection serverConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_user_profile);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.edit_profile_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        circleImageView = findViewById(R.id.add_new_profile_pic_button);
        userProfile = findViewById(R.id.user_profile);
        txtusername = findViewById(R.id.username_editText);
        txtaddress = findViewById(R.id.address_editText);
        txtemailaddress = findViewById(R.id.email_editText);
        txtmobile = findViewById(R.id.mobile_no_editText);

        txtusername.setText(DashboardFragment.userName);
        txtmobile.setText(DashboardFragment.mobileNo);
        txtemailaddress.setText(DashboardFragment.emailAddress);
        txtaddress.setText(DashboardFragment.address);


        btnsave = findViewById(R.id.create_button);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

    }

    private void editProfile() {
        Toast.makeText(EditUserProfileActivity.this,"clicked edit",Toast.LENGTH_SHORT).show();
        String oldmail = LoginActivity.logemail;
        String un = txtusername.getText().toString();
        String address = txtaddress.getText().toString();
        String email = txtemailaddress.getText().toString();
        String mobile = txtmobile.getText().toString();

        JSONObject editedprofileobject = new JSONObject();
        try {
            editedprofileobject.put("email", oldmail);
            editedprofileobject.put("un",un);
            editedprofileobject.put("address",address);
            editedprofileobject.put("nemail",email);
            editedprofileobject.put("mobile",mobile);
            JsonObjectRequest profileeditrequest=new JsonObjectRequest(Request.Method.PUT, "https://ecobintest.herokuapp.com/api/transact/echoupdate", editedprofileobject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String msg=response.getString("msg");
                        if(msg.equals("ok")){
                            Toast.makeText(EditUserProfileActivity.this,"Successfully Updated Profile...",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditUserProfileActivity.this,"Failed To Updated Profile...",Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            serverConnection.getRequestQueue().add(profileeditrequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if (resultCode == Activity.RESULT_OK) {

                if (requestCode == REQUEST_CAMERA) {

                    Bundle bundle = data.getExtras();
                    final Bitmap bmp = (Bitmap) bundle.get("data");
                    userProfile.setImageBitmap(bmp);

                }
                // When an Image is picked
                if (requestCode == SELECT_FILE) {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    ImageView imgView = findViewById(R.id.user_profile);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inSampleSize = calculateInSampleSize(options, 500, 500);
                    options.inJustDecodeBounds = false;
                    Bitmap smallBitmap = BitmapFactory.decodeFile(imgDecodableString);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    smallBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    // Set the Image in ImageView after decoding the String
                    Bitmap bmp = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                    imgView.setImageBitmap(Bitmap.createScaledBitmap(bmp, imgView.getWidth(), imgView.getHeight(), false));

                }
            }
            super.onActivityResult(requestCode, resultCode, data);

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        Intent intent = new Intent(EditUserProfileActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(EditUserProfileActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return false;
    }

    private void SelectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditUserProfileActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();


    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
