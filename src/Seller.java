public class Seller extends Person {
  private String namaToko;
  private String alamatToko;

  //Constructor
  public Seller(String email, String nama, String noHp, String username, String password, String namaToko, String alamatToko) {
    super(email, nama, noHp, username, password);
    this.namaToko = namaToko;
    this.alamatToko = alamatToko;
  }

  //setter and getter
  public void setNamaToko(String namaToko) {
    this.namaToko = namaToko;
  }
  public void setAlamatToko(String alamatToko) {
    this.alamatToko = alamatToko;
  }
  public String getNamaToko() {
    return namaToko;
  }
  public String getAlamatToko() {
    return alamatToko;
  }

  public String getInfo() {
    return "Toko    : " + namaToko + "\n" +
           "Alamat  : " + alamatToko + "\n" +
           "Penjual : " + getNama() + " (@" + getUsername() + ")\n" +
           "Email   : " + getEmail() + "\n" +
           "No. HP  : " + getNoHp();
  }
}
