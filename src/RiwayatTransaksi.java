import java.io.*;
import java.util.ArrayList;

/*
 * Menyimpan histori pembelian secara permanen di file teks.
 * Format file (satu transaksi = 1 baris TRX + beberapa baris ITEM):
 * TRX|id|waktu|pengiriman|pembayaran|emailBuyer|namaBuyer|noHpBuyer|usernameBuyer|alamatBuyer|emailSeller|namaSeller|noHpSeller|usernameSeller|namaToko|alamatToko
 * ITEM|jenis|kode|ukuran|warna|harga|nama|detail1|detail2      (1 baris = 1 item yang dibeli)
 */
public class RiwayatTransaksi {
  private ArrayList<Penjualan> daftarTransaksi;
  private String fileHistory;

  //Constructor: histori langsung dibaca dari file
  public RiwayatTransaksi() {
    daftarTransaksi = new ArrayList<>();
    fileHistory = "riwayat.txt";
    bacaHistory();
  }

  public void tambahTransaksi(Penjualan p) {
    daftarTransaksi.add(p);
    simpanHistory();
  }

  public ArrayList<Penjualan> getRiwayat() {
    return daftarTransaksi;
  }

  public void simpanHistory() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileHistory))) {
      for (Penjualan p : daftarTransaksi) {
        Buyer b = p.getBuyer();
        Seller s = p.getSeller();
        //password sengaja tidak disimpan ke file
        writer.println(String.join("|", "TRX", p.getPenjualanID(), p.getWaktuPenjualan(),
            p.getMetodePengiriman(), p.getMetodePembayaran(),
            b.getEmail(), b.getNama(), b.getNoHp(), b.getUsername(), b.getAlamatPribadi(),
            s.getEmail(), s.getNama(), s.getNoHp(), s.getUsername(), s.getNamaToko(), s.getAlamatToko()));
        for (Produk produk : p.getListProduk()) {
          writer.println(produkKeBaris(produk));
        }
      }
    } catch (IOException e) {
      System.out.println("Gagal menyimpan riwayat: " + e.getMessage());
    }
  }

  public void bacaHistory() {
    daftarTransaksi.clear();
    File file = new File(fileHistory);
    if (!file.exists()) return;

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      Penjualan transaksi = null;
      String baris;
      while ((baris = reader.readLine()) != null) {
        String[] d = baris.split("\\|", -1);
        if (d[0].equals("TRX") && d.length == 16) {
          Buyer buyer = new Buyer(d[5], d[6], d[7], d[8], "", d[9]);
          Seller seller = new Seller(d[10], d[11], d[12], d[13], "", d[14], d[15]);
          transaksi = new Penjualan(d[1], d[2], seller, buyer, d[3], d[4]);
          daftarTransaksi.add(transaksi);
        } else if (d[0].equals("ITEM") && d.length == 9 && transaksi != null) {
          Produk produk = barisKeProduk(d);
          if (produk != null) transaksi.addProduk(produk);
        }
      }
    } catch (IOException | NumberFormatException e) {
      System.out.println("Gagal membaca riwayat: " + e.getMessage());
    }
  }

  private String produkKeBaris(Produk p) {
    String jenis, detail1, detail2 = "-";
    if (p instanceof KaosKaki) {
      jenis = "KAOSKAKI";
      detail1 = ((KaosKaki) p).getMotif();
    } else if (p instanceof Sendal) {
      Sendal s = (Sendal) p;
      jenis = "SENDAL";
      detail1 = s.getMerk();
      detail2 = s.getJenisSendal();
    } else {
      Sepatu s = (Sepatu) p;
      jenis = "SEPATU";
      detail1 = s.getMerk();
      detail2 = s.getJenisSepatu();
    }
    return String.join("|", "ITEM", jenis, p.getKode(), p.getUkuran(), p.getWarna(),
        String.valueOf(p.getHarga()), p.getNama(), detail1, detail2);
  }

  //produk di histori adalah salinan saat transaksi terjadi (stok tidak relevan = 0)
  private Produk barisKeProduk(String[] d) {
    float harga = Float.parseFloat(d[5]);
    switch (d[1]) {
      case "KAOSKAKI": return new KaosKaki(d[2], d[3], d[4], harga, d[6], 0, d[7]);
      case "SENDAL":   return new Sendal(d[2], d[3], d[4], harga, d[6], 0, d[7], d[8]);
      case "SEPATU":   return new Sepatu(d[2], d[3], d[4], harga, d[6], 0, d[7], d[8]);
      default:         return null;
    }
  }
}
