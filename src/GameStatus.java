import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Font;
import Source.Frame;

public class GameStatus implements Frame {
    private JLabel label;
    private JFrame frame;
    
    @Override
    public void setFrame() {
        frame.repaint();
        frame.setSize(529,659);
        frame.setSize(530,660);
        frame.add( label );
    }

    public void setStatus( String status, JFrame frame ) {
        this.frame = frame;
        label = new JLabel( status );
        label.setBounds( 160, 100, 700, 30 );
        label.setFont( new Font( "Verdana", Font.BOLD, 25 ) );
        
        if( status.equals( "GAME OVER" ) )
            frame.setContentPane( new JLabel( new ImageIcon( "D:\\Documents\\Coding\\Java\\Minesweeper - GUI\\image\\lose.png" ) ) );
        else
            frame.setContentPane( new JLabel( new ImageIcon( "D:\\Documents\\Coding\\Java\\Minesweeper - GUI\\image\\win.png" ) ) );

        setFrame();
    }
}