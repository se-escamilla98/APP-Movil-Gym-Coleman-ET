package com.example.gym_coleman_application.data.api

import com.example.gym_coleman_application.data.model.api.ExerciseInfoResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ExerciseInfoApi {

    // Descarga 50 ejercicios con toda la info (músculos, imágenes, etc.)
    @Headers("Accept-Language: en")
    @GET("exerciseinfo/?limit=50")
    suspend fun getExerciseInfo(): ExerciseInfoResponse
}
