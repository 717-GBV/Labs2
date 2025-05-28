package org.lab

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import org.lab.api.ApiClient

fun main() {
    val apiClient = ApiClient()
    val users = apiClient.fetchUsers()
    println("Fetched users: ${users.data}")

    val json = apiClient.serializeUsers(users)
    println("Serialized JSON: $json")

    val deserialized = apiClient.deserializeUsers(json)
    println("Deserialized users: ${deserialized.data}")
}