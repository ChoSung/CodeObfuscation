import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JCheckBox;


public class Main {
	public static void main(String args[]){
		test_Frame tf = new test_Frame();
		
	}
}

class test_Frame extends JFrame implements ActionListener{
	private JFileChooser jfc = new JFileChooser();
	JPanel jpList = new JPanel(); // �г� �ʱ�ȭ
	private JButton jbt_open = new JButton("�������");
	private JButton jbt_save = new JButton("������");
	private JButton jbt_start = new JButton("����ȭ ����");
	private JTextField jbt_open_textPeriod = new JTextField(18);
	private JTextField jbt_save_textPeriod = new JTextField(18);
	static TextArea textArea = new TextArea("", 15, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
	JCheckBox[] checkbox= new JCheckBox[6];
	JButton nullButton = new JButton();
	JButton nullButton2 = new JButton();
	BasicSetting setting = new BasicSetting();


	public test_Frame(){
		super("Code Obfuscation");
		this.init();
		this.pro_start();
		this.setSize(330,510);
		this.setVisible(true);
		this.setResizable(false); //������ ���� ���ϰ�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x ��ư�� �������� ����
	}
	
	public void init(){
		checkbox[0] = new JCheckBox("��Ű�� �̸� ����");
		checkbox[1] = new JCheckBox("�޼ҵ� �̸� ����");
		checkbox[2]= new JCheckBox("����� ���� ����");
		checkbox[3] = new JCheckBox("Java ���� ��������");
		checkbox[4] = new JCheckBox("���ڿ� ��ȣȭ");
		checkbox[5] = new JCheckBox("Ŭ���� �̸� ����");

		getContentPane().setLayout(new FlowLayout());

		add(jbt_open_textPeriod);
		add(jbt_open);
		add(jbt_save_textPeriod);
		add(jbt_save);

		for(int i=0; i<checkbox.length; i++){
			checkbox[i].setSelected(true);
			jpList.add(checkbox[i]);
		}

		jpList.setLayout(new GridLayout(3,2)); // GridLayout

		add(jpList);

		jbt_start.setPreferredSize(new Dimension(300, 50));

		add(jbt_start); //����ȭ ���� ��ư �߰�
		textArea.setBounds(10,200,480,140);
		add(textArea);

	}
	public void pro_start(){
		jbt_open.addActionListener(this);
		jfc.setFileFilter(new FileNameExtensionFilter("class", "class"));
		// ���� ����
		jfc.setMultiSelectionEnabled(false);//���� ���� �Ұ�
		jbt_save.addActionListener(this);
		jbt_start.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == jbt_open){
			JFileChooser chooser = new JFileChooser();// ��ü ����
			chooser.setCurrentDirectory(new File("C:\\Users\\cho93\\workspace")); // ��ó����θ� C�� ��
			chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); // ���丮�� ���ð���
			int re = chooser.showSaveDialog(null);
			if (re == JFileChooser.APPROVE_OPTION) { //���丮�� ����������
				jbt_open_textPeriod.setText(chooser.getSelectedFile().toString());
			}else{
				JOptionPane.showMessageDialog(null, "��θ� ���������ʾҽ��ϴ�.",
						"���", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}else if(arg0.getSource() == jbt_save){
			JFileChooser chooser = new JFileChooser();// ��ü ����
			chooser.setCurrentDirectory(new File("C:\\Users\\cho93\\workspace")); // ��ó����θ� C�� ��
			chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); // ���丮�� ���ð���
			int re = chooser.showSaveDialog(null);
			if (re == JFileChooser.APPROVE_OPTION) { //���丮�� ����������
				jbt_save_textPeriod.setText(chooser.getSelectedFile().toString());
			}else{
				JOptionPane.showMessageDialog(null, "��θ� ���������ʾҽ��ϴ�.",
						"���", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}else if(arg0.getSource() == jbt_start){
			if(!jbt_open_textPeriod.getText().equals("") && !jbt_save_textPeriod.getText().equals("")){
				//��ȯ ���� ��ü ����
				String forChangeFoler = jbt_save_textPeriod.getText();
				File selectFile = new File(jbt_open_textPeriod.getText());
				fileCopy(selectFile,new File(forChangeFoler));
				textArea.append("FileCopy Success\n\n");
				forChangeFoler += "\\"+selectFile.getName();
				File forChangeFoler_File= new File(forChangeFoler);
				
				setting.setSelectFile(forChangeFoler_File);
				
				Obfuscation_switch mainSwitch = new Obfuscation_switch();
				
				
				if(checkbox[2].isSelected()){ //����� ���� ����
					mainSwitch.changeDebug();
					textArea.append("����� ���� ���� �Ϸ�\n");
				}
				
				if(checkbox[3].isSelected()){
					mainSwitch.changeJavaFile();
					textArea.append("Java ���� �������� �Ϸ�\n");
				}
				
				
				if(checkbox[5].isSelected()){
					mainSwitch.changeClass();
					textArea.append("Ŭ���� �̸� ����ȭ �Ϸ�\n");
				}
				
				if(checkbox[1].isSelected()){ 
					mainSwitch.changeMethod();
					textArea.append("�޼ҵ� ����ȭ �Ϸ�\n");
				}
				
				if(checkbox[0].isSelected()){
					mainSwitch.changePackage();
					textArea.append("��Ű�� �̸� ����ȭ �Ϸ�\n");
				}
				
				if(checkbox[4].isSelected()){
					mainSwitch.changeString();
					textArea.append("���ڿ� ��ȣȭ �Ϸ�\n");
				}
				
				mainSwitch.changeCheck();
				textArea.append("\n----Obfuscation Success----\n");
				
			}
			else{
				JOptionPane.showMessageDialog(null, "��θ� ���������ʾҽ��ϴ�.",
						"���", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	}
	
	//���� ��ü ����
	public static void fileCopy(File selectFile, File copyFile){
		File temp = new File(copyFile.getAbsolutePath() +"\\"+selectFile.getName());
		temp.mkdirs();
		fileCopy_reflection(selectFile, temp);
	}

	//���� ��ü ����
	public static void fileCopy_reflection(File selectFile, File copyFile) { //������ ���丮, ����� ���丮
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			File temp = new File(copyFile.getAbsolutePath() +"\\"+file.getName()); 
			//temp - ���������� ���丮 ������ ������ ����,���ϵ��� ���������� ������ �����մϴ�. 

			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				temp.mkdirs();          //����� ��ġ�� �Ȱ��� ������ �����ϰ�,
				fileCopy_reflection(file, temp);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸� �����۾��� �����մϴ�.
				FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(temp);
					byte[] b = new byte[4096];   //4kbyte������ ���縦 �����մϴ�.
					int cnt = 0;

					while ((cnt = fis.read(b)) != -1) {  //������ ���Ͽ��� �����͸� �а�,
						fos.write(b, 0, cnt);               //����� ��ġ�� ���Ͽ� �����͸� ���ϴ�.
					}
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						fis.close();
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
