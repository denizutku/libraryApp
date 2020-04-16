package springboot.libraryApp.GoogleAPI;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import springboot.libraryApp.models.Author;
import springboot.libraryApp.models.Book;
import springboot.libraryApp.models.GoogleAPI.GoogleBook;

import java.io.IOException;

public interface GoogleBookService {

//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl("https://www.googleapis.com/books/v1/")
//                    .addConverterFactory(GsonConverterFactory.create());
//
//    public static <S> S createService(Class<S> serviceClass) {
//        Retrofit retrofit = builder.client(httpClient.build()).build();
//        return retrofit.create(serviceClass);
//    }

    public Book getGoogleBook(String isbn);
    public Author getGoogleBookAuthor(String isbn);
}
