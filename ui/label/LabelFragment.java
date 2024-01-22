package com.example.myfinall.ui.label;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.myfinall.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LabelFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_label,container,false);

        LinearLayout linearLayout=root.findViewById(R.id.linearLayout);
        EditText labelEditText =root.findViewById(R.id.label);
        Button addButton = root.findViewById(R.id.added);

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference labelRef= db.collection("label");

        labelRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document :task.getResult()){
                        Label label =document.toObject(Label.class);
                        CheckBox checkBox=new CheckBox(getActivity());
                        checkBox.setText(label.getLabelText());
                        linearLayout.addView(checkBox);
                    }
                }else {
                    Toast.makeText(getActivity(),"Hata oluştu.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String labelText = labelEditText.getText().toString();

                labelRef.whereEqualTo("labelText",labelText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult().size()>0) {
                            Toast.makeText(getActivity(), "label zaten ekli", Toast.LENGTH_SHORT).show();
                        }else {
                            labelRef.add(new Label(labelText)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity(),"label ekleme başarılı",Toast.LENGTH_SHORT).show();

                                    CheckBox checkBox=new CheckBox(getActivity());
                                    checkBox.setText(labelText);
                                    linearLayout.addView(checkBox);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Ekleme başarısız",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        return root;
    }
}
