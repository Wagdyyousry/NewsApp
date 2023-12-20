package com.wagdybuild.newsapp.db

import androidx.room.TypeConverter
import com.wagdybuild.newsapp.models.Source

class SourceConverter {
    @TypeConverter
    fun fromSource(source : Source):String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String):Source{
        return Source(name,name)

    }
}