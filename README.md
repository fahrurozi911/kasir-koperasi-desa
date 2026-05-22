# 🏪 Kasir Koperasi Desa

Aplikasi Desktop Manajemen Kasir Koperasi Desa dibangun menggunakan **Java Swing** dan **MySQL** dengan tampilan modern dan profesional.

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apache-maven)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![License](https://img.shields.io/badge/License-MIT-green)

---

## 📋 Daftar Isi
- [Fitur Utama](#-fitur-utama)
- [Teknologi](#-teknologi)
- [Struktur Proyek](#-struktur-proyek)
- [ERD Database](#-erd-database)
- [Instalasi](#-instalasi)
- [Cara Menjalankan](#-cara-menjalankan)
- [Login Default](#-login-default)
- [Screenshot](#-screenshot)
- [Kontributor](#-kontributor)

---

## ✨ Fitur Utama

### 👨‍💼 Kasir
| Fitur | Deskripsi |
|-------|-----------|
| **CRUD Barang** | Tambah, ubah, hapus, dan cari data barang |
| **Transaksi Penjualan** | Pilih barang, input jumlah, hitung subtotal & total otomatis |
| **Pembayaran** | Input bayar dan hitung kembalian otomatis |
| **Riwayat Transaksi** | Lihat semua transaksi beserta detailnya |
| **Laporan** | Harian, Bulanan, Stok Barang, Barang Terlaris |
| **Export Excel** | Export laporan ke file Excel (.xlsx) |

### 👨‍💻 Pimpinan
| Fitur | Deskripsi |
|-------|-----------|
| **Dashboard** | Ringkasan total barang, transaksi, dan pendapatan |
| **Laporan Lengkap** | Akses semua jenis laporan |
| **Export Excel** | Export laporan ke file Excel (.xlsx) |

---

## 🛠 Teknologi

| Teknologi | Versi | Kegunaan |
|-----------|-------|----------|
| **Java JDK** | 17 | Bahasa pemrograman utama |
| **Java Swing** | - | Framework GUI desktop |
| **MySQL** | 8.0 | Database (via XAMPP) |
| **JDBC** | - | Koneksi Java ke MySQL |
| **Maven** | 3.9 | Build tools & dependency management |
| **FlatLaf** | 3.2.5 | Look and Feel modern |
| **Apache POI** | 5.2.5 | Export data ke Excel |
| **JCalendar** | 1.4 | Date picker component |

---

## 📁 Struktur Proyek

```
kasir-koperasi-desa/
├── pom.xml                          # Konfigurasi Maven & dependencies
├── database.sql                     # Script database lengkap
├── README.md                        # Dokumentasi proyek
├── .gitignore                       # File yang diabaikan Git
└── src/
    └── main/
        └── java/
            └── com/
                └── kasirkoperasi/
                    ├── MainApp.java              # Entry point aplikasi
                    ├── model/                    # POJO classes
                    │   ├── User.java             # Model pengguna
                    │   ├── Barang.java           # Model barang
                    │   ├── Transaksi.java        # Model transaksi
                    │   ├── DetailTransaksi.java  # Model detail transaksi
                    │   └── LaporanData.java      # Model laporan
                    ├── dao/                      # Data Access Object
                    │   ├── UserDAO.java          # Query user & login
                    │   ├── BarangDAO.java        # CRUD barang
                    │   ├── TransaksiDAO.java     # Simpan transaksi
                    │   └── LaporanDAO.java       # Query laporan
                    ├── util/                     # Utility classes
                    │   ├── DatabaseConnection.java  # Koneksi JDBC
                    │   └── ExcelExporter.java    # Export ke Excel
                    └── view/                     # GUI Swing
                        ├── MainFrame.java        # Frame utama + sidebar
                        ├── LoginPanel.java       # Form login
                        ├── DashboardKasirPanel.java      # Dashboard kasir
                        ├── DashboardPimpinanPanel.java    # Dashboard pimpinan
                        ├── BarangPanel.java      # CRUD barang
                        ├── TransaksiPanel.java   # Form transaksi
                        ├── RiwayatTransaksiPanel.java  # Riwayat transaksi
                        └── LaporanPanel.java     # Laporan & export
```

---

## 🗄 ERD Database

```
┌─────────┐       ┌──────────────┐       ┌───────────────────┐
│  users  │       │  transaksi   │       │ detail_transaksi  │
├─────────┤       ├──────────────┤       ├───────────────────┤
│ id (PK) │──┐    │ id_transaksi │──┐    │ id_detail (PK)    │
│ username│  └───>│ (PK)         │  └───>│ id_transaksi (FK) │
│ password│       │ no_faktur    │       │ id_barang (FK)    │
│ nama    │       │ tanggal      │       │ jumlah            │
│ role    │       │ total        │       │ subtotal          │
└─────────┘       │ bayar        │       └───────────────────┘
                  │ kembalian    │                │
                  │ id_user (FK) │                │
                  └──────────────┘                │
                                                  │
                  ┌──────────────┐                │
                  │    barang    │                │
                  ├──────────────┤                │
                  │ id_barang(PK)│<───────────────┘
                  │ kode_barang  │
                  │ nama_barang  │
                  │ kategori     │
                  │ harga_beli   │
                  │ harga_jual   │
                  │ stok         │
                  │ satuan       │
                  │ tanggal_masuk│
                  └──────────────┘
```

**Relasi:**
- **users** 1 ────< **transaksi** (One to Many)
- **transaksi** 1 ────< **detail_transaksi** (One to Many)
- **barang** 1 ────< **detail_transaksi** (One to Many)

---

## 💻 Instalasi

### Prasyarat
Pastikan software berikut sudah terinstall di komputer Anda:
- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Apache Maven 3.9+](https://maven.apache.org/download.cgi)
- [XAMPP](https://www.apachefriends.org/download.html) (MySQL)
- [Visual Studio Code](https://code.visualstudio.com/) atau IDE lainnya
- [Git](https://git-scm.com/downloads) (opsional)

### Langkah Instalasi

#### 1. Clone Repository
```bash
git clone https://github.com/fahrurozi911/kasir-koperasi-desa.git
cd kasir-koperasi-desa
```

#### 2. Setup Database
1. Nyalakan **XAMPP** (Apache & MySQL)
2. Buka **phpMyAdmin** (http://localhost/phpmyadmin)
3. Jalankan script `database.sql` atau import file tersebut
4. Atau jalankan via terminal MySQL:
```bash
mysql -u root -p < database.sql
```

#### 3. Konfigurasi Database
Edit file `src/main/java/com/kasirkoperasi/util/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/kasir_koperasi_desa";
private static final String USER = "root";
private static final String PASSWORD = ""; // Sesuaikan dengan password MySQL Anda
```

#### 4. Build Project
```bash
mvn clean compile
```

---

## 🚀 Cara Menjalankan

### Menggunakan Maven (Terminal)
```bash
mvn exec:java
```

### Menggunakan VSCode
1. Install **Extension Pack for Java** di VSCode
2. Buka folder `kasir-koperasi-desa` di VSCode
3. Tunggu hingga semua dependency terdownload
4. Buka file `MainApp.java`
5. Klik tombol **Run** (▶️) atau klik kanan → **Run Java**

### Menggunakan JAR
```bash
mvn clean package
java -jar target/kasir-koperasi-desa-1.0.0-jar-with-dependencies.jar
```

---

## 🔑 Login Default

| Role | Username | Password |
|------|----------|----------|
| **Kasir** | `kasir1` | `12345` |
| **Pimpinan** | `pimpinan` | `12345` |

---

## 🤝 Kontributor

- **Ahmad Fahru Rozi** - [GitHub](https://github.com/fahrurozi911)

---

## 📝 Lisensi

Proyek ini dilisensikan di bawah lisensi **MIT** - lihat file [LICENSE](LICENSE) untuk detail lebih lanjut.

---

## 📞 Kontak

Jika ada pertanyaan atau masukan, silakan hubungi:
- GitHub: [fahrurozi911](https://github.com/fahrurozi911)
- Email: [email Anda]

---

**⭐ Jangan lupa beri bintang jika proyek ini bermanfaat! ⭐**

---

Dibuat dengan ❤️ untuk Koperasi Desa Indonesia
