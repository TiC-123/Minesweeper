import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Source.*;

public class Game implements Frame, ActionListener {
    private JLabel label;
    private JButton buttonStart[], buttonLevel[], buttonGame[], buttonHint;
    private int level, limit, bound, count, nHint, n = 0, bPosition[], map[], posH[];
    private String temp[];
    private Hint hint;
    private Map mMap;
    private ConfigureMap cMap;
    private GenerateBomb gBomb = new GenerateBomb();
    private GameStatus endGame = new GameStatus();
    private JFrame frame = new JFrame();

    //Start a game
    public void newGame() {
        setFrame();
        setFrameStart();
    }

    private void setFrameStart() {
        buttonStart = new JButton[2];
        buttonStart[0] = new JButton( "START GAME" );
        buttonStart[0].setBounds( 100, 230, 150, 30 );
        buttonStart[1] = new JButton( "QUIT" );
        buttonStart[1].setBounds( 300, 230, 150, 30 );

        buttonStart[0].addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                frame.getContentPane().removeAll();
                frame.repaint();

                setFrameDifficulty();
            }
        } );
        buttonStart[1].addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.exit(1);
            }
        } );
        
        label = new JLabel( "MINESWEEPER" );
        label.setBounds( 160, 100, 700, 30 );
        label.setFont( new Font( "Verdana", Font.BOLD, 25 ) );

        frame.setContentPane( new JLabel( new ImageIcon( "D:\\Documents\\Coding\\Java\\Minesweeper - GUI\\image\\minesweeper.png" ) ) );
        frame.setSize(529,659);
        frame.setSize(530,660);
        frame.add( label );
        frame.add( buttonStart[0] );
        frame.add( buttonStart[1] );
    }  

    //Make a difficulty setting screen
    private void setFrameDifficulty() {
        buttonLevel = new JButton[3];
        
        for( int i = 0; i < 3; i++ ) {
            if( i == 0 ) 
                buttonLevel[i] = new JButton( "EASY" );
            else if( i == 1 )
                buttonLevel[i] = new JButton( "MEDIUM" );
            else
                buttonLevel[i] = new JButton( "HARD" );

            buttonLevel[i].setBounds( 50 + ( i * 140 ), 230, 100, 30 );
            buttonLevel[i].addActionListener( this );
            frame.add( buttonLevel[i] );
        }
        
        label = new JLabel( "SET LEVEL" );
        label.setBounds( 160, 100, 700, 30 );
        label.setFont( new Font( "Verdana", Font.BOLD, 25 ) );

        frame.add( label );
    }

    //Determine important data used in game
    private void setData() {
        if( level == 1 ) {
            limit = 9;
            bound = 3;
        } else if( level == 2 ) {
            limit = 25;
            bound = 5;
        } else if( level == 3 ) {
            limit = 49;
            bound = 7;
        }

        count = limit - bound;

        gBomb.setLimit( limit, bound );
        bPosition = gBomb.getRandomNumberList();
        gBomb.showBomb();
        
        mMap = new ConfigureMap( bound, bPosition );
        cMap = ( ConfigureMap ) mMap;
        cMap.setMap();
        map = cMap.getMap();

        hint = new Hint( bound, bPosition );

        setFrameGame();
    }

    //Make main game screen
    private void setFrameGame() {
        buttonGame = new JButton[limit];
        label = new JLabel( count + " space left" );
        
        label.setBounds( 200, 30, 700, 30 );
        label.setFont( new Font( "Verdana", Font.BOLD, 25 ) );
        frame.add( label );

        buttonHint = new JButton( "Hint : OFF" );
        buttonHint.setBounds( 50, 30, 100, 30 );
        frame.add( buttonHint );
        buttonHint.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if( buttonHint.getText().equals( "Hint : OFF" ) )
                    buttonHint.setText( "Hint : ON" );
                else
                    buttonHint.setText( "Hint : OFF" );
            }
        } );

        for( int i = 0, y = 100; i < bound; i++, y += 525 / bound )
            for( int j = 0, x = 0; j < bound; j++, x += 525 / bound ) {
                buttonGame[n] = new JButton( "O" );
                buttonGame[n].setBounds( x, y, 525 / bound, 525 / bound );
                frame.add( buttonGame[n] );
                buttonGame[n].addActionListener( this );

                n++;
            }
                
    }
    
    //Determne the screen
    @Override
    public void setFrame() {
        frame.setTitle( "Minesweeper" );
        frame.setLocationRelativeTo( null );
        frame.setBackground( Color.WHITE );
        frame.setSize( 530, 660 );
        frame.setVisible( true );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout( null );
    }

    //Action performed when a certain button is pressed
    @Override
    public void actionPerformed( ActionEvent e ) {
        //Determine game difficulty from button 
        for( int i = 0; i < 3; i++ )
            if( e.getSource() == buttonLevel[i] ) {
                frame.getContentPane().removeAll();
                frame.repaint();

                level = i + 1;

                setData();
            }

        //Determine game transition when button pressed
        for( int i = 0; i < n; i++ ) {
            if( e.getSource() == buttonGame[i] ) {
                if( buttonHint.getText().equals( "Hint : ON" ) ) {
                    if( temp != null ) {
                        for( int j = 0; j < posH.length; j++ ) {
                            buttonGame[posH[j]-1].setBackground( null );
                            buttonGame[posH[j]-1].setText( temp[j] );
                        }

                        temp = null;
                    }
                    
                    hint.setPosition( i + 1 );
                    nHint = hint.getHint();
                    posH = hint.getPosHint();

                    temp = new String[posH.length];

                    for( int j = 0; j < posH.length; j++ ) {
                        buttonGame[posH[j]-1].setBackground( Color.RED );
                        temp[j] = buttonGame[posH[j]-1].getText();
                        buttonGame[posH[j]-1].setText( null );
                    }

                    buttonGame[i].setText( "" + nHint );
                    buttonHint.setText( "Hint : OFF" );
                } else 
                    if( map[i] == 1 ) {
                        endGame.setStatus( "GAME OVER", frame );
                    } else if( map[i] != 2 ) {
                        count--;
                    
                        if( temp != null ) {
                            for( int j = 0; j < posH.length; j++ ) {
                                buttonGame[posH[j]-1].setBackground( null );
                                buttonGame[posH[j]-1].setText( temp[j] );
                            }
                        }

                        if( count > 0 ) {
                            map[i] = 2;
                            buttonGame[i].setText( "X" );

                            if( temp != null )
                                for( int j = 0; j < posH.length; j++ ) 
                                    temp[j] = buttonGame[posH[j]-1].getText();

                            label.setText( count + " space left" );
                        } else {
                            endGame.setStatus( "YOU WIN", frame );
                        }
                    } 
            }
        }
    }
}