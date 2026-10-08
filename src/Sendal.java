public class Sendal extends AlasKaki {
  private String jenisSendal;

  //Constructor
  public Sendal(String kode, String ukuran, String warna, float harga, String nama, int stok, String merk, String jenisSendal) {
    super(kode, ukuran, warna, harga, nama, stok, merk);
    this.jenisSendal = jenisSendal;
  }

  //setter and getter
  public void setJenisSendal(String jenisSendal) {
    this.jenisSendal = jenisSendal;
  }
  public String getJenisSendal() {
    return jenisSendal;
  }

  public String getInfo() {
    return "Sendal " + super.getInfo() + ", Jenis = " + jenisSendal;
  }
}
