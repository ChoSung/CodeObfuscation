import java.io.File;

public class Obfuscation_LastCheck {
	File selectFile;

	public Obfuscation_LastCheck(){
		selectFile = BasicSetting.getSelectFile();
	}

	//최종 파일 점검 및 변경
	public void lastCheck(){
		compileAll(selectFile);
		move_Class_to_bin(selectFile, false);
	}

	//컴파일
	private void compileAll(File selectFile){
		JavaCompiler javaCom = new JavaCompiler();

		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.

		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				compileAll(file);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					javaCom.compile(file);
					//test_Frame.textArea.append(file.getName()+" compile Success\n");
				}
			}
		}
	}

	//src파일의 class를 bin파일로 이동
	private void move_Class_to_bin(File selectFile, boolean flag){
		//src 폴더에 class 있을 경우 bin 파일로 이동
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				if(flag){
					move_Class_to_bin(file, true);  
				}
				else if(file.getName().equals("src")){
					move_Class_to_bin(file, true);      //폴더의 내부를 다시 살펴봅니다.
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
