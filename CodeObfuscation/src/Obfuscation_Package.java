import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Obfuscation_Package {
	File selectFile;
	String packageList[];
	int count_of_package;

	public Obfuscation_Package(){
		selectFile = BasicSetting.getSelectFile();
		packageList = new String[300];
		count_of_package= 0;
	}

	public void obfuscationPackage(){
		getPackageName(selectFile, false);
		changePackageName(selectFile);
		changePackageCode(selectFile);
	}

	public void getPackageName(File selectFile , boolean flag){
		//src 폴더에 class 있을 경우 bin 파일로 이동
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				if(flag){
					String fileName = file.getName();
					packageList[count_of_package] = fileName;
					packageList[++count_of_package] = BasicSetting.getNameEn();
					count_of_package++;
					getPackageName(file, true); 
				}
				else if(file.getName().equals("src")){
					getPackageName(file, true);      //폴더의 내부를 다시 살펴봅니다.
				}
				else{
					getPackageName(file, false);
				}
			}
		}
	}

	public void changePackageName(File selectFile){
		//src 폴더에 class 있을 경우 bin 파일로 이동
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면

				changePackageName(file);  
				int countNum =-1;
				for(int i=0; i<count_of_package; i++){
					if(packageList[i].equals(file.getName())){
						countNum = i;
					}
				}
				if(countNum != -1){
					File newRooteFile = new File(file.getParentFile()+"\\"+packageList[countNum+1]);
					file.renameTo(newRooteFile);
				}

			}
		}
	}
	
	public void changePackageCode(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.

		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				changePackageCode(file);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					try {
						// BufferedReader 변수에 file을 넣는다
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// 파일을 한줄씩 읽어 넣기 위한 변수 line
						String line = "";
						String outputCode = "";
						int index ;
						// 한 줄씩 읽어서 line에 넣은 후 null이 아니면 실행
						while( (line = reader.readLine()) != null ) {
							for(int i=0; i<count_of_package; i= i+2){
								while(true){
									int index2 = line.indexOf("package");
									index = line.indexOf(packageList[i]);
									if(index !=-1 && index2 != -1)
										line = line.replace(packageList[i], packageList[i+1]);
									else
										break;
								}
							}
							outputCode += line+"\n";
						}
						FileWriter fw = new FileWriter(file);
						fw.write(outputCode);
						fw.close();
						reader.close();

					} catch (FileNotFoundException fnf) {
						fnf.printStackTrace();
					} catch( IOException e) {
						e.printStackTrace();
					}


				}
			}
		}
	}
}
