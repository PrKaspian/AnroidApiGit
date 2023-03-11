package com.example.androidapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvContacts;
    private ProgressBar progressBar;
    private Button btnAdd;
    private ContactApi contactApi;
    private ContactAdapter adapter;
    private List<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvContacts = findViewById(R.id.rvContacts);
        progressBar = findViewById(R.id.progressBar);
        contactApi = NetworkService.getInstance().getApi();
        btnAdd = findViewById(R.id.btnAdd);



        initListView();
        addListeners();


//        rvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Contact contact = (Contact) adapterView.getAdapter().getItem(i);
//                //Toast.makeText(MainActivity.this, c.getFirstName(), Toast.LENGTH_SHORT).show();
//                NetworkService.getInstance().getApi().delete(contact.getId()).enqueue(new Callback<Contact>() {
//                    @Override
//                    public void onResponse(Call<Contact> call, Response<Contact> response) {
//                        if (android.os.Build.VERSION.SDK_INT >= 11){
//                            recreate();
//                        } else {
//                            Intent intent = getIntent();
//                            finish();
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Contact> call, Throwable t) {
//
//                    }
//                });
//
//                return false;
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
        initListView();

    }

    private void addListeners() {
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateContactActivity.class));
        });
    }

    private void deleteItemById(int id){
        contactApi.delete(id).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (android.os.Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }
        });
    }


    private void initListView() {
        contactApi.getAll().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                adapter = new ContactAdapter(MainActivity.this, R.layout.contact_item, contacts, new ContactAdapter.ClickListener() {
                    @Override
                    public void onItemLongClick(int position, View v) {
                        Contact contact = contacts.get(position);
                        deleteItemById(contact.getId());
                    }
                });
                rvContacts.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        MainActivity.this,
                        RecyclerView.VERTICAL,
                        false
                );
                rvContacts.setLayoutManager(layoutManager);
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int id = (Integer) viewHolder.itemView.getTag();
                        deleteItemById(id);
                    }
                }).attachToRecyclerView(rvContacts);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("xxx", t.getMessage());
                Log.d("xxx", t.toString());

            }
        });
    }
}