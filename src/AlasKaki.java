public abstract class AlasKaki extends Produk {
  private String merk;

  //Constructor
  public AlasKaki(String kode, String ukuran, String warna, float harga, String nama, int stok, String merk) {
    super(kode, ukuran, warna, harga, nama, stok);
    this.merk = merk;
  }

  //setter and getter
  public void setMerk(String merk) {
    this.merk = merk;
  }
  public String getMerk() {
    return merk;
  }

  //info umum alas kaki, dilengkapi oleh Sendal dan Sepatu
  public String getInfo() {
    return getNama() + " (" + merk + "), Ukuran = " + getUkuran() + ", Warna = " + getWarna();
  }
}
