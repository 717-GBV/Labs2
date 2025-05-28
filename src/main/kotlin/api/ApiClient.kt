package org.lab.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.runBlocking
import org.lab.model.UserResponse

class ApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
    }

    fun fetchUsers(): UserResponse = runBlocking {
        client.get("https://reqres.in/api/users").body()
    }

    fun serializeUsers(users: UserResponse): String {
        val mapper = jacksonObjectMapper()
        return mapper.writeValueAsString(users)
    }

    fun deserializeUsers(json: String): UserResponse {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json, UserResponse::class.java)
    }
}