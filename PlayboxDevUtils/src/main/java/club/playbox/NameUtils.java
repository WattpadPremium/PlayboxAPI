package club.playbox;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class NameUtils {

    public static NameUtils nameUtils = new NameUtils();

    public static NameUtils getNameUtils(){
        return nameUtils;
    }

    public void sendTeamCreatePacket(Player player){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
    }


    public void sendTeamDeletePacket(){

    }




}
