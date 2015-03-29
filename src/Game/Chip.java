package Game;

public enum ChipType{ BOMB, SWORD, CANNON}

public class Chip{
	//the different type of Chiplist
	ChipType chip;
	//char represent the chip
	char l;
	String spritePath;
	private Player player;
	private Tile tile;
	private Arena arena;
	
	public Chip(ChipType chip, char l, Player player, Tile tile, Arena arena){
		this.chip = chip;
		this.l = l;
		this.player = player;
		this.tile = tile;
		this.arena = arena;
	}
	
	public void createAction(){
		switch(chip){
			case BOMB:
				System.out.println("BombChip created");
				BombChipAction bombChip = new BombChipAction(player, tile, arena);
				spritePath = bombChip.getSpritePath();
				break;
			case SWORD:
				System.out.println("Sword Chip created");
				SwordChipAction swordChip = new SwordChipAction(player, tile, arena);
				spritePath = swordChip.getSpritePath();
				break;
			case CANNON:
				System.out.println("CANNON chip created");
				CannonChipAction cannonChip = new CannonChipAction(player, tile, arena);
				spritePath = cannonChip.getSpritePath();
				break;
			default:
				System.out.println("No Chip action created");
				break;
		}
	}
}