package com.example.andy.tutorialspoint;

class aracGonderimListesiData {
    String id;
    String plaka;
    String marka;
    String musteriAdi;
    String musteriSoyadi;
    String soforAdi;
    String aracPlaka;
    String aracMarka;
    String adres;
    String tarih;
    String saat;
    public aracGonderimListesiData(String id,String aracPlaka, String aracMarka,String musteriAdi,String musteriSoyadi,String soforAdi,
                                   String adres,String tarih, String saat) {
        this.id = id;
        this.musteriAdi = musteriAdi;
        this.musteriSoyadi = musteriSoyadi;
        this.soforAdi = soforAdi;
        this.aracPlaka = aracPlaka;
        this.aracMarka = aracMarka;
        this.adres = adres;
        this.tarih = tarih;
        this.saat = saat;
    }
}
