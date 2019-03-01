package com.example.rhkdg.sharethetrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhkdg.sharethetrip.models.Post;
import com.example.rhkdg.sharethetrip.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private ImageButton mSelectImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    //image upload
    private static final int GALLERY_REQUEST = 999;
    private Uri mImageUri;

    //edittext
    private EditText mTitleField;
    private EditText mBodyField;
    private EditText mNationField;
    private EditText mDateField;
    private EditText mPriceField;


    //text
    //private TextView mNationtext;
   // private TextView mDatetext;
    private TextView mPricetext;
    //private TextView mAddresstext;
    private TextView mAddressField;

    //button
    private FloatingActionButton mSubmitButton;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        //image upload
        bindViews();


        //maintext
        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);

        //edittext
        mNationField = findViewById(R.id.nationet);
        mDateField = findViewById(R.id.dateet);
        mPriceField = findViewById(R.id.priceed);
        mAddressField = findViewById(R.id.addressed);

        //text
        //mNationtext = findViewById(R.id.nationtext);
        //mDatetext = findViewById(R.id.datetext);
        mPricetext = findViewById(R.id.pricetext);
        //mAddresstext = findViewById(R.id.addresstext);

        //Button
        mSubmitButton = findViewById(R.id.fabSubmitPost);

        clickEvents();

    }

    // [Start upload image]
    private void bindViews() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mSelectImage = (ImageButton) findViewById(R.id.uploadimg);
    }

    private void clickEvents() {
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    // Start submit
    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();
        final String nation = mNationField.getText().toString();
        final String date = mDateField.getText().toString();
        final String price = mPriceField.getText().toString();
        final String address = mAddressField.getText().toString();
        //final String nationtext = mNationtext.getText().toString();
        //final String datetext = mDatetext.getText().toString();
        final String pricetext = mPricetext.getText().toString();
        //final String addresstext = mAddresstext.getText().toString();


        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Price is required
        if (TextUtils.isEmpty(price)) {
            mPriceField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        StorageReference filepath = mStorageRef.child("photos").child(Objects.requireNonNull(mImageUri.getLastPathSegment()));
        filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Task<Uri> downloadUrl = filepath.getDownloadUrl();
                downloadUrl.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        final String img_url = task.getResult().toString();
                        writeNewPost(title, body, nation, date, pricetext, price, address,img_url);
                    }
                });
            }
        });
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String title, String body, String nation, String date, String pricetext, String price, String address, String IMAGE) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        final DatabaseReference newPost = mDatabase.child("posts").push();//cret uniquid
        final String postId = newPost.getKey();
        mDatabase.child("posts").child(postId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userId = getUid();
                        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // Get user value
                                        User user = dataSnapshot.getValue(User.class);

                                        Post post = new Post(userId, user.username, title, body, nation, date, pricetext, price, address, IMAGE);
                                        Map<String, Object> postValues = post.toMap();

                                        Map<String, Object> childUpdates = new HashMap<>();
                                        childUpdates.put("/posts/" + postId, postValues);
                                        childUpdates.put("/user-posts/" + userId + "/" + postId, postValues);

                                        mDatabase.updateChildren(childUpdates);

                                        // Finish this Activity, back to the stream
                                        setEditingEnabled(true);
                                        finish();
                                        // [END_EXCLUDE]
                                    }

                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                        // [START_EXCLUDE]
                                        setEditingEnabled(true);
                                        // [END_EXCLUDE]
                                    }
                                });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                }
        );
    }
    // [END write_fan_out]

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mSelectImage.setImageURI(mImageUri);
        }
    }
}
