import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("コマンドを選択:");
            System.out.println("1)一覧  2)検索  3)追加  4)削除  q)終了");
            String command = scanner.nextLine();//標準入力から受け取る
            switch (command) {
                case "1":
                    System.out.println("書籍一覧:");
                    ArrayList<BookBean> books = BookDAO.selectAll();
                    for (BookBean book : books)
                        System.out.println(book);
                    break;
                case "2":
                    System.out.println("検索キーワードを入力:");
                    String keyword = scanner.nextLine();
                    System.out.println("検索結果:");
                    ArrayList<BookBean> searchResults = BookDAO.selectLikeTitle(keyword);
                    for (BookBean book : searchResults) {
                        System.out.println(book);
                    }
                    break;
                case "3":
                    System.out.println("書籍IDを入力:");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println("書籍タイトルを入力:");
                    String title = scanner.nextLine();
                    System.out.println("著者を入力:");
                    String author = scanner.nextLine();
                    BookDAO.insertBooks(id, title, author);
                    System.out.println("書籍が追加されました。");
                    break;
                case "4":
                    System.out.println("削除する書籍のIDを入力");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    BookDAO.deleteBooks(deleteId);
                    System.out.println("書籍が削除されました。");
                    break;
                case "q":
                    System.out.println("アプリを終了");
                    System.exit(0);
                    break;
                default:
                    System.out.println("無効なコマンドです。再入力してください。");
            }
        }
    }
}