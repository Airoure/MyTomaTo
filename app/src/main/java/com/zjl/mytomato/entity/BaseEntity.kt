package com.zjl.mytomato.entity

interface BaseLocalEntity {
    fun toNetwork(username: String): BaseNetworkEntity
}

interface BaseNetworkEntity {
    fun toLocal(): BaseLocalEntity
}