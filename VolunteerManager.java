import java.util.*;
import java.io.*;

public class VolunteerManager {
    private List<Person> personList;
    private List<Project> projectList;
    private static final String DATA_FILE = "volunteer_data.dat";
    private static final String PROJECT_FILE = "project_data.dat";

    public VolunteerManager() {
        personList = new ArrayList<>();
        projectList = new ArrayList<>();
        loadData();
    }

    public boolean tambahPerson(Person person) {
        for (Person p : personList) {
            if (p.getId().equals(person.getId())) {
                return false;
            }
        }
        personList.add(person);
        saveData();
        return true;
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(personList);
    }

    public Person cariPersonById(String id) {
        for (Person p : personList) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePerson(String id, Person personBaru) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(id)) {
                personList.set(i, personBaru);
                saveData();
                return true;
            }
        }
        return false;
    }

    public boolean hapusPerson(String id) {
        boolean removed = personList.removeIf(p -> p.getId().equals(id));
        if (removed) saveData();
        return removed;
    }

    public List<Person> cariByNama(String nama) {
        List<Person> hasil = new ArrayList<>();
        for (Person p : personList) {
            if (p.getNama().toLowerCase().contains(nama.toLowerCase())) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public List<Person> filterByTipe(String tipe) {
        List<Person> hasil = new ArrayList<>();
        for (Person p : personList) {
            if (p.getTipe().equals(tipe)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public boolean tambahProject(Project project) {
        for (Project p : projectList) {
            if (p.getIdProyek().equals(project.getIdProyek())) {
                return false;
            }
        }
        projectList.add(project);
        saveData();
        return true;
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projectList);
    }

    public Project cariProjectById(String id) {
        for (Project p : projectList) {
            if (p.getIdProyek().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean updateProject(String id, Project projectBaru) {
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getIdProyek().equals(id)) {
                projectList.set(i, projectBaru);
                saveData();
                return true;
            }
        }
        return false;
    }

    public boolean hapusProject(String id) {
        boolean removed = projectList.removeIf(p -> p.getIdProyek().equals(id));
        if (removed) saveData();
        return removed;
    }

    public List<Project> getProjectsByStatus(String status) {
        List<Project> hasil = new ArrayList<>();
        for (Project p : projectList) {
            if (p.getStatus().equals(status)) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public int getJumlahVolunteer() {
        int count = 0;
        for (Person p : personList) {
            if (p instanceof Volunteer) count++;
        }
        return count;
    }

    public int getJumlahCoordinator() {
        int count = 0;
        for (Person p : personList) {
            if (p instanceof Coordinator) count++;
        }
        return count;
    }

    public int getJumlahVolunteerAktif() {
        int count = 0;
        for (Person p : personList) {
            if (p instanceof Volunteer) {
                Volunteer v = (Volunteer) p;
                if ("Aktif".equals(v.getStatus())) count++;
            }
        }
        return count;
    }

    public int getTotalJamKerja() {
        int total = 0;
        for (Person p : personList) {
            if (p instanceof Volunteer) {
                Volunteer v = (Volunteer) p;
                total += v.getJamKerja();
            }
        }
        return total;
    }

    public double getRataRataRating() {
        int count = 0;
        double total = 0;
        for (Person p : personList) {
            if (p instanceof Volunteer) {
                Volunteer v = (Volunteer) p;
                total += v.getRating();
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            personList = (List<Person>) ois.readObject();
        } catch (FileNotFoundException e) {
            personList = new ArrayList<>();
        } catch (Exception e) {
            personList = new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PROJECT_FILE))) {
            projectList = (List<Project>) ois.readObject();
        } catch (FileNotFoundException e) {
            projectList = new ArrayList<>();
        } catch (Exception e) {
            projectList = new ArrayList<>();
        }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(personList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROJECT_FILE))) {
            oos.writeObject(projectList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ID,Nama,Email,Telepon,Tipe,Status/Level");
            for (Person p : personList) {
                if (p instanceof Volunteer) {
                    Volunteer v = (Volunteer) p;
                    writer.printf("%s,%s,%s,%s,%s,%s\n",
                            v.getId(), v.getNama(), v.getEmail(), v.getNoTelepon(),
                            v.getTipe(), v.getStatus());
                } else if (p instanceof Coordinator) {
                    Coordinator c = (Coordinator) p;
                    writer.printf("%s,%s,%s,%s,%s,%s\n",
                            c.getId(), c.getNama(), c.getEmail(), c.getNoTelepon(),
                            c.getTipe(), c.getLevelJabatan());
                }
            }
        }
    }
}
