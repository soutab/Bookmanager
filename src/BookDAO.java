import java.util.ArrayList;
import java.sql.*;

public class BookDAO {
    private static final String DB_URL = "jdbc:sqlite:books.db";
    public BookDAO() {
        createTable();
    }
    private void createTable() {
        try (Connection con = DriverManager.getConnection(DB_URL);
             Statement stmt = con.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY, title TEXT, author TEXT);";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    //追加
    public static void insertBooks(int id, String title, String author) {

        String sql = "INSERT INTO books (id, title, author) VALUES (?,?,?)";
        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setInt(1,id);
            pstmt.setString(2,title);
            pstmt.setString(3,author);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //削除
    public static void deleteBooks(int id) {

        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection con  = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1,id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //一覧
    public static ArrayList<BookBean> selectAll() {
        String sql = "SELECT * FROM books";
        ArrayList<BookBean> results = new ArrayList<>();

        try(
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {
                results.add(
                        new BookBean(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author")
                        )
                );
            }

        }catch (SQLException e) {
            System.out.println(e);
        }
        return results;
    }

    //検索
    public static ArrayList<BookBean> selectLikeTitle(String keyword) {
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        ArrayList<BookBean> results = new ArrayList<>();

        try(
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()){

                while (rs.next()) {
                    results.add(
                            new BookBean(
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getString("author")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return results;
    }


    public static BookBean selectById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        BookBean result = null;

        try (
                Connection con = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()){

                if(rs.next()) {
                    result = new BookBean(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
}
