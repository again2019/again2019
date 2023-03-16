package com.example.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object TodayTotalTimeSettingsSerializer : Serializer<TodayTotalTimeSettings> {
    override val defaultValue: TodayTotalTimeSettings
        get() = TodayTotalTimeSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TodayTotalTimeSettings {
        try {
            return TodayTotalTimeSettings.parseFrom(input)
        } catch (exception : InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: TodayTotalTimeSettings, output: OutputStream) {
        return t.writeTo(output)
    }
}