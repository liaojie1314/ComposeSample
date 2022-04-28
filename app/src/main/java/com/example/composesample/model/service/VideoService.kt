package com.example.composesample.model.service

import com.example.composesample.model.Network
import com.example.composesample.model.entity.VideoInfoResponse
import com.example.composesample.model.entity.VideoListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {

    @GET("video/list")
    suspend fun list(
        @Query("pageOffset") pageOffset: Int,
        @Query("pageSize") pageSize: Int
    ): VideoListResponse

    @GET("video/info")
    suspend fun info(
        @Query("id") id: String
    ): VideoInfoResponse

    companion object {
        fun instance(): VideoService {
            return Network.createService(VideoService::class.java)
        }
    }

}