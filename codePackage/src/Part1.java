import javax.swing.*;
import java.awt.*;

public class Part1 extends JFrame {
    Part1(){
        super("객실설정, 고객정보"); //타이틀
        JPanel jPanel = new JPanel();

        jPanel.setBackground(Color.BLUE);

        setSize(300, 200);

        add(jPanel);

        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}