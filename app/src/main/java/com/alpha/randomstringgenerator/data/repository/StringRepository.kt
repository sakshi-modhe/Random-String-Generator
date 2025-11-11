package com.alpha.randomstringgenerator.data.repository

import android.content.ContentResolver
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.alpha.randomstringgenerator.data.model.RandomString
import org.json.JSONObject

class StringRepository(private val contentResolver: ContentResolver) {

    companion object {
        private const val TAG = "StringRepository"
    }

    private val contentUrl = "content://com.iav.contestdataprovider/text".toUri()

    fun getRandomString(maxLength: Int): Result<RandomString> {
        Log.d(TAG, "Fetching random string for maxLength=$maxLength")
        return try {
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                queryProvider(maxLength)
            } else {
                null
            }

            if (result != null) {
                Log.d(TAG, "Received data from provider: ${result.value}")
                Result.success(result)
            } else {
                Log.e(TAG, "No data received from provider")
                Result.failure(Exception("No data received from provider"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during getRandomString: ${e.message}", e)
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun queryProvider(maxLength: Int): RandomString? {
        Log.d(TAG, "Querying provider with maxLength=$maxLength")

        val queryArgs = Bundle().apply {
            putInt("length", maxLength)
            putInt("maxLength", maxLength)
        }

        Log.d(TAG, "Querying URI: $contentUrl with args: $queryArgs")

        val cursor = contentResolver.query(
            contentUrl,
            null,
            queryArgs,
            null
        )

        cursor?.use {
            Log.d(TAG, "Provider returned columns: ${it.columnNames.joinToString()}")
            if (it.moveToFirst()) {
                val dataIndex = it.getColumnIndex("data")
                if (dataIndex != -1) {
                    val rawJson = it.getString(dataIndex)
                    Log.d(TAG, "Raw JSON from provider: $rawJson")

                    val jsonObject = JSONObject(rawJson)
                    val randomTextObj = jsonObject.getJSONObject("randomText")

                    return RandomString(
                        value = randomTextObj.getString("value"),
                        length = randomTextObj.getInt("length"),
                        created = randomTextObj.getString("created")
                    )
                } else {
                    Log.e(TAG, "data column not found in provider response")
                }
            } else {
                Log.e(TAG, "Cursor is empty — no data")
            }
        }

        return null
    }
}
