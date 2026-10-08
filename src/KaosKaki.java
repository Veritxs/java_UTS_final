public class KaosKaki extends Produk {
  private String motif;

  //Constructor
  public KaosKaki(String kode, String ukuran, String warna, float harga, String nama, int stok, String motif) {
    super(kode, ukuran, warna, harga, nama, stok);
    this.motif = motif;
  }

  //setter and getter
  public void setMotif(String motif) {
    this.motif = motif;
  }
  public String getMotif() {
    return motif;
  }

  public String getInfo() {
    return "Kaos Kaki " + getNama() + ", Ukuran = " + getUkuran() + ", Warna = " + getWarna() + ", Motif = " + motif;
  }
}
