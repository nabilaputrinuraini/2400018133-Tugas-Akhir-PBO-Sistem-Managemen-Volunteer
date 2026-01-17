import java.time.LocalDate;

public class Volunteer extends Person {
    private String keahlian;
    private int jamKerja;
    private String status;
    private String tingkatPendidikan;
    private double rating;
    private String ketersediaan;
    private int poinPrestasi;

    public Volunteer(String id, String nama, String email, String noTelepon,
                     String alamat, LocalDate tanggalDaftar, String jenisKelamin,
                     LocalDate tanggalLahir, String keahlian, int jamKerja,
                     String status, String tingkatPendidikan, double rating,
                     String ketersediaan) {
        super(id, nama, email, noTelepon, alamat, tanggalDaftar, jenisKelamin, tanggalLahir);
        this.keahlian = keahlian;
        this.jamKerja = jamKerja;
        this.status = status;
        this.tingkatPendidikan = tingkatPendidikan;
        this.rating = rating;
        this.ketersediaan = ketersediaan;
        this.poinPrestasi = 0;
    }


    public String getKeahlian() {
        return keahlian;
    }
    public int getJamKerja() {
        return jamKerja;
    }
    public String getStatus() {
        return status;
    }
    public String getTingkatPendidikan() {
        return tingkatPendidikan;
    }
    public double getRating() {
        return rating;
    }
    public String getKetersediaan() {
        return ketersediaan;
    }
    public int getPoinPrestasi() {
        return poinPrestasi;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }
    public void setJamKerja(int jamKerja) {
        this.jamKerja = jamKerja;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTingkatPendidikan(String tingkatPendidikan) {
        this.tingkatPendidikan = tingkatPendidikan;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setKetersediaan(String ketersediaan) {
        this.ketersediaan = ketersediaan;
    }
    public void setPoinPrestasi(int poinPrestasi) {
        this.poinPrestasi = poinPrestasi;
    }

    public void tambahPoinPrestasi(int poin) {
        this.poinPrestasi += poin;
    }

    public String getLevel() {
        if (poinPrestasi >= 1000)
            return "Diamond";
        if (poinPrestasi >= 500)
            return "Platinum";
        if (poinPrestasi >= 250)
            return "Gold";
        if (poinPrestasi >= 100)
            return "Silver";
        return "Bronze";
    }

    @Override
    public String getInfo() {
        return String.format("%s | %s | %s | Status: %s | Rating: %.1f | Level: %s",
                getId(), getNama(), getEmail(), status, rating, getLevel());
    }

    @Override
    public String getTipe() {
        return "Volunteer";
    }
}
