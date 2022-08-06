import Source.Map;

public class Hint extends Map {
    private int check, bomb[], posH[], nBomb, hPos, vPos, v, h, pos[][], map[][];

    @Override
    public void makeMap( int dLength ) {
        map = new int[dLength][dLength];

        for( int i = 0, n = 1; i < dLength; i++ )
            for( int j = 0; j < dLength; j++ ) {
                map[i][j] = n;
                n++;
            }
    }

    public Hint( int dLength, int[] bomb ) {
        this.bomb = bomb;
        this.dLength = dLength;

        makeMap( dLength );
    }

    public void setPosition ( int position ) {
        pos = new int[dLength][dLength];

        for( int i = 0, n = 1; i < dLength; i++ )
            for( int j = 0; j < dLength; j++ ) {
                for( int k = 0; k < bomb.length && pos[i][j] != 1; k++ ) {
                    if( n == bomb[k] )
                        pos[i][j] = 1;
                    else
                        pos[i][j] = 0;

                    if( n == position ) {
                        vPos = i;
                        hPos = j;
                    }
                }
    
                n++;
            }
    }

    public int getHint() {
        nBomb = 0;
        check = 0;
        
        for( int i = -1; i < 2; i++ ) {
            v = vPos + i;

            for( int j = -1; j < 2 && v >= 0 && v < dLength; j++ ) {
                h = hPos + j;

                if(  h >= 0 && h < dLength ) {
                    if( pos[v][h] == 1 )
                        nBomb += 1;

                    check += 1;
                }
            }
        }

        return nBomb;
    }

    public int[] getPosHint() {        
        posH = new int[check];

        for( int i = -1, neff = 0; i < 2 && neff < check; i++ ) {
            v = vPos + i;

            for( int j = -1; j < 2 && v >= 0 && v < dLength; j++ ) {
                h = hPos + j;

                if(  h >= 0 && h < dLength ) {
                    posH[neff] = map[v][h];
                    neff++;
                }
            }
        }

        return posH;
    }
}