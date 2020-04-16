package springboot.libraryApp.GoogleAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import springboot.libraryApp.models.GoogleAPI.GoogleBook;

public interface BookInterface {
    @GET("volumes")
    public Call<GoogleBook> getGoogleBook(@Query("q") String id);
}
