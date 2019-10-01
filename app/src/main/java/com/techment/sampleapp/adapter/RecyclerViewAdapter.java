package com.techment.sampleapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techment.sampleapp.R;
import com.techment.sampleapp.helper.Utility;
import com.techment.sampleapp.model.ResponseItems;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<ResponseItems> data;
    private DataAdapterCallback dataAdapterCallback;

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_data_item,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ResponseItems items = data.get(position);
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvCreatedAt.setText(data.get(position).getCreated_at());
        holder.toggleSwitch.setChecked(data.get(position).isChecked());
        /*switch toggle listener*/
        holder.toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                items.setChecked(b);
                dataAdapterCallback.changeTitle(Utility.getCheckedCount(data));
            }
        });

        holder.linLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*this will handle the toggle switch listner if clicked from the entire layout*/
                holder.toggleSwitch.setChecked(!holder.toggleSwitch.isChecked());
            }
        });
    }

    @Inject
    public RecyclerViewAdapter(DataAdapterCallback dataAdapterCallback){
        this.dataAdapterCallback = dataAdapterCallback;
        data = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvCreatedAt;
        private Switch toggleSwitch;
        private LinearLayout linLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            toggleSwitch = itemView.findViewById(R.id.toggleSwitch);
            linLayout = itemView.findViewById(R.id.linLayout);
        }
    }

    public interface DataAdapterCallback{
        void changeTitle(String dataCount);
    }

    public void setData(int pageSize, List<ResponseItems> data){
        if(pageSize==1){
            this.data.clear();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
