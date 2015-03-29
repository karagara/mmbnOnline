package Game;

enum ChipType {BOMB, SWORD, CANNON}

public class Chip{
	//the different type of Chiplist
	ChipType chip;
	//char represent the chip
	char l;
	String spritePath;
	private Player player;
	private Arena arena;
	
	public Chip(ChipType chip, char l, Player player, Arena arena){
		this.chip = chip;
		this.l = l;
		this.player = player;
		this.arena = arena;
	}
	public void createAction(){
		switch(chip){
			case BOMB:
				System.out.println("BombChip created");
				BombChipAction bombChip = new BombChipAction(player, player.position, arena);
				spritePath = bombChip.getSpritePath();
				break;
			case SWORD:
				System.out.println("Sword Chip created");
				SwordChipAction swordChip = new SwordChipAction(player, player.position, arena);
				spritePath = swordChip.getSpritePath();
				break;
			case CANNON:
				System.out.println("CANNON chip created");
				CannonChipAction cannonChip = new CannonChipAction(player, player.position, arena);
				spritePath = cannonChip.getSpritePath();
				break;
			default:
				System.out.println("No Chip action created");
				break;
		}
	}
}