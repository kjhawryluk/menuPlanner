package edu.uchicago.kjhawryluk.prolistview.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * This is used to store dates in a sqlLite Db.
 * https://android.jlelse.eu/room-persistence-library-typeconverters-and-database-migration-3a7d68837d6c
 */
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}