public class Sepatu extends AlasKaki {
  private String jenisSepatu;

  //Constructor
  public Sepatu(String kode, String ukuran, String warna, float harga, String nama, int stok, String merk, String jenisSepatu) {
    super(kode, ukuran, warna, harga, nama, stok, merk);
    this.jenisSepatu = jenisSepatu;
  }

  //setter and getter
  public void setJenisSepatu(String jenisSepatu) {
    this.jenisSepatu = jenisSepatu;
  }
  public String getJenisSepatu() {
    return jenisSepatu;
  }

  public String getInfo() {
    return "Sepatu " + super.getInfo() + ", Jenis = " + jenisSepatu;
  }
}
