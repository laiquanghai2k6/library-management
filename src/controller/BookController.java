@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        // TODO: Replace with real service call once it's done
        // bookService.addBook(book);
        System.out.println("Received book: " + book.getTitle());

        return ResponseEntity.ok("Book added (mocked)");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        // TODO: Replace with real DB data
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book("Mock Title", "Mock Author", 2023));

        return ResponseEntity.ok(mockBooks);
    }
}
