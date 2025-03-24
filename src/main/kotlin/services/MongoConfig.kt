package com.smart_conspect.services

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets

object MongoConfig {
    private const val username = "admin"
    private const val password = "password"
    private const val MONGO_URI = "mongodb://$username:$password@localhost:27017/smartconspect_db?authSource=admin"
    private const val DATABASE_NAME = "smart_conspect_db" // Replace with your database name

    val client: MongoClient = MongoClients.create(MONGO_URI)
    val database = client.getDatabase(DATABASE_NAME)
    val gridFSBucket: GridFSBucket = GridFSBuckets.create(database)
}