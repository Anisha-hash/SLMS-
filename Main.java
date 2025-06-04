import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Combined Smart Library Management System minimal example
public class Main extends JFrame {

    // --- Book Model ---
    public static class Book {
        private int bookId;
        private String title, author, isbn;
        private int quantity;

        public Book(int bookId, String title, String author, String isbn, int quantity) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.quantity = quantity;
        }

        public int getBookId() { return bookId; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getIsbn() { return isbn; }
        public int getQuantity() { return quantity; }
    }

    // --- Database Connection Utility ---
    public static class DBConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/slms";
        private static final String USER = "root";
        private static final String PASSWORD = "yourpassword"; // change this

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    // --- Book Data Access Object ---
    public static class BookDAO {

        public void addBook(Book book) throws SQLException {
            String sql = "INSERT INTO books (title, author, isbn, quantity) VALUES (?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setInt(4, book.getQuantity());
                stmt.executeUpdate();
            }
        }

        public List<Book> getAllBooks() throws SQLException {
            List<Book> books = new ArrayList<>();
            String sql = "SELECT * FROM books";
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getInt("quantity")));
                }
            }
            return books;
        }
    }

    // --- GUI Components ---
    private JTextField titleField, authorField, isbnField, quantityField;
    private JTable bookTable;
    private BookDAO bookDAO = new BookDAO();

    public Main() {
        setTitle("Smart Library Management System");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Title:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        panel.add(isbnField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        JButton addButton = new JButton("Add Book");
        panel.add(addButton);

        JButton refreshButton = new JButton("Refresh List");
        panel.add(refreshButton);

        add(panel, BorderLayout.NORTH);

        bookTable = new JTable();
        add(new JScrollPane(bookTable), BorderLayout.CENTER);

        addButton.addActionListener(e -> addBook());
        refreshButton.addActionListener(e -> loadBooks());

        loadBooks();
    }

    private void addBook() {
        try {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            Book book = new Book(0, title, author, isbn, quantity);
            bookDAO.addBook(book);

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            clearFields();
            loadBooks();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number.");
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + sqle.getMessage());
        }
    }

    private void loadBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "ISBN", "Quantity"}, 0);
            for (Book b : books) {
                model.addRow(new Object[]{b.getBookId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getQuantity()});
            }
            bookTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
