package org.lab

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.lab.model.User
import org.lab.model.UserResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.lab.api.ApiClient
import org.lab.model.CreateUserRequest

class ApiClientTest {
    private val apiClient = ApiClient()

    @Test
    fun `should fetch data from api`() = runBlocking {
        val userId = 1
        val response = apiClient.getUser(userId)
        assertEquals(true, response != null)
    }

    @Test
    fun `should return 404 data from api`() = runBlocking {
        val response = apiClient.getUser(23)
        assertEquals(true, response == null)
    }

    @Test
    fun `should return created user`() = runBlocking {
        val newUser = apiClient.createUser(CreateUserRequest("Jayne", "leader"))
        assertEquals(true, newUser.id.isNotEmpty())
    }
}