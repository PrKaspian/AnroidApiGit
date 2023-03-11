package com.example.androidapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private int resource;
    private List<Contact> contacts;
    private final ClickListener clickListener;

    public ContactAdapter(Context context, int resource, List<Contact> contacts, ClickListener clickListener) {
        this.context = context;
        this.resource = resource;
        this.contacts = contacts;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(resource, parent, false);
        ContactAdapter.ContactViewHolder contactViewHolder = new ContactAdapter.ContactViewHolder(container);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        String fullName = contacts.get(position).getFirstName() + " " + contacts.get(position).getLastName();
        String phone = contacts.get(position).getPhone();
        holder.tvFullName.setText(fullName);
        holder.tvPhone.setText(phone);
        holder.itemView.setTag(contacts.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView tvFullName;
        TextView tvPhone;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            tvFullName = itemView.findViewById(R.id.tvFulName);
            tvPhone = itemView.findViewById(R.id.tvPhone);

        }

        @Override
        public boolean onLongClick(View v) {
            int position = getBindingAdapterPosition();
            if (position >= 0) {
                clickListener.onItemLongClick(position, v);
                return true;
            }
            return false;
        }
    }
    public interface ClickListener {
        void onItemLongClick(int position, View v);
    }
//    private Context context;
//    private int resource;
//    private List<Contact> contacts;
//    private LayoutInflater inflater;
//
//
//    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> contacts) {
//        super(context, resource, contacts);
//        this.context = context;
//        this.contacts = contacts;
//        this.resource = resource;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View item = inflater.inflate(resource, parent, false);
//        TextView tvFullName = item.findViewById(R.id.tvFulName);
//        TextView tvPhone = item.findViewById(R.id.tvPhone);
//        Contact contact = contacts.get(position);
//        tvFullName.setText(contact.getFirstName() + " " + contact.getLastName());
//        tvPhone.setText(contact.getPhone());
//
//
//        return item;
//    }

}
