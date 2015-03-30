package Game;

import java.util.ArrayList;

public class playerChips{
    static class JSONMenuChip{
        char letter;
        String sprite;
        ChipType type;
        boolean isSelected;
    }

    static class JSONSelectedChip{
        ChipType type;
    }

    ArrayList<JSONMenuChip> menuChips;
    ArrayList<JSONSelectedChip> selectedChips;
    int cursorPos;
}
