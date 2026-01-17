import java.time.LocalDate;
import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nama;
    private String email;
    private String noTelepon;
    private String alamat;
    private LocalDate tanggalDaftar;
    private String jenisKelamin;
    private LocalDate tanggalLahir;

    public Person(String id, String nama, String email, String noTelepon,
                  String alamat, LocalDate tanggalDaftar, String jenisKelamin,
                  LocalDate tanggalLahir) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
        this.tanggalDaftar = tanggalDaftar;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
    }

    public String getId() {
        return id;
    }
    public String getNama() {
        return nama;
    }
    public String getEmail() {
        return email;
    }
    public String getNoTelepon() {
        return noTelepon;
    }
    public String getAlamat() {
        return alamat;
    }
    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }
    public String getJenisKelamin() {
        return jenisKelamin;
    }
    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public int getUsia() {
        if (tanggalLahir == null) return 0;
        return LocalDate.now().getYear() - tanggalLahir.getYear();
    }

    public abstract String getInfo();
    public abstract String getTipe();
}
