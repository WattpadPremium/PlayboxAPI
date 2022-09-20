package club.playbox.events;

import club.playbox.UtilsPlugin;
import club.playbox.HeadScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;



public class ConnectionEvent implements Listener {

    @EventHandler
    public void JoinEvent(PlayerJoinEvent event){

        HeadScoreboard.sendPacket(event.getPlayer(), HeadScoreboard.scoreboardPacket(event.getPlayer(), HeadScoreboard.SB.CREATE));

    }

    @EventHandler
    public void QuitEvent(PlayerQuitEvent event){
        HeadScoreboard.sendPacket(event.getPlayer(), HeadScoreboard.scoreboardPacket(event.getPlayer(), HeadScoreboard.SB.DESTROY));
    }



}
