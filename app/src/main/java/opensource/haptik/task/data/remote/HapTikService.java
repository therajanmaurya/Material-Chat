package opensource.haptik.task.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import opensource.haptik.task.data.model.Chat;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Rajan Maurya on 02/01/17.
 */
public interface HapTikService {

    String ENDPOINT = "https://gist.githubusercontent.com/therajanmaurya/5ce67f9836b3d4b12a2d20a35b6ceaf8/raw/b09c75624ba37e8e7ab9e1300db79eb863c05b1c/";

    @GET("Haptik-Task-API")
    Observable<Chat> getChats();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static HapTikService newHapTikService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HapTikService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            return retrofit.create(HapTikService.class);
        }
    }
}