package com.project.gear2care;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ServiceCheckList> list;

    public MyAdapter(Context context, ArrayList<ServiceCheckList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
}

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ServiceCheckList serviceCheckList=list.get(position);
            holder.service_name.setText(serviceCheckList.getServiceName());

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int x=holder.getAdapterPosition();


                    CharSequence[] delete={
                            "Delete"
                    };


                    AlertDialog.Builder alert=new AlertDialog.Builder(context);
                    alert.setItems(delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which==0){
                                    list.remove(x);
                                    notifyItemRemoved(x);
                            }
                        }
                    });
                    alert.create().show();
                    return false;
                }
            });



                }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView service_name;
        Button nameBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name=itemView.findViewById(R.id.service_name);

            }

        }
    }

