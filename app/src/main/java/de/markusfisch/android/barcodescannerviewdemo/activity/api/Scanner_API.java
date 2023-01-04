package de.markusfisch.android.barcodescannerviewdemo.activity.api;

import de.markusfisch.android.barcodescannerviewdemo.activity.model.ScannerRequest;
import de.markusfisch.android.barcodescannerviewdemo.activity.model.ScannerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Scanner_API {

    String base_url = "https://eticketing20221126151145.azurewebsites.net/";

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    //get ticket status

    @POST("Admission/Validate")
    Call<ScannerRequest> getScan(@Body ScannerRequest scanRequest);


}
