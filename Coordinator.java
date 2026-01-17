import java.time.LocalDate;

public class Coordinator extends Person {
    private String departemen;
    private int jumlahVolunteerDikelola;
    private String levelJabatan;
    private double gaji;
    private int pengalamanTahun;

    public Coordinator(String id, String nama, String email, String noTelepon,
                       String alamat, LocalDate tanggalDaftar, String jenisKelamin,
                       LocalDate tanggalLahir, String departemen,
                       int jumlahVolunteerDikelola, String levelJabatan,
                       double gaji, int pengalamanTahun) {
        super(id, nama, email, noTelepon, alamat, tanggalDaftar, jenisKelamin, tanggalLahir);
        this.departemen = departemen;
        this.jumlahVolunteerDikelola = jumlahVolunteerDikelola;
        this.levelJabatan = levelJabatan;
        this.gaji = gaji;
        this.pengalamanTahun = pengalamanTahun;
    }


    public String getDepartemen() {
        return departemen;
    }
    public int getJumlahVolunteerDikelola() {
        return jumlahVolunteerDikelola;
    }
    public String getLevelJabatan() {
        return levelJabatan;
    }
    public double getGaji() {
        return gaji;
    }
    public int getPengalamanTahun() {
        return pengalamanTahun;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }
    public void setJumlahVolunteerDikelola(int jumlahVolunteerDikelola) {
        this.jumlahVolunteerDikelola = jumlahVolunteerDikelola;
    }
    public void setLevelJabatan(String levelJabatan) {
        this.levelJabatan = levelJabatan;
    }
    public void setGaji(double gaji) {
        this.gaji = gaji;
    }
    public void setPengalamanTahun(int pengalamanTahun) {
        this.pengalamanTahun = pengalamanTahun;
    }

    public String getKategoriPengalaman() {
        if (pengalamanTahun >= 10) return "Expert";
        if (pengalamanTahun >= 5) return "Senior";
        if (pengalamanTahun >= 2) return "Intermediate";
        return "Junior";
    }

    @Override
    public String getInfo() {
        return String.format("%s | %s | %s | Dept: %s | Level: %s | Volunteer: %d",
                getId(), getNama(), getEmail(), departemen, levelJabatan, jumlahVolunteerDikelola);
    }

    @Override
    public String getTipe() {
        return "Coordinator";
    }
}
