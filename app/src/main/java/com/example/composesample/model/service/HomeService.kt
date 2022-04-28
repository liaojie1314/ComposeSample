package com.example.composesample.model.service

import com.example.composesample.model.Network
import com.example.composesample.model.entity.CategoryResponse
import com.example.composesample.model.entity.SwiperResource
import retrofit2.http.GET

interface HomeService {

    @GET("category/list")
    suspend fun category(): CategoryResponse

    @GET("recommand/banner")
    suspend fun banner():SwiperResource

    companion object {
        fun instance(): HomeService {
            return Network.createService(HomeService::class.java)
        }
    }

}