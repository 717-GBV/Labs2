package org.lab.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.lab.model.CreateUserRequest
import org.lab.model.CreateUserResponse
import org.lab.model.SingleUserResponse
import org.lab.model.UpdateUserResponse
import org.lab.model.User
import org.lab.model.UserResponse

class ApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            header("x-api-key", "reqres-free-v1")
            header("Content-Type", "application/json")
        }
    }

    suspend fun getUsers(page: Int = 1): UserResponse {
        return client.get("https://reqres.in/api/users?page=$page").body()
    }

    suspend fun getUser(id: Int): SingleUserResponse? {
        try {
            val response = client.get("https://reqres.in/api/users/$id")
            if (response.status == HttpStatusCode.OK) {
                val body = response.bodyAsText()
                if (body.isEmpty() || body == "{}" || body == "null") {
                    return null
                } else {
                    return response.body<SingleUserResponse>()
                }
            } else if (response.status == HttpStatusCode.NotFound) {
                return null
            } else {
                throw Exception("Неожиданный статус-код: ${response.status}")
            }
        } catch (e: SerializationException) {
            return null
        }
    }

    suspend fun createUser(request: CreateUserRequest): CreateUserResponse {
        return client.post("https://reqres.in/api/users") {
            setBody(request)
        }.body()
    }

    suspend fun updateUser(id: Int, request: CreateUserRequest): UpdateUserResponse {
        return client.patch("https://reqres.in/api/users/$id") {
            setBody(request)
        }.body()
    }

    fun serializeUser(users: User): String {
        val mapper = jacksonObjectMapper()
        return mapper.writeValueAsString(users)
    }

    fun deserializeUser(json: String): User {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json, User::class.java)
    }

}
