package springboot.libraryApp.GoogleAPI;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import springboot.libraryApp.Repositories.AuthorRepository;
import springboot.libraryApp.Repositories.BookRepository;
import springboot.libraryApp.models.Author;
import springboot.libraryApp.models.Book;
import springboot.libraryApp.models.GoogleAPI.GoogleBook;

import java.io.IOException;

@Service
public class GoogleBookServiceImpl implements GoogleBookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public GoogleBookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

    BookInterface googleBookApi = retrofit.create(BookInterface.class);

    @Override
    public Book getGoogleBook(String isbn) {

        Book newBook = new Book();
        Author newAuthor = new Author();
        GoogleBook newGoogleBook = new GoogleBook();

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
        authorRepository.save(newAuthor);

        String title = newGoogleBook.getItems().get(0).getVolumeInfo().getTitle();
        String about = newGoogleBook.getItems().get(0).getVolumeInfo().getDescription();
        String category = newGoogleBook.getItems().get(0).getVolumeInfo().getCategories().get(0);
        String imageLink = newGoogleBook.getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail();
        String publishedDate = newGoogleBook.getItems().get(0).getVolumeInfo().getPublishedDate();
        int pageCount = newGoogleBook.getItems().get(0).getVolumeInfo().getPageCount();
        float rating = newGoogleBook.getItems().get(0).getVolumeInfo().getAverageRating();
        newBook.setName(title);
        newBook.setAbout(about);
        newBook.setCategory(category);
        newBook.setImageLink(imageLink);
        newBook.setAuthor(newAuthor);
        newBook.setPublishedDate(publishedDate);
        newBook.setPageCount(pageCount);
        newBook.setRating(rating);
        return newBook;
    }

}
