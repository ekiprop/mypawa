package com.matrix.mypawa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PowerRecyclerAdapter extends RecyclerView.Adapter<PowerRecyclerAdapter.PowerHolder> {
    private List<Power> powers = new ArrayList<>();

    @NonNull
    @Override
    public PowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.power_list, parent, false);
        return new PowerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PowerHolder holder, int position) {
        Power currentPower = powers.get(position);
        holder.textViewTitle.setText(currentPower.getName());
        holder.textViewDescription.setText(currentPower.getPhone_no());


        if (currentPower.getDevice0() == null && currentPower.getDevice2() == null && currentPower.getDevice3() == null){
            holder.textViewPriority.setText(currentPower.getDevice1());
        } else if (currentPower.getDevice0() == null && currentPower.getDevice1() == null && currentPower.getDevice3() == null){
            holder.textViewPriority.setText(currentPower.getDevice2());
        }else if (currentPower.getDevice0() == null && currentPower.getDevice1() == null && currentPower.getDevice2() == null){
            holder.textViewPriority.setText(currentPower.getDevice3());
        }else {
            if (currentPower.getDevice0().equalsIgnoreCase("TF")){
                holder.textViewPriority.setText("OFF");
                //holder.textViewPriority.setText(currentPower.getDevice0());
            }else  if (currentPower.getDevice0().equalsIgnoreCase("TO")){
                holder.textViewPriority.setText("ON");
                //holder.textViewPriority.setText(currentPower.getDevice0());
            }
        }


    }

    @Override
    public int getItemCount() {
        return powers.size();
    }

    public void setPowers(List<Power> powers) {
        this.powers = powers;
        notifyDataSetChanged();
    }

    class PowerHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public PowerHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
