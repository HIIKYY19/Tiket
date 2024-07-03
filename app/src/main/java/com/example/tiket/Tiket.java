package com.example.tiket;

public class Tiket {

    private String namaAcara;
    private String tanggal;
    private String waktu;
    private int harga;
    private int sisaTiket;

    public Tiket(String namaAcara, String tanggal, String waktu, String harga, String sisaTiket) {
        this.namaAcara = namaAcara;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.harga = harga;
        this.sisaTiket = sisaTiket;
    }

    // Getter dan setter untuk semua atribut
    public String getNamaAcara() {
        return namaAcara;
    }

    public void setNamaAcara(String namaAcara) {
        this.namaAcara = namaAcara;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getSisaTiket() {
        return sisaTiket;
    }

    public void setSisaTiket(int sisaTiket) {
        this.sisaTiket = sisaTiket;
    }
}
