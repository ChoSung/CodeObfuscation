import java.io.File;

public class Obfuscation_LastCheck {
	File selectFile;

	public Obfuscation_LastCheck(){
		selectFile = BasicSetting.getSelectFile();
	}

	//���� ���� ���� �� ����
	public void lastCheck(){
		compileAll(selectFile);
		move_Class_to_bin(selectFile, false);
	}

	//������
	private void compileAll(File selectFile){
		JavaCompiler javaCom = new JavaCompiler();

		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.

		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				compileAll(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸�
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					javaCom.compile(file);
					//test_Frame.textArea.append(file.getName()+" compile Success\n");
				}
			}
		}
	}

	//src������ class�� bin���Ϸ� �̵�
	private void move_Class_to_bin(File selectFile, boolean flag){
		//src ������ class ���� ��� bin ���Ϸ� �̵�
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				if(flag){
					move_Class_to_bin(file, true);  
				}
				else if(file.getName().equals("src")){
					move_Class_to_bin(file, true);      //������ ���θ� �ٽ� ���캾�ϴ�.
				}
				else{
					move_Class_to_bin(file, false);
				}
			}else {  
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("class")){
					if(flag){
						String temp = "";
						File tempFile = new File(file.getAbsolutePath());
						while(!tempFile.getParentFile().getName().equals("src")){
							temp += file.getParentFile().getName()+"\\";
							tempFile = tempFile.getParentFile();
						}
						File newRooteFile = new File(tempFile.getParentFile().getParent()+"\\bin\\"+temp+file.getName());
						file.renameTo(newRooteFile);
					}
				}
			}
		}
	}
}
