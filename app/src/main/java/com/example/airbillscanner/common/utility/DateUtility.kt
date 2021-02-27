package com.example.airbillscanner.common.utility


import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtility {

    companion object {
        const val DateTimeDatabasePattern = "yyyy-MM-dd HH:mm:ss"
        const val DateDatabasePattern = "yyyy-MM-dd"
        const val TimeDatabasePattern = "HH:mm:ss.SSS"
        const val DatabaseBackupDisplayPattern = "yyyyMMdd_HHmmss"

//        const val DateTimeDatabasePattern = "yyyy-MM-dd HH:mm:ss.SSS"

        const val DateTimeDisplayPattern = "dd/MM/yyyy hh:mm a"
        const val DateDisplayPattern = "dd/MM/yyyy"
        const val TimeDisplayPattern = "hh:mm a"

        fun current(): LocalDateTime = LocalDateTime.now()

        fun now() = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateDatabasePattern))
        fun nowDateTime() = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeDatabasePattern))
        fun now(pattern: String) = LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern))
        fun parseDatabaseDateTime(date: String) = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DateTimeDatabasePattern))
        fun parseDatabaseDate(date: String) = LocalDate.parse(date, DateTimeFormatter.ofPattern(DateDatabasePattern))

        fun convertToDisplayFormat(dateTime: String): String {
            return try {
                val databasePattern = DateTimeFormatter.ofPattern(DateTimeDatabasePattern)
                val displayPattern = DateTimeFormatter.ofPattern(DateTimeDisplayPattern)
                LocalDateTime.parse(dateTime, databasePattern).format(displayPattern)
            } catch (ex: Exception) {
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimeDisplayPattern))
            }
        }

        fun convertToDateDisplayFormat(localDateTime: LocalDateTime): String {
            val displayPattern = DateTimeFormatter.ofPattern(DateDisplayPattern)
            return localDateTime.format(displayPattern)
        }

        fun convertToDateDisplayFormat(localDate: LocalDate): String {
            val displayPattern = DateTimeFormatter.ofPattern(DateDisplayPattern)
            return localDate.format(displayPattern)
        }

        fun convertToTimeDisplayFormat(localDateTime: LocalDateTime): String {
            val displayPattern = DateTimeFormatter.ofPattern(TimeDisplayPattern)
            return localDateTime.format(displayPattern)
        }

        fun convertToDateDatabaseFormat(localDateTime: LocalDateTime): String {
            val databasePattern = DateTimeFormatter.ofPattern(DateDatabasePattern)
            return localDateTime.format(databasePattern)
        }

        fun convertDatabaseDateTimeToDatabaseDate(localDateTimeString: String) : LocalDate {
            val localDateTime = parseDatabaseDateTime(localDateTimeString)
            val pattern = DateTimeFormatter.ofPattern(DateDatabasePattern)
            val localDateString = localDateTime.format(pattern)
            return parseDatabaseDate(localDateString)
        }

        fun convertToDisplayFormat(dateTime: LocalDateTime): String {
            val displayPattern = DateTimeFormatter.ofPattern(DateTimeDisplayPattern)
            return dateTime.format(displayPattern)
        }

        fun convertToDatabaseFormat(dateTime: LocalDateTime): String {
            val databasePattern = DateTimeFormatter.ofPattern(DateTimeDatabasePattern)
            return dateTime.format(databasePattern)
        }

        fun convertStringToDate(sDate: String): Date {
            val format = SimpleDateFormat(DateTimeDatabasePattern, Locale.US)
            var date = Date()
            try {
                date = format.parse(sDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }

        fun dateTimeInMicroSeconds() : String{
            val microSeconds: Long = (System.nanoTime() - System.nanoTime()) / 1000
            val date: Long = System.currentTimeMillis() + microSeconds / 1000
            val dateFormat = SimpleDateFormat(DateTimeDatabasePattern, Locale.US)
            return dateFormat.format(date).toString() + String.format("%03d", microSeconds % 1000)
        }
    }
}