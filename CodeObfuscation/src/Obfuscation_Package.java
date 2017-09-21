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
		//src ������ class ���� ��� bin ���Ϸ� �̵�
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				if(flag){
					String fileName = file.getName();
					packageList[count_of_package] = fileName;
					packageList[++count_of_package] = BasicSetting.getNameEn();
					count_of_package++;
					getPackageName(file, true); 
				}
				else if(file.getName().equals("src")){
					getPackageName(file, true);      //������ ���θ� �ٽ� ���캾�ϴ�.
				}
				else{
					getPackageName(file, false);
				}
			}
		}
	}

	public void changePackageName(File selectFile){
		//src ������ class ���� ��� bin ���Ϸ� �̵�
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���

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
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.

		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				changePackageCode(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸�
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					try {
						// BufferedReader ������ file�� �ִ´�
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// ������ ���پ� �о� �ֱ� ���� ���� line
						String line = "";
						String outputCode = "";
						int index ;
						// �� �پ� �о line�� ���� �� null�� �ƴϸ� ����
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
