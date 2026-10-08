public abstract class Person {
  private String email;
  private String nama;
  private String noHp;
  private String username;
  private String password;

  //Constructor
  public Person(String email, String nama, String noHp, String username, String password) {
    this.email = email;
    this.nama = nama;
    this.noHp = noHp;
    this.username = username;
    this.password = password;
  }

  //setter and getter
  public void setEmail(String email) {
    this.email = email;
  }
  public void setNama(String nama) {
    this.nama = nama;
  }
  public void setNoHp(String noHp) {
    this.noHp = noHp;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getEmail() {
    return email;
  }
  public String getNama() {
    return nama;
  }
  public String getNoHp() {
    return noHp;
  }
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }

  //Abstract Method
  public abstract String getInfo();
}
