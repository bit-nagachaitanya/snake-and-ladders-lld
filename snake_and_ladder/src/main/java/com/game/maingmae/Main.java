package com.game.maingmae;
import java.util.*;
public class Main {
	
	
	public static class Player{
		int id;
		String name;
		private int position;
		public Player(int id,String name,int position) {
			this.id=id;
			this.name=name;	
			this.position=position;
		}
		
		public String getName(){
			return this.name;
		}
		
		public int getPosition() {
			return this.position;
		}
		private void setposition(int position) {
			 this.position=position;
		}
		
	}

	
	public static class Board{
		private  final int size;
		private  final Map<Integer,Integer> Ladders;
		private  final Map<Integer,Integer> Snakes;
		
		private Board(int size) {
			this.size=size;
			this.Ladders=new HashMap<>();
			this.Snakes=new HashMap<>();
			Initialize();			
		}			
		private void Initialize() {
			
			System.out.println("intializing Board");
			Ladders.put(6, 20);
			Ladders.put(15,29);
			Ladders.put(30,45);
			Ladders.put(55,75);
			Ladders.put(81,92);
		
				
			Snakes.put(17,3);
			Snakes.put(32,20 );
			Snakes.put(95, 65);
			Snakes.put(97, 85);
			
		}
		
		public int checkSnakeBited(int new_position) {
			if (!Snakes.containsKey(new_position)){
					
			return new_position;
			}
			System.out.println(" Got bitten by Snake ");
			return Snakes.get(new_position);
		}
		public int ladderClimbed(int new_position) {
			
			if (!Ladders.containsKey(new_position)) {
				
			return new_position;
			}
			System.out.println(" Got ladder ");
			return Ladders.get(new_position);
		}
		
	}
	
	public static class Game {
		private Dice dice;
		List<Player> players;
		Board board;
		
		private Game(List<Player> players) {
		board=new Board(100);
		dice=new Dice();
		this.players=players;
		}
		
		private void startGame() {
			
			Boolean won=false;
			while (!won) {
				
				for (Player p: players) {
					System.out.println("Player "+p.getName()+" Turn");
					int dice_number=dice.RollDice();
					System.out.println("Your dice value is :"+dice_number);
					int new_position=p.getPosition()+dice_number;
					
					if (new_position ==100) {
						
						System.out.println(p.getName()+" is the winner");
						won=true;
						break;
					}
					
					if (new_position>100){
						continue;
						
					}
					System.out.println("newposition"+new_position);
					 new_position=board.checkSnakeBited(new_position);
					 new_position=board.ladderClimbed(new_position);
					
						
					p.setposition(new_position);
					
					
				} 
			}
			
		}
		
	
	
	
	public static class Dice{
		private static Random rand=new Random();
		public int RollDice() {
			return rand.nextInt(6)+1;
		}
		
	} 
	public static void main(String[] args) {
		
		List <Player> players=new ArrayList<>();
		players.add(new Player(1,"Naga",0));
		players.add(new Player(2,"Akash",0));
		players.add(new Player(3,"Rahul",0));
		
		Game game=new Game(players);
		game.startGame();
		

	}

	}}
