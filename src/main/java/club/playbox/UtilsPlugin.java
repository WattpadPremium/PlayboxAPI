package club.playbox;

import club.playbox.events.ConnectionEvent;
import fr.dwightstudio.dsmapi.commands.TestExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilsPlugin extends JavaPlugin {

    static private UtilsPlugin INSTANCE;

    public static UtilsPlugin getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable(){
        INSTANCE = this;
        getCommand("dsm-test").setExecutor(new TestExecutor());
        Bukkit.getPluginManager().registerEvents(new ConnectionEvent(),this);
    }


    @Override
    public void onDisable(){

    }


}
