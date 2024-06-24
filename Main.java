import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
  static int soThuTuBenhAn = 0;

  public static void main(String[] args) {
    QuanLyBenhAn quanLyBenhAn = new QuanLyBenhAn();
    Scanner scanner = new Scanner(System.in);

    int luaChon;
    do {
      System.out.println("\n===== QUAN LY BENH AN =====");
      System.out.println("1. Them benh an");
      System.out.println("2. Xoa benh an");
      System.out.println("3. Hien thi danh sach benh an");
      System.out.println("0. Thoat");
      System.out.print("Nhap lua chon: ");

      luaChon = scanner.nextInt();
      scanner.nextLine();

      switch (luaChon) {
        case 1:
          themBenhAn(quanLyBenhAn, scanner);
          break;
        case 2:
          xoaBenhAn(quanLyBenhAn, scanner);
          break;
        case 3:
          quanLyBenhAn.hienThiDanhSachBenhAn();
          break;
        case 0:
          System.out.println("Thoát chương trình.");
          break;
        default:
          System.out.println("Khong hop le.");
      }
    } while (luaChon != 0);
  }

  private static void themBenhAn(QuanLyBenhAn quanLyBenhAn, Scanner scanner) {
    System.out.print("Nhap loai benh an(1 Thuong, 2 VIP): ");
    int loaiBenhAn = scanner.nextInt();
    scanner.nextLine();

    BenhAn benhAn;
    try {
      benhAn = nhapThongTinBenhAn(loaiBenhAn, scanner);
      quanLyBenhAn.themBenhAn(benhAn);
      System.out.println("Them benh an thanh cong.");
    } catch (ParseException | DuplicateMedicalRecordException e) {
      System.out.println("Loi: " + e.getMessage());
    }
  }

  private static void xoaBenhAn(QuanLyBenhAn quanLyBenhAn, Scanner scanner) {
    System.out.print("Nhap ma benh an can xoa: ");
    String maBenhAn = scanner.nextLine();
    System.out.print("!!! Canh bao ban co chac chan muon xoa?(y/n): ");
    String check = scanner.nextLine();
    if (check.equals("y")|| check.equals("Y")) {
      quanLyBenhAn.xoaBenhAn(maBenhAn);
      System.out.println("Thanh cong.");
      soThuTuBenhAn--;
    }
  }

  private static BenhAn nhapThongTinBenhAn(int loaiBenhAn, Scanner scanner)
      throws ParseException, DuplicateMedicalRecordException {
    System.out.print("Nhap ma benh an: ");
    String maBenhAn = scanner.nextLine();
    System.out.print("Nhap ma benh nhan: ");
    String maBenhNhan = scanner.nextLine();
    System.out.print("Nhap ten benh nhan: ");
    String tenBenhNhan = scanner.nextLine();
    if (!kiemTraMaBenhAn(maBenhAn) || !kiemTraMaBenhNhan(maBenhNhan)) {
      throw new DuplicateMedicalRecordException("Ma benh an hoac ma benh nha khong hop le.");
    }
    System.out.print("Nhap ngay vao vien (dd/MM/yyyy): ");
    Date ngayNhapVien = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
    System.out.print("Nhap ngay ra vien (dd/MM/yyyy): ");
    Date ngayRaVien = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
    System.out.print("Ly do nhap vien: ");
    String lyDoNhapVien = scanner.nextLine();

    if (loaiBenhAn == 1) {
      System.out.print("Nhap phi nam vien: ");
      double phiNamVien = scanner.nextDouble();
      scanner.nextLine();
      return new BenhAnThuong(++soThuTuBenhAn, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien,
          lyDoNhapVien, phiNamVien);
    } else {
      System.out.print("Nhap loai VIP (VIP I, VIP II, VIP III): ");
      String loaiVIP = scanner.nextLine();
      System.out.print("Nhap thoi han VIP (dd/MM/yyyy): ");
      Date thoiHanVIP = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
      return new BenhAnVIP(++soThuTuBenhAn, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien,
          lyDoNhapVien, loaiVIP, thoiHanVIP);
    }
  }

  public static boolean kiemTraMaBenhAn(String ma) {
    return ma.matches("BA-\\d{3}");
  }

  public static boolean kiemTraMaBenhNhan(String ma) {
    return ma.matches("BN-\\d{3}");
  }
}

abstract class BenhAn {
  protected int soThuTuBenhAn;
  protected String maBenhAn;
  protected String maBenhNhan;
  protected String tenBenhNhan;
  protected Date ngayNhapVien;
  protected Date ngayRaVien;
  protected String lyDoNhapVien;

  public BenhAn(int soThuTuBenhAn, String maBenhAn, String maBenhNhan, String tenBenhNhan, Date ngayNhapVien,
      Date ngayRaVien, String lyDoNhapVien) {
    this.soThuTuBenhAn = soThuTuBenhAn;
    this.maBenhAn = maBenhAn;
    this.maBenhNhan = maBenhNhan;
    this.tenBenhNhan = tenBenhNhan;
    this.ngayNhapVien = ngayNhapVien;
    this.ngayRaVien = ngayRaVien;
    this.lyDoNhapVien = lyDoNhapVien;
  }

  public abstract void nhapThongTin(Scanner scanner) throws ParseException, DuplicateMedicalRecordException;

  public abstract void hienThiThongTin();
}

class BenhAnThuong extends BenhAn {
  public double phiNamVien;

  public BenhAnThuong(int soThuTuBenhAn, String maBenhAn, String maBenhNhan, String tenBenhNhan, Date ngayNhapVien,
      Date ngayRaVien, String lyDoNhapVien, double phiNamVien) {
    super(soThuTuBenhAn, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
    this.phiNamVien = phiNamVien;
  }

  @Override
  public void nhapThongTin(Scanner scanner) throws ParseException {
    System.out.print("Nhap phi nam vien: ");
    phiNamVien = scanner.nextDouble();
    scanner.nextLine();
  }

  @Override
  public void hienThiThongTin() {
    System.out.println(maBenhAn + "|" + maBenhNhan + "|" + tenBenhNhan + "|"
        + new SimpleDateFormat("dd/MM/yyyy").format(ngayNhapVien) + "|"
        + new SimpleDateFormat("dd/MM/yyyy").format(ngayRaVien) + "|" + lyDoNhapVien + "|" + phiNamVien + "|");
  }
}

class BenhAnVIP extends BenhAn {
  public String loaiVIP;
  public Date thoiHanVIP;

  public BenhAnVIP(int soThuTuBenhAn, String maBenhAn, String maBenhNhan, String tenBenhNhan, Date ngayNhapVien,
      Date ngayRaVien, String lyDoNhapVien, String loaiVIP, Date thoiHanVIP) {
    super(soThuTuBenhAn, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
    this.loaiVIP = loaiVIP;
    this.thoiHanVIP = thoiHanVIP;
  }

  @Override
  public void nhapThongTin(Scanner scanner) throws ParseException {
    // super.nhapThongTin(scanner);
    System.out.print("Nhap loai VIP (VIP I, VIP II, VIP III): ");
    loaiVIP = scanner.nextLine();
    System.out.print("Nhap thoi han VIP (dd/MM/yyyy): ");
    thoiHanVIP = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
  }

  @Override
  public void hienThiThongTin() {
    System.out.println(maBenhAn + "|" + maBenhNhan + "|" + tenBenhNhan + "|"
        + new SimpleDateFormat("dd/MM/yyyy").format(ngayNhapVien) + "|"
        + new SimpleDateFormat("dd/MM/yyyy").format(ngayRaVien) + "|" + lyDoNhapVien + "|" + loaiVIP + "|"
        + new SimpleDateFormat("dd/MM/yyyy").format(thoiHanVIP) + "|");
  }
}

class DuplicateMedicalRecordException extends Exception {
  public DuplicateMedicalRecordException(String message) {
    super(message);
  }
}

class QuanLyBenhAn {
  private List<BenhAn> danhSachBenhAn;
  private String fileName = "data/medical_records.csv";

  public QuanLyBenhAn() {
    danhSachBenhAn = new ArrayList<>();
    docDuLieuTuFile();
  }

  public void themBenhAn(BenhAn benhAn) throws DuplicateMedicalRecordException {
    if (kiemTraMaBenhAnTonTai(benhAn.maBenhAn)) {
      throw new DuplicateMedicalRecordException("Da ton tai.");
    }
    danhSachBenhAn.add(benhAn);
    luuDuLieuVaoFile();
  }

  public void xoaBenhAn(String maBenhAn) {
    danhSachBenhAn.removeIf(ba -> ba.maBenhAn.equalsIgnoreCase(maBenhAn));
    luuDuLieuVaoFile();
  }

  public void hienThiDanhSachBenhAn() {
    if (danhSachBenhAn.isEmpty()) {
      System.out.println("Danh sách bệnh án trống.");
      return;
    }
    int soThuTuBenhAn = 0;
    System.out.println(
        "|STT|Me benh an|Ma benh nhan|Ten benh nhan|Ngay nhap vien|Ngay ra vien|Ly do nhap vin|Phi nam vien/Loai VIP|Thoi han VIP|");
    for (BenhAn benhAn : danhSachBenhAn) {
      soThuTuBenhAn++;
      System.out.print("|" + soThuTuBenhAn + "|");
      benhAn.hienThiThongTin();
    }
  }

  private boolean kiemTraMaBenhAnTonTai(String maBenhAn) {
    for (BenhAn ba : danhSachBenhAn) {
      if (ba.maBenhAn.equalsIgnoreCase(maBenhAn)) {
        return true;
      }
    }
    return false;
  }

  private void docDuLieuTuFile() {
    int soThuTuBenhAn = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 7) {
          String maBenhAn = parts[1];
          String maBenhNhan = parts[2];
          String tenBenhNhan = parts[3];
          Date ngayNhapVien = new SimpleDateFormat("dd/MM/yyyy").parse(parts[4]);
          Date ngayRaVien = new SimpleDateFormat("dd/MM/yyyy").parse(parts[5]);
          String lyDoNhapVien = parts[6];

          BenhAn benhAn;
          if (parts.length == 8) {
            double phiNamVien = Double.parseDouble(parts[7]);
            benhAn = new BenhAnThuong(++soThuTuBenhAn, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien,
                lyDoNhapVien, phiNamVien);
          } else if (parts.length >= 9) {
            String loaiVIP = parts[7];
            Date thoiHanVIP = new SimpleDateFormat("dd/MM/yyyy").parse(parts[8]);
            benhAn = new BenhAnVIP(++soThuTuBenhAn, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien,
                lyDoNhapVien, loaiVIP, thoiHanVIP);
          } else {
            throw new ParseException("Loi doc du lieu.", 0);
          }
          danhSachBenhAn.add(benhAn);
        }
      }
    } catch (IOException | ParseException e) {
      System.out.println("Loi doc: " + e.getMessage());
    }
  }

  private void luuDuLieuVaoFile() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
      for (BenhAn benhAn : danhSachBenhAn) {
        writer.println(benhAn.soThuTuBenhAn + "," + benhAn.maBenhAn + "," + benhAn.maBenhNhan + ","
            + benhAn.tenBenhNhan + "," + new SimpleDateFormat("dd/MM/yyyy").format(benhAn.ngayNhapVien) + ","
            + new SimpleDateFormat("dd/MM/yyyy").format(benhAn.ngayRaVien) + "," + benhAn.lyDoNhapVien);
        if (benhAn instanceof BenhAnThuong) {
          writer.println(((BenhAnThuong) benhAn).phiNamVien);
        } else {
          writer.println(((BenhAnVIP) benhAn).loaiVIP + ","
              + new SimpleDateFormat("dd/MM/yyyy").format(((BenhAnVIP) benhAn).thoiHanVIP));
        }
      }
    } catch (IOException e) {
      System.out.println("Loi luu: " + e.getMessage());
    }
  }
}
