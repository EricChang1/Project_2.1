package DataStructure;

import java.util.*;
import java.util.stream.Collectors;

public class Board{

    private int width;
    private int height;
    public HashMap<Vector3, Hex> map;

    public Board(int gridWidth, int gridHeight){

        map = new HashMap<>();
        width = gridWidth;
        height = gridHeight;

        int y = 0;
        for (int z = 0; z < gridHeight; z++) {
            for (int x = 0; x < gridWidth ; x++) {

                Vector3 coord = new Vector3(x, y, -x-y);
                Hex h = new Hex();
                EnumSet<Hex.Border> borders = EnumSet.noneOf(Hex.Border.class);

                if(x == 0)
                    borders.add(Hex.Border.WEST);
                else if(x == width-1)
                    borders.add(Hex.Border.EAST);
                if(z == 0)
                    borders.add(Hex.Border.NORTH);
                else if(z == height-1)
                    borders.add(Hex.Border.SOUTH);

                h.setBorder(borders);

                map.put(coord, h);
            }
            y++;
        }
    }

    public List<Vector3> getNeighbours(Vector3 coord) {

        if (!map.containsKey(coord))
            return null;

        ArrayList<Vector3> directions = new ArrayList<>();

        directions.add(new Vector3(coord).add(-1, 1, 0)); // down left
        directions.add(new Vector3(coord).add(0, -1, 1)); // up left

        directions.add(new Vector3(coord).add(0, 1, -1)); // down right
        directions.add(new Vector3(coord).add(1, -1, 0)); // up right

        directions.add(new Vector3(coord).add(1, 0, -1)); // right
        directions.add(new Vector3(coord).add(-1, 0, 1)); // left

        return directions.stream()
                .filter(dir -> map.containsKey(dir))
                .collect(Collectors.toList());
    }
}
