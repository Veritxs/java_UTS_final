import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
  private static final String[] METODE_PENGIRIMAN = {"JNE", "J&T", "SiCepat", "Ambil di Toko"};
  private static final String[] METODE_PEMBAYARAN = {"Transfer Bank", "E-Wallet", "COD"};

  private static Scanner input = new Scanner(System.in);
  private static ArrayList<Produk> daftarProduk;
  private static RiwayatTransaksi riwayat;
  private static Seller seller;

  public static void main(String[] args) {
    //data awal: produk & stok langsung tersedia, histori dibaca dari file
    daftarProduk = new DataProdukAwal().getDaftarProduk();
    riwayat = new RiwayatTransaksi();
    sinkronStok();

    seller = new Seller("toko@example.com", "Admin Toko", "081234567890", "admin", "admin123",
        "Toko Alas Kaki & Kaos Kaki", "Jl. Contoh No. 1");

    System.out.println("============================================");
    System.out.println("       SISTEM TOKO ALAS KAKI & KAOS KAKI");
    System.out.println("============================================");
    System.out.println(daftarProduk.size() + " produk dimuat, " + riwayat.getRiwayat().size() + " transaksi di riwayat.");

    boolean jalan = true;
    while (jalan) {
      tampilMenuUtama();
      switch (bacaInt("Pilih menu : ")) {
        case 1: tampilkanSemuaProduk(); break;
        case 2: prosesPembelian(); break;
        case 3: tampilkanRiwayat(); break;
        case 4: tampilkanDataToko(); break;
        case 0:
          jalan = false;
          System.out.println("\nProgram selesai. Terima kasih!");
          break;
        default:
          System.out.println("Pilihan menu tidak tersedia.");
      }
    }
    input.close();
  }

  //stok sekarang = stok awal - semua item yang pernah terjual di riwayat
  private static void sinkronStok() {
    for (Penjualan p : riwayat.getRiwayat()) {
      for (Produk item : p.getListProduk()) {
        Produk produk = cariDenganKode(item.getKode());
        if (produk != null && produk.getStok() > 0) {
          produk.setStok(produk.getStok() - 1);
        }
      }
    }
  }

  private static void tampilMenuUtama() {
    System.out.println("\n============================================");
    System.out.println("                 MENU UTAMA");
    System.out.println("============================================");
    System.out.println("1. Lihat Semua Produk");
    System.out.println("2. Beli Produk");
    System.out.println("3. Riwayat Transaksi");
    System.out.println("4. Data Toko");
    System.out.println("0. Keluar");
    System.out.println("============================================");
  }

  private static void tampilkanSemuaProduk() {
    System.out.println("\n=== SEMUA PRODUK ===");
    for (int i = 0; i < daftarProduk.size(); i++) {
      tampilkanProduk(i + 1, daftarProduk.get(i));
    }
  }

  private static void tampilkanProduk(int nomor, Produk p) {
    System.out.println(nomor + ". [" + p.getKode() + "] " + p.getInfo());
    System.out.println("   Harga: " + rupiah(p.getHarga()) + " | Stok: " + p.getStok());
  }

  private static void prosesPembelian() {
    System.out.println("\n=== DATA BUYER ===");
    String email = bacaTeks("Email         : ");
    String nama = bacaTeks("Nama          : ");
    String noHp = bacaTeks("No. handphone : ");
    String username = bacaTeks("Username      : ");
    String password = bacaTeks("Password      : ");
    String alamat = bacaTeks("Alamat        : ");
    Buyer buyer = new Buyer(email, nama, noHp, username, password, alamat);

    System.out.println("\n=== METODE PENGIRIMAN ===");
    String pengiriman = pilihOpsi(METODE_PENGIRIMAN);
    System.out.println("\n=== METODE PEMBAYARAN ===");
    String pembayaran = pilihOpsi(METODE_PEMBAYARAN);

    LocalDateTime sekarang = LocalDateTime.now();
    String id = "TRX" + sekarang.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String waktu = sekarang.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    Penjualan penjualan = new Penjualan(id, waktu, seller, buyer, pengiriman, pembayaran);

    while (true) {
      System.out.println("\n=== PILIH PRODUK ===");
      for (int i = 0; i < daftarProduk.size(); i++) {
        Produk p = daftarProduk.get(i);
        System.out.println((i + 1) + ". " + p.getKode() + " | " + p.getNama() + " | " + rupiah(p.getHarga()) + " | Stok: " + p.getStok());
      }
      System.out.println("0. Selesai belanja (total: " + rupiah(penjualan.getTotalHarga()) + ")");
      int pilihan = bacaInt("Pilih produk: ");

      if (pilihan == 0) break;
      if (pilihan < 1 || pilihan > daftarProduk.size()) {
        System.out.println("Pilihan produk tidak tersedia.");
        continue;
      }

      Produk produk = daftarProduk.get(pilihan - 1);
      int jumlah = bacaInt("Jumlah: ");
      if (jumlah <= 0) {
        System.out.println("Jumlah harus lebih dari 0.");
      } else if (jumlah > produk.getStok()) {
        System.out.println("Stok " + produk.getNama() + " tidak cukup.");
      } else {
        for (int i = 0; i < jumlah; i++) {
          penjualan.addProduk(produk);
        }
        produk.setStok(produk.getStok() - jumlah);
        System.out.println(jumlah + " x " + produk.getNama() + " ditambahkan ke pesanan.");
      }
    }

    if (penjualan.getListProduk().isEmpty()) {
      System.out.println("Tidak ada produk yang dibeli. Transaksi dibatalkan.");
      return;
    }

    Cetak struk = penjualan;
    struk.cetakStruk();
    riwayat.tambahTransaksi(penjualan);
    System.out.println("Transaksi tersimpan di riwayat.");
  }

  private static void tampilkanRiwayat() {
    System.out.println("\n=== RIWAYAT TRANSAKSI ===");
    ArrayList<Penjualan> daftar = riwayat.getRiwayat();
    if (daftar.isEmpty()) {
      System.out.println("Belum ada transaksi.");
      return;
    }

    for (int i = 0; i < daftar.size(); i++) {
      Penjualan p = daftar.get(i);
      System.out.println((i + 1) + ". " + p.getPenjualanID() + " | " + p.getWaktuPenjualan() + " | " +
          p.getBuyer().getNama() + " | " + p.getMetodePembayaran() + " | " + rupiah(p.getTotalHarga()));
    }

    int pilihan = bacaInt("Lihat struk nomor (0 = kembali): ");
    if (pilihan >= 1 && pilihan <= daftar.size()) {
      Cetak struk = daftar.get(pilihan - 1);
      struk.cetakStruk();
    }
  }

  private static void tampilkanDataToko() {
    System.out.println("\n=== DATA TOKO ===");
    System.out.println(seller.getInfo());
    System.out.println("Produk  : " + daftarProduk.size());
  }

  private static Produk cariDenganKode(String kode) {
    for (Produk p : daftarProduk) {
      if (p.getKode().equalsIgnoreCase(kode)) return p;
    }
    return null;
  }

  private static String pilihOpsi(String[] opsi) {
    for (int i = 0; i < opsi.length; i++) {
      System.out.println((i + 1) + ". " + opsi[i]);
    }
    while (true) {
      int pilihan = bacaInt("Pilih: ");
      if (pilihan >= 1 && pilihan <= opsi.length) return opsi[pilihan - 1];
      System.out.println("Pilihan tidak tersedia.");
    }
  }

  //"|" dipakai sebagai pemisah di file riwayat, jadi tidak boleh ada di input
  private static String bacaTeks(String pesan) {
    while (true) {
      System.out.print(pesan);
      String teks = input.nextLine().trim().replace("|", "/");
      if (!teks.isEmpty()) return teks;
      System.out.println("Input tidak boleh kosong.");
    }
  }

  private static int bacaInt(String pesan) {
    while (true) {
      try {
        System.out.print(pesan);
        return Integer.parseInt(input.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println("Masukkan angka yang valid.");
      }
    }
  }

  private static String rupiah(float nilai) {
    return "Rp " + String.format("%,.0f", nilai).replace(',', '.');
  }
}
