import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Test {
    static String url = "jdbc:mysql://192.168.56.101:4567/madang";
    static String user = "ykkim";
    static String pw = "0201";

    public static void main(String args[]) {


        ResultSet rs;

        //rs = stmt.executeQuery("SELECT * FROM Book");

        Scanner scan = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("종료")) {
            System.out.println("================ madang 데이터베이스 관리 프로그램 ================");
            System.out.println("원하는 기능을 입력하세요 : (삽입, 삭제, 검색, 종료)");
            System.out.print("-----> ");

            userInput = scan.nextLine();
            System.out.println();

            switch (userInput) {
                case "삽입":
                    System.out.println("[ 삽입 기능 ]");
                    insertBook();
                    selectBook();
                    break;
                case "삭제":
                    System.out.println("[ 삭제 기능 ]");
                    selectBook();
                    deleteBookByBookid();
                    selectBook();
                    break;
                case "검색":
                    System.out.println("[ 검색 기능 ]");
                    selectBookByBookname();
                    break;
                case "종료":
                    System.out.println("[ 종료 ]");
                    break;
                // 그 외
                default:
                    System.out.println("잘못 입력하였습니다.");
                    break;
            }
            System.out.println("\n");

        }

    }

    // Insert into Book table
    public static void insertBook() {
        // 변수 입력 받기
        System.out.println("Book(bookid, bookname, publisher, price)");
        Scanner scan = new Scanner(System.in);

        System.out.print("bookid : ");
        int bookid = scan.nextInt();
        scan.nextLine(); // 개행문제 없애기

        System.out.print("bookname : ");
        String bookname = scan.nextLine();

        System.out.print("publisher : ");
        String publisher = scan.nextLine();

        System.out.print("price : ");
        int price = scan.nextInt();

        // sql
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pw);
            String query = "INSERT INTO Book (bookid, bookname, publisher, price) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(query);

            stmt.setInt(1, bookid);
            stmt.setString(2, bookname);
            stmt.setString(3, publisher);
            stmt.setInt(4, price);

            stmt.executeUpdate(); // INSERT 실행

            System.out.println("\n삽입 완료");
        } catch (Exception e) {
            System.out.println();
            System.out.println(e);
        } finally {
            try {
                // Statement 객체 닫기 (쿼리 관련)
                if (stmt != null) {
                    stmt.close();
                }
                // Connection 객체 닫기 (DB 관련)
                if (con != null) {
                    con.close();
                }
                // stmt나 con 닫는 과정에서 오류가 발생할 경우
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Insert into Customer table
    public void insertCustomer() {
        // 변수 입력 받기
        Scanner scan = new Scanner(System.in);

        System.out.print("custid : ");
        int custid = scan.nextInt();
        scan.nextLine(); // 개행문제 없애기

        System.out.print("name : ");
        String name = scan.nextLine();

        System.out.print("address : ");
        String address = scan.nextLine();

        System.out.print("phone : ");
        String phone = scan.nextLine();

        // sql
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pw);
            String query = "INSERT INTO Customer (custid, name, address, phone) VALUES (?, ?, ?, ?)";

            stmt = con.prepareStatement(query);
            stmt.setInt(1, custid);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.setString(4, phone);

            // 삽입
            stmt.executeUpdate();
            System.out.println("\n삽입 완료");
        } catch (Exception e) {
            System.out.println();
            System.out.println(e);
        } finally {
            try {
                // Statement 객체 닫기 (쿼리 관련)
                if (stmt != null) {
                    stmt.close();
                }
                // Connection 객체 닫기 (DB 관련)
                if (con != null) {
                    con.close();
                }
                // stmt나 con 닫는 과정에서 오류가 발생할 경우
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void selectBook() {
        System.out.println("\n[ Book table 상태 ]");
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pw);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Book ");

            // 결과 출력
            while(rs.next()) {
                System.out.println(rs.getInt(1)+"번, "+rs.getString(2)+", "+rs.getString(3)+", "+rs.getInt(4));
            }

        } catch (Exception e) {
            System.out.println();
            System.out.println(e);
        } finally {
            try {
                // Statement 객체 닫기 (쿼리 관련)
                if (stmt != null) {
                    stmt.close();
                }
                // Connection 객체 닫기 (DB 관련)
                if (con != null) {
                    con.close();
                }
                // stmt나 con 닫는 과정에서 오류가 발생할 경우
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteBookByBookid() {
        System.out.println("삭제할 bookid를 입력해 주세요.");
        System.out.print("-----> ");
        Scanner scan = new Scanner(System.in);
        int bookid = scan.nextInt();

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pw);
            stmt = con.prepareStatement("DELETE FROM Book WHERE bookid = ?");
            stmt.setInt(1, bookid);

            // 삭제
            int index = stmt.executeUpdate();

            if (index>0) {
                System.out.println(bookid+"번 책 삭제 완료");
            }

        } catch (Exception e) {
            System.out.println();
            System.out.println(e);
        } finally {
            try {
                // Statement 객체 닫기 (쿼리 관련)
                if (stmt != null) {
                    stmt.close();
                }
                // Connection 객체 닫기 (DB 관련)
                if (con != null) {
                    con.close();
                }
                // stmt나 con 닫는 과정에서 오류가 발생할 경우
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void selectBookByBookname() {
        System.out.print("검색할 책 이름 : ");
        Scanner scan = new Scanner(System.in);
        String bookname = scan.nextLine();

        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pw);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Book WHERE bookname LIKE '%" + bookname + "%'");

            // 결과 출력
            System.out.println("[ 검색 결과 ]");
            while(rs.next()) {
                System.out.println(rs.getInt(1)+"번, "+rs.getString(2)+", "+rs.getString(3)+", "+rs.getInt(4));
            }

        } catch (Exception e) {
            System.out.println();
            System.out.println(e);
        } finally {
            try {
                // Statement 객체 닫기 (쿼리 관련)
                if (stmt != null) {
                    stmt.close();
                }
                // Connection 객체 닫기 (DB 관련)
                if (con != null) {
                    con.close();
                }
                // stmt나 con 닫는 과정에서 오류가 발생할 경우
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}