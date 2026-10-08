import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Penjualan implements Cetak {
  private String penjualanID;
  private String waktuPenjualan;
  private ArrayList<Produk> listProduk;
  private Seller seller;
  private Buyer buyer;
  private String metodePengiriman;
  private String metodePembayaran;
  private float totalHarga;

  //Constructor
  public Penjualan(String penjualanID, String waktuPenjualan, Seller seller, Buyer buyer, String metodePengiriman, String metodePembayaran) {
    this.penjualanID = penjualanID;
    this.waktuPenjualan = waktuPenjualan;
    this.seller = seller;
    this.buyer = buyer;
    this.metodePengiriman = metodePengiriman;
    this.metodePembayaran = metodePembayaran;
    this.listProduk = new ArrayList<>();
    this.totalHarga = 0;
  }

  //method penjualan: 1 item produk = 1 kali addProduk
  public void addProduk(Produk produk) {
    listProduk.add(produk);
    hitungTotalHarga();
  }

  public float hitungTotalHarga() {
    totalHarga = 0;
    for (Produk produk : listProduk) {
      totalHarga += produk.getHarga();
    }
    return totalHarga;
  }

  //setter and getter
  public void setPenjualanID(String penjualanID) {
    this.penjualanID = penjualanID;
  }
  public void setWaktuPenjualan(String waktuPenjualan) {
    this.waktuPenjualan = waktuPenjualan;
  }
  public void setSeller(Seller seller) {
    this.seller = seller;
  }
  public void setBuyer(Buyer buyer) {
    this.buyer = buyer;
  }
  public void setMetodePengiriman(String metodePengiriman) {
    this.metodePengiriman = metodePengiriman;
  }
  public void setMetodePembayaran(String metodePembayaran) {
    this.metodePembayaran = metodePembayaran;
  }
  public String getPenjualanID() {
    return penjualanID;
  }
  public String getWaktuPenjualan() {
    return waktuPenjualan;
  }
  public ArrayList<Produk> getListProduk() {
    return listProduk;
  }
  public Seller getSeller() {
    return seller;
  }
  public Buyer getBuyer() {
    return buyer;
  }
  public String getMetodePengiriman() {
    return metodePengiriman;
  }
  public String getMetodePembayaran() {
    return metodePembayaran;
  }
  public float getTotalHarga() {
    return totalHarga;
  }

  //implementasi interface Cetak: tampilkan struk dan simpan salinannya ke file
  public void cetakStruk() {
    //kelompokkan item yang sama (berdasarkan kode) agar struk lebih ringkas
    LinkedHashMap<String, Produk> produkUnik = new LinkedHashMap<>();
    LinkedHashMap<String, Integer> jumlah = new LinkedHashMap<>();
    for (Produk produk : listProduk) {
      produkUnik.putIfAbsent(produk.getKode(), produk);
      jumlah.merge(produk.getKode(), 1, Integer::sum);
    }

    StringBuilder struk = new StringBuilder();
    struk.append("========================================\n");
    struk.append("             STRUK PEMBELIAN\n");
    struk.append("========================================\n");
    struk.append("No. Pesanan : ").append(penjualanID).append("\n");
    struk.append("Waktu       : ").append(waktuPenjualan).append("\n");
    struk.append("----------------------------------------\n");
    struk.append(seller.getInfo()).append("\n");
    struk.append("----------------------------------------\n");
    struk.append(buyer.getInfo()).append("\n");
    struk.append("----------------------------------------\n");

    int nomor = 1;
    for (Produk produk : produkUnik.values()) {
      int qty = jumlah.get(produk.getKode());
      struk.append(nomor++).append(". ").append(produk.getInfo()).append("\n");
      struk.append("   ").append(qty).append(" x ").append(rupiah(produk.getHarga()))
          .append(" = ").append(rupiah(produk.getHarga() * qty)).append("\n");
    }

    struk.append("----------------------------------------\n");
    struk.append("Pengiriman        : ").append(metodePengiriman).append("\n");
    struk.append("Pembayaran        : ").append(metodePembayaran).append("\n");
    struk.append("Total Produk      : ").append(listProduk.size()).append("\n");
    struk.append("Total Bayar       : ").append(rupiah(totalHarga)).append("\n");
    struk.append("========================================\n");
    struk.append("Terima kasih telah berbelanja!\n");

    System.out.println("\n" + struk);

    try {
      Path folderStruk = Paths.get("struk");
      Files.createDirectories(folderStruk);
      String namaFile = penjualanID.replaceAll("[^a-zA-Z0-9._-]", "_") + ".txt";
      Path fileStruk = folderStruk.resolve(namaFile);
      Files.write(fileStruk, struk.toString().getBytes(StandardCharsets.UTF_8));
      System.out.println("Struk disimpan di " + fileStruk + ".");
    } catch (IOException e) {
      System.out.println("Gagal menyimpan struk: " + e.getMessage());
    }
  }

  private String rupiah(float nilai) {
    return "Rp " + String.format("%,.0f", nilai).replace(',', '.');
  }
}
