import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.io.IOException;

public class VolunteerGUI extends JFrame {
    private VolunteerManager manager;
    private JTabbedPane tabbedPane;

    private final Color PRIMARY = new Color(41, 128, 185);
    private final Color SUCCESS = new Color(46, 204, 113);
    private final Color DANGER = new Color(231, 76, 60);
    private final Color WARNING = new Color(241, 196, 15);

    private JTextField vId, vNama, vEmail, vTelepon, vAlamat, vTglLahir, vKeahlian, vJamKerja, vRating;
    private JComboBox<String> vGender, vStatus, vPendidikan, vKetersediaan;
    private DefaultTableModel volunteerTableModel;
    private JTable volunteerTable;

    private JTextField cId, cNama, cEmail, cTelepon, cAlamat, cTglLahir, cJumlahVol, cGaji, cPengalaman;
    private JComboBox<String> cGender, cDepartemen, cLevel;
    private DefaultTableModel coordinatorTableModel;
    private JTable coordinatorTable;

    private JTextField pId, pNama, pTglMulai, pTglSelesai, pKoordinator, pVolDibutuhkan, pLokasi;
    private JTextArea pDeskripsi;
    private JComboBox<String> pKategori, pStatus;
    private DefaultTableModel projectTableModel;
    private JTable projectTable;

    private JLabel lblTotalVol, lblVolAktif, lblTotalCoord, lblTotalJam, lblAvgRating, lblTotalProject;

    public VolunteerGUI() {
        manager = new VolunteerManager();
        initGUI();
        setTitle("Sistem Manajemen Volunteer by Nabila Putri Nuraini");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("📊 Dashboard", createDashboard());
        tabbedPane.addTab("👥 Volunteer", createVolunteerPanel());
        tabbedPane.addTab("👔 Fasilitator", createCoordinatorPanel());
        tabbedPane.addTab("📁 Project", createProjectPanel());

        tabbedPane.addChangeListener(e -> refreshDashboard());
        add(tabbedPane);
    }

    // ========== DASHBOARD ==========
    private JPanel createDashboard() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Dashboard Sistem Manajemen Volunteer by Nabila Putri Nuraini", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        lblTotalVol = new JLabel("0", SwingConstants.CENTER);
        lblVolAktif = new JLabel("0", SwingConstants.CENTER);
        lblTotalCoord = new JLabel("0", SwingConstants.CENTER);
        lblTotalJam = new JLabel("0", SwingConstants.CENTER);
        lblAvgRating = new JLabel("0.0", SwingConstants.CENTER);
        lblTotalProject = new JLabel("0", SwingConstants.CENTER);

        statsPanel.add(createStatCard("Total Volunteer", lblTotalVol, PRIMARY));
        statsPanel.add(createStatCard("Volunteer Aktif", lblVolAktif, SUCCESS));
        statsPanel.add(createStatCard("Total Fasilitator", lblTotalCoord, WARNING));
        statsPanel.add(createStatCard("Total Jam Kerja", lblTotalJam, DANGER));
        statsPanel.add(createStatCard("Rata-rata Rating", lblAvgRating, WARNING));
        statsPanel.add(createStatCard("Total Proyek", lblTotalProject, PRIMARY));

        panel.add(title, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);

        refreshDashboard();
        return panel;
    }

    private JPanel createStatCard(String label, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 3),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(color);

        JLabel labelText = new JLabel(label, SwingConstants.CENTER);
        labelText.setFont(new Font("Arial", Font.PLAIN, 14));

        card.add(valueLabel, BorderLayout.CENTER);
        card.add(labelText, BorderLayout.SOUTH);

        return card;
    }

    private void refreshDashboard() {
        lblTotalVol.setText(String.valueOf(manager.getJumlahVolunteer()));
        lblVolAktif.setText(String.valueOf(manager.getJumlahVolunteerAktif()));
        lblTotalCoord.setText(String.valueOf(manager.getJumlahCoordinator()));
        lblTotalJam.setText(String.valueOf(manager.getTotalJamKerja()));
        lblAvgRating.setText(String.format("%.1f", manager.getRataRataRating()));
        lblTotalProject.setText(String.valueOf(manager.getAllProjects().size()));
    }

    private JPanel createVolunteerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        vId = new JTextField(15);
        vNama = new JTextField(15);
        vEmail = new JTextField(15);
        vTelepon = new JTextField(15);
        vAlamat = new JTextField(15);
        vTglLahir = new JTextField(15);
        vKeahlian = new JTextField(15);
        vJamKerja = new JTextField(15);
        vRating = new JTextField(15);
        vGender = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        vStatus = new JComboBox<>(new String[]{"Aktif", "Tidak Aktif", "Cuti"});
        vPendidikan = new JComboBox<>(new String[]{"SMP", "SMA", "D3", "S1", "S2", "S3"});
        vKetersediaan = new JComboBox<>(new String[]{"Full Time", "Part Time", "Weekend", "Flexible"});

        addFormField(formPanel, gbc, 0, "ID:", vId);
        addFormField(formPanel, gbc, 1, "Nama:", vNama);
        addFormField(formPanel, gbc, 2, "Email:", vEmail);
        addFormField(formPanel, gbc, 3, "Telepon:", vTelepon);
        addFormField(formPanel, gbc, 4, "Alamat:", vAlamat);
        addFormField(formPanel, gbc, 5, "Gender:", vGender);
        addFormField(formPanel, gbc, 6, "Tgl Lahir (yyyy-mm-dd):", vTglLahir);
        addFormField(formPanel, gbc, 7, "Keahlian:", vKeahlian);
        addFormField(formPanel, gbc, 8, "Jam Kerja:", vJamKerja);
        addFormField(formPanel, gbc, 9, "Status:", vStatus);
        addFormField(formPanel, gbc, 10, "Pendidikan:", vPendidikan);
        addFormField(formPanel, gbc, 11, "Rating (0-5):", vRating);
        addFormField(formPanel, gbc, 12, "Ketersediaan:", vKetersediaan);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnAdd = createButton("Tambah", SUCCESS);
        JButton btnUpdate = createButton("Update", PRIMARY);
        JButton btnDelete = createButton("Hapus", DANGER);
        JButton btnClear = createButton("Clear", WARNING);

        btnAdd.addActionListener(e -> saveVolunteer(false));
        btnUpdate.addActionListener(e -> saveVolunteer(true));
        btnDelete.addActionListener(e -> deleteVolunteer());
        btnClear.addActionListener(e -> clearVolunteerForm());

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        String[] columns = {"ID", "Nama", "Email", "Keahlian", "Jam", "Status", "Rating", "Level", "Poin"};
        volunteerTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        volunteerTable = new JTable(volunteerTableModel);
        volunteerTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadVolunteerToForm();
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = createButton("Cari", PRIMARY);
        JButton btnRefresh = createButton("Refresh", SUCCESS);

        btnSearch.addActionListener(e -> searchVolunteer(txtSearch.getText()));
        btnRefresh.addActionListener(e -> refreshVolunteerTable());

        searchPanel.add(new JLabel("Cari Nama:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(volunteerTable), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(formPanel), tablePanel);
        splitPane.setDividerLocation(400);

        panel.add(splitPane, BorderLayout.CENTER);
        refreshVolunteerTable();
        return panel;
    }

    private void saveVolunteer(boolean isUpdate) {
        try {
            String id = vId.getText().trim();
            String nama = vNama.getText().trim();
            String email = vEmail.getText().trim();

            if (id.isEmpty() || nama.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID, Nama, dan Email wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Volunteer v = new Volunteer(
                    id, nama, email, vTelepon.getText().trim(), vAlamat.getText().trim(),
                    LocalDate.now(), (String) vGender.getSelectedItem(),
                    LocalDate.parse(vTglLahir.getText().trim()),
                    vKeahlian.getText().trim(), Integer.parseInt(vJamKerja.getText().trim()),
                    (String) vStatus.getSelectedItem(), (String) vPendidikan.getSelectedItem(),
                    Double.parseDouble(vRating.getText().trim()), (String) vKetersediaan.getSelectedItem()
            );

            boolean success = isUpdate ? manager.updatePerson(id, v) : manager.tambahPerson(v);

            if (success) {
                JOptionPane.showMessageDialog(this, "Data berhasil " + (isUpdate ? "diupdate" : "disimpan") + "!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearVolunteerForm();
                refreshVolunteerTable();
                refreshDashboard();
            } else {
                JOptionPane.showMessageDialog(this, isUpdate ? "Data tidak ditemukan!" : "ID sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteVolunteer() {
        String id = vId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && manager.hapusPerson(id)) {
            JOptionPane.showMessageDialog(this, "Data volunteer berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearVolunteerForm();
            refreshVolunteerTable();
            refreshDashboard();
        }
    }

    private void clearVolunteerForm() {
        vId.setText("");
        vNama.setText("");
        vEmail.setText("");
        vTelepon.setText("");
        vAlamat.setText("");
        vTglLahir.setText("");
        vKeahlian.setText("");
        vJamKerja.setText("");
        vRating.setText("");
        vGender.setSelectedIndex(0);
        vStatus.setSelectedIndex(0);
        vPendidikan.setSelectedIndex(0);
        vKetersediaan.setSelectedIndex(0);
    }

    private void refreshVolunteerTable() {
        volunteerTableModel.setRowCount(0);
        for (Person p : manager.filterByTipe("Volunteer")) {
            Volunteer v = (Volunteer) p;
            volunteerTableModel.addRow(new Object[]{
                    v.getId(), v.getNama(), v.getEmail(), v.getKeahlian(), v.getJamKerja(),
                    v.getStatus(), String.format("%.1f", v.getRating()), v.getLevel(), v.getPoinPrestasi()
            });
        }
    }

    private void searchVolunteer(String nama) {
        volunteerTableModel.setRowCount(0);
        for (Person p : manager.cariByNama(nama)) {
            if (p instanceof Volunteer) {
                Volunteer v = (Volunteer) p;
                volunteerTableModel.addRow(new Object[]{
                        v.getId(), v.getNama(), v.getEmail(), v.getKeahlian(), v.getJamKerja(),
                        v.getStatus(), String.format("%.1f", v.getRating()), v.getLevel(), v.getPoinPrestasi()
                });
            }
        }
    }

    private void loadVolunteerToForm() {
        int row = volunteerTable.getSelectedRow();
        if (row >= 0) {
            String id = volunteerTableModel.getValueAt(row, 0).toString();
            Volunteer v = (Volunteer) manager.cariPersonById(id);
            if (v != null) {
                vId.setText(v.getId());
                vNama.setText(v.getNama());
                vEmail.setText(v.getEmail());
                vTelepon.setText(v.getNoTelepon());
                vAlamat.setText(v.getAlamat());
                vGender.setSelectedItem(v.getJenisKelamin());
                vTglLahir.setText(v.getTanggalLahir().toString());
                vKeahlian.setText(v.getKeahlian());
                vJamKerja.setText(String.valueOf(v.getJamKerja()));
                vStatus.setSelectedItem(v.getStatus());
                vPendidikan.setSelectedItem(v.getTingkatPendidikan());
                vRating.setText(String.valueOf(v.getRating()));
                vKetersediaan.setSelectedItem(v.getKetersediaan());
            }
        }
    }

    // ========== COORDINATOR PANEL ==========
    private JPanel createCoordinatorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cId = new JTextField(15);
        cNama = new JTextField(15);
        cEmail = new JTextField(15);
        cTelepon = new JTextField(15);
        cAlamat = new JTextField(15);
        cTglLahir = new JTextField(15);
        cJumlahVol = new JTextField(15);
        cGaji = new JTextField(15);
        cPengalaman = new JTextField(15);
        cGender = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        cDepartemen = new JComboBox<>(new String[]{"Teater", "Story Telling", "Tari Tradisional", "Vokal", "Puisi", "Pidato", "Tari Kreasi"});
        cLevel = new JComboBox<>(new String[]{"Pengurus Inti", "Anggota"});

        addFormField(formPanel, gbc, 0, "ID:", cId);
        addFormField(formPanel, gbc, 1, "Nama:", cNama);
        addFormField(formPanel, gbc, 2, "Email:", cEmail);
        addFormField(formPanel, gbc, 3, "Telepon:", cTelepon);
        addFormField(formPanel, gbc, 4, "Alamat:", cAlamat);
        addFormField(formPanel, gbc, 5, "Gender:", cGender);
        addFormField(formPanel, gbc, 6, "Tgl Lahir (yyyy-mm-dd):", cTglLahir);
        addFormField(formPanel, gbc, 7, "Departemen:", cDepartemen);
        addFormField(formPanel, gbc, 8, "Jumlah Volunteer:", cJumlahVol);
        addFormField(formPanel, gbc, 9, "Level Jabatan:", cLevel);
        addFormField(formPanel, gbc, 10, "Gaji:", cGaji);
        addFormField(formPanel, gbc, 11, "Pengalaman (tahun):", cPengalaman);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnAdd = createButton("Tambah", SUCCESS);
        JButton btnUpdate = createButton("Update", PRIMARY);
        JButton btnDelete = createButton("Hapus", DANGER);
        JButton btnClear = createButton("Clear", WARNING);

        btnAdd.addActionListener(e -> saveCoordinator(false));
        btnUpdate.addActionListener(e -> saveCoordinator(true));
        btnDelete.addActionListener(e -> deleteCoordinator());
        btnClear.addActionListener(e -> clearCoordinatorForm());

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        String[] columns = {"ID", "Nama", "Email", "Departemen", "Level", "Volunteer", "Pengalaman"};
        coordinatorTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        coordinatorTable = new JTable(coordinatorTableModel);
        coordinatorTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadCoordinatorToForm();
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = createButton("Cari", PRIMARY);
        JButton btnRefresh = createButton("Refresh", SUCCESS);

        btnSearch.addActionListener(e -> searchCoordinator(txtSearch.getText()));
        btnRefresh.addActionListener(e -> refreshCoordinatorTable());

        searchPanel.add(new JLabel("Cari Nama:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(coordinatorTable), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(formPanel), tablePanel);
        splitPane.setDividerLocation(400);

        panel.add(splitPane, BorderLayout.CENTER);
        refreshCoordinatorTable();
        return panel;
    }

    private void saveCoordinator(boolean isUpdate) {
        try {
            String id = cId.getText().trim();
            String nama = cNama.getText().trim();
            String email = cEmail.getText().trim();

            if (id.isEmpty() || nama.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID, Nama, dan Email wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Coordinator c = new Coordinator(
                    id, nama, email, cTelepon.getText().trim(), cAlamat.getText().trim(),
                    LocalDate.now(), (String) cGender.getSelectedItem(),
                    LocalDate.parse(cTglLahir.getText().trim()),
                    (String) cDepartemen.getSelectedItem(), Integer.parseInt(cJumlahVol.getText().trim()),
                    (String) cLevel.getSelectedItem(), Double.parseDouble(cGaji.getText().trim()),
                    Integer.parseInt(cPengalaman.getText().trim())
            );

            boolean success = isUpdate ? manager.updatePerson(id, c) : manager.tambahPerson(c);

            if (success) {
                JOptionPane.showMessageDialog(this, "Data berhasil " + (isUpdate ? "diupdate" : "disimpan") + "!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearCoordinatorForm();
                refreshCoordinatorTable();
                refreshDashboard();
            } else {
                JOptionPane.showMessageDialog(this, isUpdate ? "Data tidak ditemukan!" : "ID sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCoordinator() {
        String id = cId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && manager.hapusPerson(id)) {
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearCoordinatorForm();
            refreshCoordinatorTable();
            refreshDashboard();
        }
    }

    private void clearCoordinatorForm() {
        cId.setText("");
        cNama.setText("");
        cEmail.setText("");
        cTelepon.setText("");
        cAlamat.setText("");
        cTglLahir.setText("");
        cJumlahVol.setText("");
        cGaji.setText("");
        cPengalaman.setText("");
        cGender.setSelectedIndex(0);
        cDepartemen.setSelectedIndex(0);
        cLevel.setSelectedIndex(0);
    }

    private void refreshCoordinatorTable() {
        coordinatorTableModel.setRowCount(0);
        for (Person p : manager.filterByTipe("Koordinator")) {
            Coordinator c = (Coordinator) p;
            coordinatorTableModel.addRow(new Object[]{
                    c.getId(), c.getNama(), c.getEmail(), c.getDepartemen(),
                    c.getLevelJabatan(), c.getJumlahVolunteerDikelola(), c.getPengalamanTahun() + " th"
            });
        }
    }

    private void searchCoordinator(String nama) {
        coordinatorTableModel.setRowCount(0);
        for (Person p : manager.cariByNama(nama)) {
            if (p instanceof Coordinator) {
                Coordinator c = (Coordinator) p;
                coordinatorTableModel.addRow(new Object[]{
                        c.getId(), c.getNama(), c.getEmail(), c.getDepartemen(),
                        c.getLevelJabatan(), c.getJumlahVolunteerDikelola(), c.getPengalamanTahun() + " th"
                });
            }
        }
    }

    private void loadCoordinatorToForm() {
        int row = coordinatorTable.getSelectedRow();
        if (row >= 0) {
            String id = coordinatorTableModel.getValueAt(row, 0).toString();
            Coordinator c = (Coordinator) manager.cariPersonById(id);
            if (c != null) {
                cId.setText(c.getId());
                cNama.setText(c.getNama());
                cEmail.setText(c.getEmail());
                cTelepon.setText(c.getNoTelepon());
                cAlamat.setText(c.getAlamat());
                cGender.setSelectedItem(c.getJenisKelamin());
                cTglLahir.setText(c.getTanggalLahir().toString());
                cDepartemen.setSelectedItem(c.getDepartemen());
                cJumlahVol.setText(String.valueOf(c.getJumlahVolunteerDikelola()));
                cLevel.setSelectedItem(c.getLevelJabatan());
                cGaji.setText(String.valueOf(c.getGaji()));
                cPengalaman.setText(String.valueOf(c.getPengalamanTahun()));
            }
        }
    }

    private JPanel createProjectPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pId = new JTextField(15);
        pNama = new JTextField(15);
        pDeskripsi = new JTextArea(3, 15);
        pTglMulai = new JTextField(15);
        pTglSelesai = new JTextField(15);
        pKoordinator = new JTextField(15);
        pVolDibutuhkan = new JTextField(15);
        pLokasi = new JTextField(15);
        pKategori = new JComboBox<>(new String[]{"Pendidikan", "Kesehatan", "Lingkungan", "Sosial", "Bencana"});
        pStatus = new JComboBox<>(new String[]{"Planned", "Ongoing", "Completed", "Cancelled"});

        addFormField(formPanel, gbc, 0, "ID Proyek:", pId);
        addFormField(formPanel, gbc, 1, "Nama Proyek:", pNama);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Deskripsi:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(pDeskripsi), gbc);

        addFormField(formPanel, gbc, 3, "Tgl Mulai (yyyy-mm-dd):", pTglMulai);
        addFormField(formPanel, gbc, 4, "Tgl Selesai (yyyy-mm-dd):", pTglSelesai);
        addFormField(formPanel, gbc, 5, "Koordinator ID:", pKoordinator);
        addFormField(formPanel, gbc, 6, "Volunteer Dibutuhkan:", pVolDibutuhkan);
        addFormField(formPanel, gbc, 7, "Lokasi:", pLokasi);
        addFormField(formPanel, gbc, 8, "Kategori:", pKategori);
        addFormField(formPanel, gbc, 9, "Status:", pStatus);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnAdd = createButton("Tambah", SUCCESS);
        JButton btnUpdate = createButton("Update", PRIMARY);
        JButton btnDelete = createButton("Hapus", DANGER);
        JButton btnClear = createButton("Clear", WARNING);

        btnAdd.addActionListener(e -> saveProject(false));
        btnUpdate.addActionListener(e -> saveProject(true));
        btnDelete.addActionListener(e -> deleteProject());
        btnClear.addActionListener(e -> clearProjectForm());

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        String[] columns = {"ID", "Nama Proyek", "Koordinator", "Tgl Mulai", "Status", "Volunteer", "Kategori"};
        projectTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        projectTable = new JTable(projectTableModel);
        projectTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadProjectToForm();
                }
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> cmbFilter = new JComboBox<>(new String[]{"Semua", "Planned", "Ongoing", "Completed", "Cancelled"});
        JButton btnFilter = createButton("Filter", PRIMARY);
        JButton btnRefresh = createButton("Refresh", SUCCESS);
        JButton btnExport = createButton("Export CSV", WARNING);

        btnFilter.addActionListener(e -> filterProject((String) cmbFilter.getSelectedItem()));
        btnRefresh.addActionListener(e -> refreshProjectTable());
        btnExport.addActionListener(e -> exportToCSV());

        searchPanel.add(new JLabel("Filter Status:"));
        searchPanel.add(cmbFilter);
        searchPanel.add(btnFilter);
        searchPanel.add(btnRefresh);
        searchPanel.add(btnExport);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(projectTable), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(formPanel), tablePanel);
        splitPane.setDividerLocation(400);

        panel.add(splitPane, BorderLayout.CENTER);
        refreshProjectTable();
        return panel;
    }

    private void saveProject(boolean isUpdate) {
        try {
            String id = pId.getText().trim();
            String nama = pNama.getText().trim();

            if (id.isEmpty() || nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID dan Nama Proyek wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Project p = new Project(
                    id, nama, pDeskripsi.getText().trim(),
                    LocalDate.parse(pTglMulai.getText().trim()),
                    LocalDate.parse(pTglSelesai.getText().trim()),
                    pKoordinator.getText().trim(),
                    Integer.parseInt(pVolDibutuhkan.getText().trim()),
                    pLokasi.getText().trim(),
                    (String) pKategori.getSelectedItem()
            );
            p.setStatus((String) pStatus.getSelectedItem());

            boolean success = isUpdate ? manager.updateProject(id, p) : manager.tambahProject(p);

            if (success) {
                JOptionPane.showMessageDialog(this, "Proyek berhasil " + (isUpdate ? "diupdate" : "disimpan") + "!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearProjectForm();
                refreshProjectTable();
                refreshDashboard();
            } else {
                JOptionPane.showMessageDialog(this, isUpdate ? "Proyek tidak ditemukan!" : "ID sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProject() {
        String id = pId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih proyek yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && manager.hapusProject(id)) {
            JOptionPane.showMessageDialog(this, "Proyek berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearProjectForm();
            refreshProjectTable();
            refreshDashboard();
        }
    }

    private void clearProjectForm() {
        pId.setText("");
        pNama.setText("");
        pDeskripsi.setText("");
        pTglMulai.setText("");
        pTglSelesai.setText("");
        pKoordinator.setText("");
        pVolDibutuhkan.setText("");
        pLokasi.setText("");
        pKategori.setSelectedIndex(0);
        pStatus.setSelectedIndex(0);
    }

    private void refreshProjectTable() {
        projectTableModel.setRowCount(0);
        for (Project p : manager.getAllProjects()) {
            projectTableModel.addRow(new Object[]{
                    p.getIdProyek(), p.getNamaProyek(), p.getKoordinator(), p.getTanggalMulai(),
                    p.getStatus(), p.getJumlahVolunteerTerdaftar() + "/" + p.getJumlahVolunteerDibutuhkan(),
                    p.getKategori()
            });
        }
    }

    private void filterProject(String status) {
        projectTableModel.setRowCount(0);
        List<Project> projects = status.equals("Semua") ? manager.getAllProjects() : manager.getProjectsByStatus(status);

        for (Project p : projects) {
            projectTableModel.addRow(new Object[]{
                    p.getIdProyek(), p.getNamaProyek(), p.getKoordinator(), p.getTanggalMulai(),
                    p.getStatus(), p.getJumlahVolunteerTerdaftar() + "/" + p.getJumlahVolunteerDibutuhkan(),
                    p.getKategori()
            });
        }
    }

    private void loadProjectToForm() {
        int row = projectTable.getSelectedRow();
        if (row >= 0) {
            String id = projectTableModel.getValueAt(row, 0).toString();
            Project p = manager.cariProjectById(id);
            if (p != null) {
                pId.setText(p.getIdProyek());
                pNama.setText(p.getNamaProyek());
                pDeskripsi.setText(p.getDeskripsi());
                pTglMulai.setText(p.getTanggalMulai().toString());
                pTglSelesai.setText(p.getTanggalSelesai().toString());
                pKoordinator.setText(p.getKoordinator());
                pVolDibutuhkan.setText(String.valueOf(p.getJumlahVolunteerDibutuhkan()));
                pLokasi.setText(p.getLokasi());
                pKategori.setSelectedItem(p.getKategori());
                pStatus.setSelectedItem(p.getStatus());
            }
        }
    }

    private void exportToCSV() {
        try {
            String filename = "volunteer_export_" + LocalDate.now() + ".csv";
            manager.exportToCSV(filename);
            JOptionPane.showMessageDialog(this, "Data berhasil diexport ke: " + filename, "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Gagal export: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VolunteerGUI gui = new VolunteerGUI();
            gui.setVisible(true);
        });
    }
}
