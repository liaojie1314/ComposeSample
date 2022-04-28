package com.example.composesample.model.service

import com.example.composesample.model.Network
import com.example.composesample.model.entity.UserInfoResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @FormUrlEncoded
    @POST("user/signIn")
    suspend fun signIn(
        @Field("userName") useName: String,
        @Field("password") password: String,
    ): UserInfoResponse

    companion object {
        fun instance(): UserService {
            return Network.createService(UserService::class.java)
        }
    }
}