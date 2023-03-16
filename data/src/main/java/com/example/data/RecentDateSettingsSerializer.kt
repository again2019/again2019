package com.example.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object RecentDateSettingsSerializer : Serializer<RecentDateSettings> {
    override val defaultValue: RecentDateSettings
        get() = RecentDateSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): RecentDateSettings {
        try {
            return RecentDateSettings.parseFrom(input)
        } catch (exception : InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: RecentDateSettings, output: OutputStream) {
        return t.writeTo(output)
    }
}