package com.project.gear2care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fb;
    public static DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<ServiceCheckList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_list_activity);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("ServiceCheckList");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ServiceCheckList serviceCheckList = dataSnapshot.getValue(ServiceCheckList.class);
                    list.add(serviceCheckList);
//                    Object value = dataSnapshot.getValue();
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference.push().setValue(checkListItem.getText().toString());
        fb = (FloatingActionButton) findViewById(R.id.f_add);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                final AlertDialog.Builder alert = new AlertDialog.Builder(CheckListActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog, null);
                final EditText editText = (EditText) mview.findViewById(R.id.check_list_item);
                Button btn = (Button) mview.findViewById(R.id.add_item);
                alert.setView(mview);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                final EditText checkListItem = mview.findViewById(R.id.check_list_item);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.clear();

                        String service_name = checkListItem.getText().toString();

                        ServiceCheckList serviceCheckList = new ServiceCheckList(service_name);

                        databaseReference.push().setValue(serviceCheckList);
                        Toast.makeText(CheckListActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();


                    }
                });

                alertDialog.show();
            }
        });

    }
}