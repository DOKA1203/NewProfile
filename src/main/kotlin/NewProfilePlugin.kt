package kr.doka.lab.newprofile

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class NewProfilePlugin: JavaPlugin() {

    companion object {
        lateinit var Instance: NewProfilePlugin
        private var sqliteConnection: Connection? = null
    }

    override fun onEnable() {
        Instance = this
        dataFolder.mkdirs()
        '''
        CREATE TABLE IF NOT EXISTS YourTableName (
            uuid VARCHAR(36) PRIMARY KEY,
            height DOUBLE,
            displayName VARCHAR(255)
        );
        '''
    }
    override fun onDisable() {
        
    }

    fun getConnection(): Connection {
        if (sqliteConnection == null) {
            try {
                Class.forName("org.sqlite.JDBC")
                sqliteConnection = DriverManager.getConnection("jdbc:sqlite:${dataFolder.absolutePath}${File.separator}profile.sql")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return sqliteConnection!!
    }
}