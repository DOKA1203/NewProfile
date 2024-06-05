package kr.doka.lab.newprofile

import kr.doka.lab.newprofile.NewProfilePlugin.Companion.Instance
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.UUID

class NewProfile(val uuid: UUID) {
    companion object {
        val profileHashMutableMap = mutableMapOf<UUID, NewProfile>()
    }

    val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(uuid)

    var displayName: String = offlinePlayer.name!!
    var height: Double = 1.0


    init {
        if (profileHashMutableMap.containsKey(uuid)) {
            displayName = profileHashMutableMap[uuid]!!.displayName
            height = profileHashMutableMap[uuid]!!.height
        } else {
            val conn = Instance.getConnection()
            val statement = conn.createStatement()
            val result = statement.executeQuery("select * from profile where uuid = '${uuid}';")
            while(result.next()) {
                println("id = " + result.getInt("id"));
                println("name = " + result.getString("name"));
            }

        }
    }
}