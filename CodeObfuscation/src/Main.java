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
	JPanel jpList = new JPanel(); // 패널 초기화
	private JButton jbt_open = new JButton("대상파일");
	private JButton jbt_save = new JButton("저장경로");
	private JButton jbt_start = new JButton("난독화 실행");
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
		this.setResizable(false); //사이즈 변경 못하게
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼을 눌렀을때 종료
	}
	
	public void init(){
		checkbox[0] = new JCheckBox("패키지 이름 변경");
		checkbox[1] = new JCheckBox("메소드 이름 변경");
		checkbox[2]= new JCheckBox("디버그 정보 삭제");
		checkbox[3] = new JCheckBox("Java 파일 조각내기");
		checkbox[4] = new JCheckBox("문자열 암호화");
		checkbox[5] = new JCheckBox("클래스 이름 변경");

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

		add(jbt_start); //난독화 실행 버튼 추가
		textArea.setBounds(10,200,480,140);
		add(textArea);

	}
	public void pro_start(){
		jbt_open.addActionListener(this);
		jfc.setFileFilter(new FileNameExtensionFilter("class", "class"));
		// 파일 필터
		jfc.setMultiSelectionEnabled(false);//다중 선택 불가
		jbt_save.addActionListener(this);
		jbt_start.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == jbt_open){
			JFileChooser chooser = new JFileChooser();// 객체 생성
			chooser.setCurrentDirectory(new File("C:\\Users\\cho93\\workspace")); // 맨처음경로를 C로 함
			chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); // 디렉토리만 선택가능
			int re = chooser.showSaveDialog(null);
			if (re == JFileChooser.APPROVE_OPTION) { //디렉토리를 선택했으면
				jbt_open_textPeriod.setText(chooser.getSelectedFile().toString());
			}else{
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.",
						"경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}else if(arg0.getSource() == jbt_save){
			JFileChooser chooser = new JFileChooser();// 객체 생성
			chooser.setCurrentDirectory(new File("C:\\Users\\cho93\\workspace")); // 맨처음경로를 C로 함
			chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); // 디렉토리만 선택가능
			int re = chooser.showSaveDialog(null);
			if (re == JFileChooser.APPROVE_OPTION) { //디렉토리를 선택했으면
				jbt_save_textPeriod.setText(chooser.getSelectedFile().toString());
			}else{
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.",
						"경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}else if(arg0.getSource() == jbt_start){
			if(!jbt_open_textPeriod.getText().equals("") && !jbt_save_textPeriod.getText().equals("")){
				//변환 파일 전체 복사
				String forChangeFoler = jbt_save_textPeriod.getText();
				File selectFile = new File(jbt_open_textPeriod.getText());
				fileCopy(selectFile,new File(forChangeFoler));
				textArea.append("FileCopy Success\n\n");
				forChangeFoler += "\\"+selectFile.getName();
				File forChangeFoler_File= new File(forChangeFoler);
				
				setting.setSelectFile(forChangeFoler_File);
				
				Obfuscation_switch mainSwitch = new Obfuscation_switch();
				
				
				if(checkbox[2].isSelected()){ //디버그 정보 삭제
					mainSwitch.changeDebug();
					textArea.append("디버그 정보 삭제 완료\n");
				}
				
				if(checkbox[3].isSelected()){
					mainSwitch.changeJavaFile();
					textArea.append("Java 파일 조각내기 완료\n");
				}
				
				
				if(checkbox[5].isSelected()){
					mainSwitch.changeClass();
					textArea.append("클래스 이름 난독화 완료\n");
				}
				
				if(checkbox[1].isSelected()){ 
					mainSwitch.changeMethod();
					textArea.append("메소드 난독화 완료\n");
				}
				
				if(checkbox[0].isSelected()){
					mainSwitch.changePackage();
					textArea.append("패키지 이름 난독화 완료\n");
				}
				
				if(checkbox[4].isSelected()){
					mainSwitch.changeString();
					textArea.append("문자열 암호화 완료\n");
				}
				
				mainSwitch.changeCheck();
				textArea.append("\n----Obfuscation Success----\n");
				
			}
			else{
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.",
						"경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	}
	
	//파일 전체 복사
	public static void fileCopy(File selectFile, File copyFile){
		File temp = new File(copyFile.getAbsolutePath() +"\\"+selectFile.getName());
		temp.mkdirs();
		fileCopy_reflection(selectFile, temp);
	}

	//파일 전체 복사
	public static void fileCopy_reflection(File selectFile, File copyFile) { //복사할 디렉토리, 복사될 디렉토리
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			File temp = new File(copyFile.getAbsolutePath() +"\\"+file.getName()); 
			//temp - 본격적으로 디렉토리 내에서 복사할 폴더,파일들을 순차적으로 선택해 진행합니다. 

			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				temp.mkdirs();          //복사될 위치에 똑같이 폴더를 생성하고,
				fileCopy_reflection(file, temp);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면 복사작업을 진행합니다.
				FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(temp);
					byte[] b = new byte[4096];   //4kbyte단위로 복사를 진행합니다.
					int cnt = 0;

					while ((cnt = fis.read(b)) != -1) {  //복사할 파일에서 데이터를 읽고,
						fos.write(b, 0, cnt);               //복사될 위치의 파일에 데이터를 씁니다.
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
