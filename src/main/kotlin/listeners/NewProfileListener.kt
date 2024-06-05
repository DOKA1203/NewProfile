package kr.doka.lab.newprofile

import kr.doka.lab.newprofile.NewProfilePlugin.Companion.Instance
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.UUID
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.attribute.Attribute
import com.destroystokyo.paper.profile.PlayerProfile
class NewProfileListener: Listener {
    init {
       Bukkit.getPluginManager().registerEvents(this, Instance)
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val profile = NewProfile(e.player.uniqueId)
        val scale = e.player.getAttribute(Attribute.GENERIC_SCALE)
        scale?.baseValue = profile.height
        
        val playerProfile: PlayerProfile = e.player.playerProfile
        playerProfile.name = ""
    }
}