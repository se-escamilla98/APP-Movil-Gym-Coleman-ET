package com.example.gym_coleman_application.data.repository.api

import com.example.gym_coleman_application.data.api.RetrofitClient
import com.example.gym_coleman_application.data.model.api.ExerciseInfoItem

class ExerciseRepository {

    suspend fun getExercises(): List<ExerciseInfoItem> {
        return RetrofitClient.exerciseInfoApi.getExerciseInfo().results
    }
}
