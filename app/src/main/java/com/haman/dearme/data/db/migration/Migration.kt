package com.haman.dearme.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDate

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE rate (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "rate INTEGER NOT NULL," +
                    "year INTEGER NOT NULL," +
                    "month INTEGER NOT NULL," +
                    "day INTEGER NOT NULL" +
                    ")"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE challenge (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "category_id INTEGER NOT NULL," +
                    "content TEXT NOT NULL," +
                    "startedAt INTEGER NOT NULL," +
                    "endedAt INTEGER NOT NULL," +
                    "image TEXT," +
                    "FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE SET NULL)"
        )
        database.execSQL(
            "CREATE TABLE challenge_detail (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "challenge_id INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "completed INTEGER NOT NULL DEFAULT 0," +
                    "year INTEGER," +
                    "month INTEGER," +
                    "day INTEGER," +
                    "FOREIGN KEY (challenge_id) REFERENCES challenge (id) ON DELETE CASCADE" +
                    ")"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE completed_challenge (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "challenge_id INTEGER NOT NULL," +
                    "year INTEGER NOT NULL," +
                    "month INTEGER NOT NULL," +
                    "day INTEGER NOT NULL," +
                    "FOREIGN KEY (challenge_id) REFERENCES challenge (id) ON DELETE CASCADE" +
                    ")"
        )
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE schedule (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "content TEXT NOT NULL," +
                    "location TEXT NOT NULL," +
                    "people TEXT NOT NULL," +
                    "startedAt INTEGER NOT NULL," +
                    "endedAt INTEGER NOT NULL," +
                    "important INTEGER NOT NULL," +
                    "state INTEGER NOT NULL" +
                    ")"
        )
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE backup (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "challenge_id INTEGER NOT NULL," +
                    "date INTEGER NOT NULL," +
                    "FOREIGN KEY (challenge_id) REFERENCES challenge (id) ON DELETE CASCADE" +
                    ")"
        )
        var cursor = database.query("SELECT * FROM completed_challenge")
        if (cursor.moveToFirst()) {
            do {
                database.execSQL(
                    "INSERT " +
                            "INTO backup(id, challenge_id, date) " +
                            "VALUES (" +
                            "${cursor.getLong(0)}, " +
                            "${cursor.getLong(1)}," +
                            "${
                                LocalDate.of(
                                    cursor.getInt(2),
                                    cursor.getInt(3),
                                    cursor.getInt(4)
                                )
                            })"
                )
            } while (cursor.moveToNext())
        }
        database.execSQL("DROP TABLE completed_challenge")
        database.execSQL("ALTER TABLE backup RENAME to completed_challenge")

        database.execSQL(
            "CREATE TABLE backup (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "challenge_id INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "completed INTEGER NOT NULL," +
                    "date INTEGER," +
                    "FOREIGN KEY (challenge_id) REFERENCES challenge (id) ON DELETE CASCADE" +
                    ")"
        )
        cursor = database.query("SELECT * FROM challenge_detail")
        if (cursor.moveToFirst()) {
            do {
                database.execSQL(
                    "INSERT " +
                            "INTO backup(id, challenge_id, title, completed, date) " +
                            "VALUES (" +
                            "${cursor.getLong(0)}, " +
                            "${cursor.getLong(1)}," +
                            "\"${cursor.getString(2)}\"," +
                            "${cursor.getInt(3)}," +
                            "${
                                LocalDate.of(
                                    cursor.getInt(4),
                                    cursor.getInt(5),
                                    cursor.getInt(6)
                                )
                            })"
                )
            } while (cursor.moveToNext())
        }
        database.execSQL("DROP TABLE challenge_detail")
        database.execSQL("ALTER TABLE backup RENAME to challenge_detail")
    }
}