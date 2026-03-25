package org.rplbo.app.Data;

public class RekamMedis {
    private int id;
    private String namaPasien;
    private String namaDokter;
    private String diagnosis;
    private String tanggal;

    public RekamMedis(int id, String namaPasien, String namaDokter, String diagnosis, String tanggal) {
        this.id = id;
        this.namaPasien = namaPasien;
        this.namaDokter = namaDokter;
        this.diagnosis = diagnosis;
        this.tanggal = tanggal;
    }

    public int getId() { return id; }
    public String getNamaPasien() { return namaPasien; }
    public String getNamaDokter() { return namaDokter; }
    public String getDiagnosis() { return diagnosis; }
    public String getTanggal() { return tanggal; }
}