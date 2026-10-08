public abstract class Produk {
  private String kode;
  private String ukuran;
  private String warna;
  private float harga;
  private String nama;
  private int stok;

  //Constructor
  public Produk(String kode, String ukuran, String warna, float harga, String nama, int stok) {
    this.kode = kode;
    this.ukuran = ukuran;
    this.warna = warna;
    this.harga = harga;
    this.nama = nama;
    this.stok = stok;
  }

  //setter and getter
  public void setKode(String kode) {
    this.kode = kode;
  }
  public void setUkuran(String ukuran) {
    this.ukuran = ukuran;
  }
  public void setWarna(String warna) {
    this.warna = warna;
  }
  public void setHarga(float harga) {
    this.harga = harga;
  }
  public void setNama(String nama) {
    this.nama = nama;
  }
  public void setStok(int stok) {
    this.stok = stok;
  }
  public String getKode() {
    return kode;
  }
  public String getUkuran() {
    return ukuran;
  }
  public String getWarna() {
    return warna;
  }
  public float getHarga() {
    return harga;
  }
  public String getNama() {
    return nama;
  }
  public int getStok() {
    return stok;
  }

  //Abstract Method
  public abstract String getInfo();
}
