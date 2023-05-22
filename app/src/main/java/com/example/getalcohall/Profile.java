package com.example.getalcohall;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class
Profile extends Fragment {
    TextView profileName, profileEmail,profileAge, profileAddress,profilePassword;
    TextView Name, Email, Age, Address, Password;
    EditText editName, editAge, editAddress, editPassword;
    String fname, mail,age,address,pass;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Map<String, String> userMap;
    private String email;
    private String userid;
    private static final String USERS = "UserInfo";
    String NAMEs="who are you", EMAIL="Whats you Email?";
    FirebaseStorage storage;
    StorageReference storageReference;
    String updateNameData,updateMailData,updateAgeData,updateAddressData,updatePassData;

    ImageView imageView;
    FloatingActionButton buttonPicker,editButton;
    Button UpdateBtn;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    Uri selectImageUri;
    Utilts us;
    Update UpdateData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());

        us = new Utilts();

        FirebaseAuth userauth = FirebaseAuth.getInstance();
        userid =userauth.getCurrentUser().getUid();






        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (keyId.getKey().equals(userid)) {
                        fname = keyId.child("fullName").getValue(String.class);
                        System.out.println(fname);
                        mail = keyId.child("UEmail").getValue(String.class);
                        System.out.println(mail);
                        pass = keyId.child("Password").getValue(String.class);
                        System.out.println(pass);
                        age = keyId.child("age").getValue(String.class);
                        System.out.println(age);
                        address = keyId.child("address").getValue(String.class);
                        System.out.println(address);


                        NAMEs = fname;
                        profileName.setText(fname);
                        profileEmail.setText(mail);
                        if(age!=null){
                            profileAge.setText(age);
                        }
                        else{
                            profileAge.setVisibility(View.GONE);
                        }
                        if(address!=null){
                            profileAddress.setText(address);
                        }
                        else{
                            profileAddress.setVisibility(View.GONE);
                        }
                        profilePassword.setText(pass);
                        EMAIL = mail;

//                        workplace = keyId.child("workplace").getValue(String.class);
//                        phone = keyId.child("phone").getValue(String.class);
//                        facebook = keyId.child("facebook").getValue(String.class);
//                        twitter = keyId.child("twitter").getValue(String.class);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        profileName = view.findViewById(R.id.profile_name_extract);
        profileEmail = view.findViewById(R.id.profile_email_extract);
        profileAge = view.findViewById(R.id.profile_Age_extract);
        profileAddress = view.findViewById(R.id.profile_Address_extract);
        buttonPicker = (FloatingActionButton) view.findViewById(R.id.SelectImg22);
        imageView = view.findViewById(R.id.Dimage);
        UpdateBtn = (Button) view.findViewById(R.id.update_profile_btn);
        editButton = (FloatingActionButton) view.findViewById(R.id.Edit);
        profilePassword = view.findViewById(R.id.profile_password_extract);


        Name = (TextView) view.findViewById(R.id.profile_name);
        Email = (TextView) view.findViewById(R.id.profile_email);
        Age = (TextView) view.findViewById(R.id.profile_Age);
        Address = view.findViewById(R.id.profile_Address);
        Password = view.findViewById(R.id.profile_password);

        editName = view.findViewById(R.id.profile_name_extract_editText);
        editAge = view.findViewById(R.id.profile_Age_extract_editText);
        editAddress = view.findViewById(R.id.profile_Address_extract_editText);
        editPassword = view.findViewById(R.id.profile_password_extract_editText);

        Name.setVisibility(View.GONE);
        Email.setVisibility(View.GONE);
        Age.setVisibility(View.GONE);
        Address.setVisibility(View.GONE);
        Password.setVisibility(View.GONE);
        editName.setVisibility(View.GONE);
        editAddress.setVisibility(View.GONE);
        editAge.setVisibility(View.GONE);
        editPassword.setVisibility(View.GONE);
        profilePassword.setVisibility(View.GONE);


        buttonPicker.setVisibility(View.GONE);
        UpdateBtn.setVisibility(View.GONE);

        buttonPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasStoragePermission(getContext())){
                    openImageChooser();
                    System.out.println(selectImageUri);
                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) getContext()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        });
        FirebaseUser U = FirebaseAuth.getInstance().getCurrentUser();

        if(U.getPhotoUrl()!=null){
            Glide.with(this).load(U.getPhotoUrl()).into(imageView);
            System.out.println(Glide.with(this).load(U.getPhotoUrl())+"  laal");
//            imageView.setImageURI();
            System.out.println(U.getPhotoUrl());
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonPicker.setVisibility(View.VISIBLE);
                UpdateBtn.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);


                Name.setVisibility(View.VISIBLE);
                Email.setVisibility(View.VISIBLE);
                Age.setVisibility(View.VISIBLE);
                Address.setVisibility(View.VISIBLE);
                editName.setVisibility(View.VISIBLE);
                editAddress.setVisibility(View.VISIBLE);
                editAge.setVisibility(View.VISIBLE);

                editName.setText(profileName.getText().toString());
                editAddress.setText(profileAddress.getText().toString());
                editAge.setText(profileAge.getText().toString());
                editPassword.setText(profilePassword.getText().toString());

                profileAge.setVisibility(View.GONE);
                profileName.setVisibility(View.GONE);
                profileAddress.setVisibility(View.GONE);
                profilePassword.setVisibility(View.GONE);

            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference().child("ProfileImages").child(userid+".jpeg");
                if(selectImageUri!=null){
                    storageReference.putFile(selectImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();

                                    user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Profile image failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure", e.getCause());
                        }
                    });
                }

                updateNameData = editName.getText().toString();
                updateMailData = profileEmail.getText().toString();
                updateAgeData = editAge.getText().toString();
                updateAddressData = editAddress.getText().toString();
                updatePassData = editPassword.getText().toString();

                database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference().child(USERS);

                mAuth = FirebaseAuth.getInstance();
                String userID = mAuth.getCurrentUser().getUid();

                HashMap hashMap = new HashMap();
                hashMap.put("fullName",updateNameData);
                hashMap.put("UEmail",updateMailData);
                hashMap.put("age",updateAgeData);
                hashMap.put("address",updateAddressData);
                hashMap.put("password",updatePassData);

                mDatabase.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Update Successful!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Update Unsuccessful!", Toast.LENGTH_SHORT).show();
                    }
                });
                getFragmentManager().beginTransaction().detach(Profile.this).attach(Profile.this).commit();

                Name.setVisibility(View.GONE);
                Email.setVisibility(View.GONE);
                Age.setVisibility(View.GONE);
                Address.setVisibility(View.GONE);
                Password.setVisibility(View.GONE);
                editName.setVisibility(View.GONE);
                editAddress.setVisibility(View.GONE);
                editAge.setVisibility(View.GONE);
                editPassword.setVisibility(View.GONE);


                buttonPicker.setVisibility(View.GONE);
                UpdateBtn.setVisibility(View.GONE);

                profileName.setVisibility(View.VISIBLE);
                profileAge.setVisibility(View.VISIBLE);
                profileAddress.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.VISIBLE);



            }
        });


        return view;
    }


    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 100){
                selectImageUri = data.getData();
                System.out.println(selectImageUri+"image");
                if(selectImageUri != null){
                    imageView.setImageURI(selectImageUri);

                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int buffersize = 1024;
        byte[] buffer = new byte[buffersize];
        int len =0;
        while ((len =  inputStream.read(buffer)) != -1){
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }


}