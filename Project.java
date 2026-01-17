import java.time.LocalDate;
import java.io.Serializable;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idProyek;
    private String namaProyek;
    private String deskripsi;
    private LocalDate tanggalMulai;
    private LocalDate tanggalSelesai;
    private String status;
    private String koordinator;
    private int jumlahVolunteerDibutuhkan;
    private int jumlahVolunteerTerdaftar;
    private String lokasi;
    private String kategori;

    public Project(String idProyek, String namaProyek, String deskripsi,
                   LocalDate tanggalMulai, LocalDate tanggalSelesai,
                   String koordinator, int jumlahVolunteerDibutuhkan,
                   String lokasi, String kategori) {
        this.idProyek = idProyek;
        this.namaProyek = namaProyek;
        this.deskripsi = deskripsi;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.status = "Planned";
        this.koordinator = koordinator;
        this.jumlahVolunteerDibutuhkan = jumlahVolunteerDibutuhkan;
        this.jumlahVolunteerTerdaftar = 0;
        this.lokasi = lokasi;
        this.kategori = kategori;
    }

    public String getIdProyek() {
        return idProyek;
    }
    public String getNamaProyek() {
        return namaProyek;
    }
    public String getDeskripsi() {
        return deskripsi;
    }
    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }
    public LocalDate getTanggalSelesai() {
        return tanggalSelesai;
    }
    public String getStatus() {
        return status;
    }
    public String getKoordinator() {
        return koordinator;
    }
    public int getJumlahVolunteerDibutuhkan() {
        return jumlahVolunteerDibutuhkan;
    }
    public int getJumlahVolunteerTerdaftar() {
        return jumlahVolunteerTerdaftar;
    }
    public String getLokasi() {
        return lokasi;
    }
    public String getKategori() {
        return kategori;
    }

    public void setIdProyek(String idProyek) {
        this.idProyek = idProyek;
    }
    public void setNamaProyek(String namaProyek) {
        this.namaProyek = namaProyek;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    public void setTanggalMulai(LocalDate tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }
    public void setTanggalSelesai(LocalDate tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setKoordinator(String koordinator) {
        this.koordinator = koordinator;
    }
    public void setJumlahVolunteerDibutuhkan(int jumlahVolunteerDibutuhkan) {
        this.jumlahVolunteerDibutuhkan = jumlahVolunteerDibutuhkan;
    }
    public void setJumlahVolunteerTerdaftar(int jumlahVolunteerTerdaftar) {
        this.jumlahVolunteerTerdaftar = jumlahVolunteerTerdaftar;
    }
    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getSlotTersedia() {
        return jumlahVolunteerDibutuhkan - jumlahVolunteerTerdaftar;
    }

    public boolean isFull() {
        return jumlahVolunteerTerdaftar >= jumlahVolunteerDibutuhkan;
    }
}
