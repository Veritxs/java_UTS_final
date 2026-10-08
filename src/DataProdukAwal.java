import java.util.ArrayList;

public class DataProdukAwal {
  private ArrayList<Produk> daftarProduk;

  //Constructor: data produk langsung tersedia saat aplikasi dijalankan
  public DataProdukAwal() {
    daftarProduk = new ArrayList<>();
    inisialisasiProduk();
  }

  public void inisialisasiProduk() {
    daftarProduk.clear();
    //KaosKaki(kode, ukuran, warna, harga, nama, stok, motif)
    daftarProduk.add(new KaosKaki("KK001", "M", "Hitam", 75000, "Nike", 20, "Sport"));
    daftarProduk.add(new KaosKaki("KK002", "L", "Putih", 85000, "Adidas", 15, "Casual"));
    //Sendal(kode, ukuran, warna, harga, nama, stok, merk, jenisSendal)
    daftarProduk.add(new Sendal("SD001", "42", "Hitam", 350000, "Adilette", 10, "Adidas", "Slides"));
    daftarProduk.add(new Sendal("SD002", "41", "Coklat", 275000, "Eiger Outdoor", 8, "Eiger", "Outdoor"));
    //Sepatu(kode, ukuran, warna, harga, nama, stok, merk, jenisSepatu)
    daftarProduk.add(new Sepatu("SP001", "42", "Putih", 1800000, "Air Force 1", 7, "Nike", "Lifestyle"));
    daftarProduk.add(new Sepatu("SP002", "43", "Hitam", 2200000, "Ultraboost", 5, "Adidas", "Running"));
  }

  public ArrayList<Produk> getDaftarProduk() {
    return daftarProduk;
  }
}
