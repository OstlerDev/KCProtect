package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author OstlerDev
 */
public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		// TODO Check to see if block is correct type to create a region around it... ex. player places sponge (configurable in the config) it creates a 25x25 region around him.
		@SuppressWarnings("unused")
		Player player = e.getPlayer();
	}
}