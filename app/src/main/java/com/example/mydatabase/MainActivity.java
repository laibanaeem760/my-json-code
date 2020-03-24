package com.example.mydatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Documentation;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.View;

import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore objectFirebaseFirestore;

    Dialog objectDialog;
     EditText documentET,cityNameET;
TextView cityDetailsET;
DocumentReference  objectDocumentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objectFirebaseFirestore=FirebaseFirestore.getInstance();
        objectDialog=new Dialog(this);

        objectDialog.setContentView(R.layout.please_wait);
        documentET=findViewById(R.id.documentIDET);

        cityNameET=findViewById(R.id.cityNameET);
        cityDetailsET=findViewById(R.id.cityDetailsET);
    }

    public void addValues(View v)
    {
        try
        {
            if(!documentET.getText().toString().isEmpty() && !cityNameET.getText().toString().isEmpty()
                    && !cityDetailsET.getText().toString().isEmpty()) {
                objectDialog.show();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("city_name", cityNameET.getText().toString());

                objectMap.put("city_details", cityDetailsET.getText().toString());
                objectFirebaseFirestore.collection("NewCities")
                        .document(documentET.getText().toString()).set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Fails to add data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            objectDialog.dismiss();
            Toast.makeText(this, "addValues:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getValuesFromFirebase(View v)
    {

        try {
            if(!documentET.getText().toString().isEmpty() && !cityNameET.getText().toString().isEmpty()
                    && !cityDetailsET.getText().toString().isEmpty()
            );
            objectDocumentReference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String cityName=documentSnapshot.getString( "CityName");
                            String CityDetails=documentSnapshot.getString( "CityDetails");
                            cityDetailsET.setText
                                    ("Name:"+cityName+"\n"+"CityDetails:+"CityDetails+"\n"

                            );
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "fails to get values back", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}
