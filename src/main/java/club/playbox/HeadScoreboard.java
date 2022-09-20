package club.playbox;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.util.Collection;

@SuppressWarnings("all")
public class HeadScoreboard implements Listener {


    public static void updateScoreboard(Player player){
        sendPacket(player, scoreboardPacket(player, SB.UPDATE));
    }

    public static PacketPlayOutScoreboardTeam scoreboardPacket(Player player, SB sb){
        String playerName = player.getName();
        return apply(player, playerName, "", sb);
    }

    public static void sendPacket(Player player, PacketPlayOutScoreboardTeam packet){
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


    /// SCOREBOARD PACKET

    private static PacketPlayOutScoreboardTeam packet;

    private static PacketPlayOutScoreboardTeam apply(Player player, String prefix, String suffix, SB sb){
        packet = new PacketPlayOutScoreboardTeam();
        setField("a", player.getName()); setField("b", player.getName()); setField("c", ChatColor.BLACK +"Panda" + ChatColor.WHITE); setField("d", suffix); setField("f", 1);
        switch(sb){
            case CREATE: addPlayer(player); setField("f", 0); break;
            case DESTROY: setField("f", 1); break;
            case UPDATE: setField("f", 2); break; }
        return packet;
    }


    private static void setField(String field, Object value) {
        try {
            Field f = packet.getClass().getDeclaredField(field);
            f.setAccessible(true); f.set(packet, value); f.setAccessible(false);
        } catch (Exception ex) {ex.printStackTrace();}
    }
    private static void addPlayer(Player pl){
        try {add(pl);}catch(Exception ex){ex.printStackTrace();}
    }

    private static void add(Player pl) throws NoSuchFieldException, IllegalAccessException{
        Field f = packet.getClass().getDeclaredField("g"); f.setAccessible(true); ((Collection) f.get(packet)).add(pl.getName());
    }
    public enum SB { CREATE, DESTROY, UPDATE }

}