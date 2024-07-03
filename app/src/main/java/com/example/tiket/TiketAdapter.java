package com.example.tiket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TiketAdapter extends RecyclerView.Adapter<TiketAdapter.TiketViewHolder> {
    private List<Tiket> tiketList;
    private Context context;
    private MainActivity mainActivity; // Referensi ke MainActivity

    public TiketAdapter(List<Tiket> tiketList, Context context) {
        this.tiketList = tiketList;
        this.context = context;

        // Check if context is an instance of MainActivity and set mainActivity
        if (context instanceof MainActivity) {
            this.mainActivity = (MainActivity) context;
        }
    }

    @NonNull
    @Override
    public TiketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiket, parent, false);
        return new TiketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiketViewHolder holder, int position) {
        Tiket tiket = tiketList.get(position);
        holder.namaAcaraTextView.setText(tiket.getNamaAcara());
        holder.tanggalTextView.setText(tiket.getTanggal());
        holder.waktuTextView.setText(tiket.getWaktu());
        holder.hargaTextView.setText(String.format("Rp. %d", tiket.getHarga()));
        holder.sisaTiketTextView.setText(String.format("Sisa Tiket: %d", tiket.getSisaTiket()));


    // Mengatur OnClickListener pada itemView untuk menangani tap pada item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mainActivity != null) {
                mainActivity.showUpdateDialog(tiket);
            }
        }
    });

    // Mengatur OnClickListener pada tombol delete untuk menangani tap pada tombol delete
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDeleteConfirmationDialog(tiket.getNamaAcara());
        }
    });
}

    @Override
    public int getItemCount() {
        return tiketList.size();
    }

    // Metode untuk menampilkan dialog konfirmasi penghapusan
    private void showDeleteConfirmationDialog(final int userId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete User");
        builder.setMessage("Are you sure you want to delete this tiket?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTiket(String.valueOf(userId));
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    // Metode untuk menghapus pengguna dari daftar dan server
    private void deleteTiket(String userId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.deleteTiket(Integer.parseInt(userId));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < tiketList.size(); i++) {
                        if (tiketList.get(i).getNamaAcara() == Integer.parseInt(userId)) {
                            tiketList.remove(i);
                            notifyItemRemoved(i);
                            break;
                        }}
                    Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    // Refresh user list after deletion
                    mainActivity.refreshData();
                } else {
                    Toast.makeText(context, "Failed to delete user: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Failed to delete user: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    class TiketViewHolder extends RecyclerView.ViewHolder {

        public View buttonDelete;
        TextView namaAcaraTextView, tanggalTextView, waktuTextView, hargaTextView, sisaTiketTextView;

        public TiketViewHolder(@NonNull View itemView) {
            super(itemView);
            namaAcaraTextView = itemView.findViewById(R.id.namaAcaraTextView);
            tanggalTextView = itemView.findViewById(R.id.tanggalTextView);
            waktuTextView = itemView.findViewById(R.id.waktuTextView);
            hargaTextView = itemView.findViewById(R.id.hargaTextView);
            sisaTiketTextView = itemView.findViewById(R.id.sisaTiketTextView);
        }
    }
    // Metode untuk mengatur MainActivity yang terkait
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
