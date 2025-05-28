package org.lab

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.lab.model.User
import org.lab.model.UserResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.lab.api.ApiClient

class ApiClientTest {
    private val apiClient = ApiClient()

    @Test
    fun `should fetch data from api`() {
        val response = apiClient.fetchUsers()
        assertEquals(true, response.data.isNotEmpty())
        assertEquals(true, response.data[0].id > 0)
    }

    @Test
    fun `should fetch serialize and deserialize`() {
        val user = User(
            id = 1,
            email = "test@example.com",
            firstName = "John",
            lastName = "Doe",
            avatar = "https://example.com/avatar.jpg"
        )
        val userResponse = UserResponse(data = listOf(user))

        val json = apiClient.serializeUsers(userResponse)

        val deserialized = apiClient.deserializeUsers(json)

        assertEquals(userResponse, deserialized)
        assertEquals(user.id, deserialized.data[0].id)
        assertEquals(user.email, deserialized.data[0].email)
        assertEquals(user.firstName, deserialized.data[0].firstName)
        assertEquals(user.lastName, deserialized.data[0].lastName)
        assertEquals(user.avatar, deserialized.data[0].avatar)
    }

    @Test
    fun `should serialize and deserialize empty list`() {
        val userResponse = UserResponse(data = emptyList())

        val json = apiClient.serializeUsers(userResponse)

        val deserialized = apiClient.deserializeUsers(json)

        assertEquals(userResponse, deserialized)
        assertEquals(0, deserialized.data.size)
    }

    @Test
    fun `should throw ex when invalid json`() {
        val invalidJson = """{"invalid_field": "value"}"""
        assertThrows<MismatchedInputException> { apiClient.deserializeUsers(invalidJson) }
    }

    @Test
    fun `should serialize and deserialize multiple objects`() {
        val users = listOf(
            User(
                id = 1,
                email = "test1@example.com",
                firstName = "John",
                lastName = "Doe",
                avatar = "https://example.com/avatar1.jpg"
            ),
            User(
                id = 2,
                email = "test2@example.com",
                firstName = "Jane",
                lastName = "Smith",
                avatar = "https://example.com/avatar2.jpg"
            )
        )
        val userResponse = UserResponse(data = users)

        val json = apiClient.serializeUsers(userResponse)

        val deserialized = apiClient.deserializeUsers(json)

        assertEquals(userResponse, deserialized)
        assertEquals(2, deserialized.data.size)
        assertEquals(users[0], deserialized.data[0])
        assertEquals(users[1], deserialized.data[1])
    }
}