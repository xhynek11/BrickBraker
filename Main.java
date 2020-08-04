import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame okno = new JFrame();
		Hra hra = new Hra();
		okno.setBounds(10, 10, 700, 600);
		okno.setTitle("BrickBraker");
		okno.setResizable(false);
		okno.setVisible(true);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.add(hra);
		

	}

}
