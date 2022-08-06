import Source.Map;

public class ConfigureMap extends Map {
    private int[] bomb, map;
    
    @Override 
    public void makeMap( int dLength ) {
        this.dLength = dLength;
        map = new int[dLength * dLength];
    }

    public ConfigureMap( int dLength, int[] bomb ) {
        makeMap( dLength );
        this.bomb = bomb;
    }
    
    public void setMap() {
        for( int i = 0; i < dLength * dLength; i++ ) {
            for( int j = 0; j < dLength && map[i] != 1; j++ )
                if( i + 1 == bomb[j] )
                    map[i] = 1;
                else
                    map[i] = 0;
        }
    }
    
    public int[] getMap() {
        return map;
    }
}