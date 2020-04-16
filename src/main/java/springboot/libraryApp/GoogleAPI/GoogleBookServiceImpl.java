package springboot.libraryApp.GoogleAPI;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.models.Author;
import springboot.libraryApp.models.Book;
import springboot.libraryApp.models.GoogleAPI.GoogleBook;

import java.io.IOException;

@Service
public class GoogleBookServiceImpl implements GoogleBookService {

    private final BookRepository bookRepository;

    public GoogleBookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    Book newBook = new Book();
    Author newAuthor = new Author();
    GoogleBook newGoogleBook = new GoogleBook();

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

    BookInterface googleBookApi = retrofit.create(BookInterface.class);

    @Override
    public Book getGoogleBook(String isbn) {

        Call<GoogleBook> callSync = googleBookApi.getGoogleBook(isbn);
        try {
            Response<GoogleBook> response = callSync.execute();
            newGoogleBook = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = newGoogleBook.getItems().get(0).getVolumeInfo().getTitle();
        String about = newGoogleBook.getItems().get(0).getVolumeInfo().getDescription();
        String category = newGoogleBook.getItems().get(0).getVolumeInfo().getCategories().get(0);
        newBook.setName(title);
        newBook.setAbout(about);
        newBook.setCategory(category);
        return newBook;
    }

    @Override
    public Author getGoogleBookAuthor(String isbn) {
        Call<GoogleBook> callSync = googleBookApi.getGoogleBook(isbn);
        try {
            Response<GoogleBook> response = callSync.execute();
            newGoogleBook = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String authorName = newGoogleBook.getItems().get(0).getVolumeInfo().getAuthors().get(0);
        newAuthor.setName(authorName);
        newAuthor.setGenre("sth");
        newAuthor.setAbout("sth");
        return newAuthor;
    }
}
