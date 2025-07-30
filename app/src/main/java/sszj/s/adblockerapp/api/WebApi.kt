package sszj.s.adblockerapp.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import sszj.s.adblockerapp.models.WebItem
import sszj.s.adblockerapp.models.WebsiteWrapper

interface WebApi {

    @GET("/v3/b/688a3404f7e7a370d1f084a3?meta=false")
    suspend fun getWebsite(@Header("X-JSON-Path") category: String): Response<List<WebItem>>

    @GET("/v3/b/688a3404f7e7a370d1f084a3?meta=false")
    @Headers("X-JSON-Path: Websites..category")
    suspend fun getCategories(): Response<List<String>>

    @PUT("v3/b/{binId}")
    suspend fun updateBin(
        @Header("Authorization") masterKey: String,
        @Path("binId") binId: String,
        @Body body: WebsiteWrapper
    ): Response<ResponseBody>
}
