package com.example.tiket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private TiketAdapter tiketAdapter;
    private List<Tiket> tiketList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TiketAdapter TiketAdapter = new TiketAdapter(tiketList, this);
        recyclerView.setAdapter(tiketAdapter);

        // Set the MainActivity reference in the adapter
        tiketAdapter.setMainActivity(this);

        findViewById(R.id.btnAddTicket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTiketDialog();
            }

        });

        fetchUsers();
    }

    private void showAddTiketDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Tike");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_tiket, null);
        final EditText etNamaAcara = view.findViewById(R.id.etNamaAcara);
        final EditText etTanggal = view.findViewById(R.id.etTanggal);
        final EditText etWaktu = view.findViewById(R.id.etWaktu);
        final EditText etHarga = view.findViewById(R.id.etHarga);
        final EditText etSisaTiket = view.findViewById(R.id.etSisaTiket);

        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String namaAcara = etNamaAcara.getText().toString();
                String tanggal = etTanggal.getText().toString();
                String waktu = etWaktu.getText().toString();
                int harga = Integer.parseInt(etHarga.getText().toString());
                int sisaTiket = Integer.parseInt(etSisaTiket.getText().toString());
                addTiket(namaAcara, tanggal, waktu, harga, sisaTiket);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void addTiket(String namaAcara, String tanggal, String waktu, int harga, int sisaTiket) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Tiket tiket = new Tiket(namaAcara, tanggal, waktu, harga, sisaTiket);
        Call<Void> call = apiService.insertTiket(tiket);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    fetchUsers();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to add user: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchUsers() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Tiket>> call = apiService.getTiket();

        call.enqueue(new Callback<List<Tiket>>() {
            @Override
            public void onResponse(Call<List<Tiket>> call, Response<List<Tiket>> response) {
                if (response.isSuccessful()) {
                    tiketList.clear();
                    tiketList.addAll(response.body());
                    tiketAdapter.notifyDataSetChanged();
                } else {
                    Log.e("MainActivity", "Response error: " + response.errorBody().toString());
                    Toast.makeText(MainActivity.this, "Failed to fetch users: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tiket>> call, Throwable t) {
                Log.e("MainActivity", "Fetch error: ", t);
                Toast.makeText(MainActivity.this, "Failed to fetch users: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTiket(String namaAcara, String tanggal, String waktu, EditText s, String harga, String sisaTiket) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Tiket tiket = new Tiket(namaAcara, tanggal , waktu, harga, sisaTiket);
        Call<Void> call = apiService.updateTiket(tiket);

        Log.d("MainActivity", "Updating tiket: " + namaAcara + ", " + tanggal + ", " + waktu + ", " + harga + ", " + sisaTiket +",");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "User updated successfully");
                    Toast.makeText(MainActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                    fetchUsers();
                } else {
                    Log.e("MainActivity", "Response error: " + response.errorBody().toString());
                    Toast.makeText(MainActivity.this, "Failed to update user: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("MainActivity", "Fetch error: ", t);
                Toast.makeText(MainActivity.this, "Failed to update user: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showUpdateDialog(final Tiket tiket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update User");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_update_tiket, (ViewGroup) findViewById(android.R.id.content), false);
        final EditText namaAcara = viewInflated.findViewById(R.id.etNamaAcara);
        final EditText tanggal = viewInflated.findViewById(R.id.etTanggal);
        final EditText waktu = viewInflated.findViewById(R.id.etWaktu);
        final EditText harga = viewInflated.findViewById(R.id.etHarga);
        final EditText sisaTiket = viewInflated.findViewById(R.id.etSisaTiket);

        namaAcara.setText(tiket.getNamaAcara());
        tanggal.setText(tiket.getTanggal());
        waktu.setText(tiket.getWaktu());
        harga.setText(tiket.getHarga());
        sisaTiket.setText(tiket.getSisaTiket());

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String eAcara = namaAcara.getText().toString();
                String etanggal = tanggal.getText().toString();
                String ewaktu = waktu.getText().toString();
                String eharga = harga.getText().toString();
                String esisaTiket = sisaTiket.getText().toString();
                updateTiket(tiket.getNamaAcara(), eAcara, etanggal, ewaktu, eharga, esisaTiket);
            }

        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void refreshData() {
        fetchUsers();
    }
}