package elevenPlus.entity;

import java.util.ArrayList;

public class PlayerList {
	ArrayList<Player> playerList;
	
	public void addPlayer(Integer playerid, String name){
		Player player = new Player();
		if (playerList == null){
			playerList = new ArrayList<Player>();
		}
		player.setId(playerid);
		player.setName(name);
		player.setPosition(0);
		playerList.add(player);
	}
	
	public Player getPlayer(Integer playerid){
		Player player = new Player();
		for (Player p : playerList){
			if (p.getId() == playerid){
				player = p;
			}
		}
		return player;
	}
	
	public ArrayList<Player> getAllPlayers(){
		return playerList;
	}
	
	public Integer numPlayers(){
		return playerList.size();
	}
}
