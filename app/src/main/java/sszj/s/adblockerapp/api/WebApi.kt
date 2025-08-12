package sszj.s.adblockerapp.api

import retrofit2.Response
import retrofit2.http.*
import sszj.s.adblockerapp.models.WebItem

interface WebApi {

    @GET("/v3/b/688a3404f7e7a370d1f084a3?meta=false")
    suspend fun getWebsite(@Header("X-JSON-Path") category: String): Response<List<WebItem>>

    @GET("/v3/b/688a3404f7e7a370d1f084a3?meta=false")
    @Headers("X-JSON-Path: Websites..category")
    suspend fun getCategories(): Response<List<String>>


}
