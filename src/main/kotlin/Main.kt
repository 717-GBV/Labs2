package org.lab

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.runBlocking
import org.lab.api.ApiClient

fun main() = runBlocking {
    val apiClient = ApiClient()
    val userId = 1
    val users = apiClient.getUser(userId)
    if (users != null) {
        println("Fetched users: ${users.data.lastName}")
        val json = apiClient.serializeUser(users.data)
        println("Serialized JSON: $json")

        val deserialized = apiClient.deserializeUser(json)
        println("Deserialized users: ${deserialized.lastName}")
    }

}