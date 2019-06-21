package com.example.blinktasker;

import com.example.blinktasker.JsonModelObject.Example;
import com.example.blinktasker.JsonModelObject.ResponseBodyLogin;
import com.example.blinktasker.JsonModelObject.StatusSuccessOrError;
import com.example.blinktasker.JsonModelObject.locationdriverupdate.LocationDriverUpdate;
import com.example.blinktasker.JsonModelObject.revenue.ExampleRevenue;
import com.example.blinktasker.Objects.MealModel;
import com.example.blinktasker.Objects.OrderModel;
import com.example.blinktasker.Objects.RestaurantModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * USED Call<Void> here but Call<String> Works as well in this case
     *
     * @param token
     * @param clientid
     * @param clientsecret
     * @return
     */
    @FormUrlEncoded
    @POST("api/social/revoke-token")
    Call<Void> getToken(@Field("token") String token,
                        @Field("client_id") String clientid,
                        @Field("client_secret") String clientsecret);

    @FormUrlEncoded
    @POST("api/customer/order/add/")
    Call<StatusSuccessOrError> getCustomerOrderAddPayment(
            @Field("access_token") String accessToken,
            @Field("registration_id") String registrationId,
            @Field("address") String address,
            @Field("order_details") String orderDetails,
            @Field("stripe_token") String stripeToken);


    @FormUrlEncoded
    @POST("api/driver/location/update")
    Call<LocationDriverUpdate> getResponseDriverLocationUpdate(
            @Field("access_token") String accessToken,
            @Field("location") String locationDriverUpdate

    );

    @POST("/api/social/convert-token")
    Call<ResponseBodyLogin> facebookLogin(@Body RequestBody request);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/customer/order/latest/")
    Call<Example> getResponseQuery(
            @Query("access_token") String responseAccessToken
    );


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/driver/order/latest/")
    Call<Example> getResponseDriverOrderLatest(
            @Query("access_token") String responseAccessTokenDriverOrderLatest
    );

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/driver/revenue/")
    Call<ExampleRevenue> getResponseDriverRevenue(
            @Query("access_token") String responseAccessToken
    );


    /**
     * What are we expecting from the response, using the GET request,
     * We need to specify where endpoint is (using our API)
     * Using @Path for "/" and you would use @Query for "&" "?"
     * If we don't specify Call method, the request will be executed synchronously:
     * Network connection,Wait for response,Send the response
     * Only seconds later it will show up in our app, which would crash it.
     * <p>
     * We are using an asynchronous method which Retrofit hides from us
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/customer/meals/{id}/")
    Call<MealModel> getMeals(@Path("id") int id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/customer/registrations")
    Call<RestaurantModel> getResponseRestuarnatList();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("api/driver/orders/ready")
    Call<OrderModel> getReponseDriverOrderReady();




}