package edu.miu.walmart

import java.io.Serializable

class User(
    val firstName: String,
    val lastName: String,
    val username: String,
    val password: String
) : Serializable