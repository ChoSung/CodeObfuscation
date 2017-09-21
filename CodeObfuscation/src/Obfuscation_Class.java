import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Obfuscation_Class {

	String[] classList ;
	int count_of_class;
	File selectFile;
	
	public Obfuscation_Class(){
		classList = new String[300];
		count_of_class= 0;
		selectFile = BasicSetting.getSelectFile();
	}
	
	public void obfuscationClass(){
		findClass(selectFile);
		changeClass(selectFile);
		changeClassTittle(selectFile);
		BasicSetting.setClassList(classList, count_of_class);
	}
	
	//class �ν�
	public void findClass(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				findClass(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸�
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					try {
						// BufferedReader ������ file�� �ִ´�
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// ������ ���پ� �о� �ֱ� ���� ���� line
						String line = "";
						String tempLine = "";
						// �� �پ� �о line�� ���� �� null�� �ƴϸ� ����
						while( (line = reader.readLine()) != null ) {
							idx = line.indexOf("class ");
							if(idx!= -1){

								//class�� ã�´�.

								//������ �ȿ� �ִ� class
								if(line.indexOf("\"")!=-1){
									if(idx> line.indexOf("\"")){
										if(idx<line.lastIndexOf("\"")){
											continue;
										}
									}
								}

								//�� ���� Ȯ��
								if(idx-1>0){
									if(!line.substring(idx-1, idx).equals(" "))
										continue;
								}

								tempLine = line.substring(idx+6);
								idx = tempLine.indexOf(" ");
								if(idx == -1)
									idx = tempLine.indexOf("{");
								tempLine = tempLine.substring(0, idx);

								classList[count_of_class] = tempLine;
								classList[++count_of_class] = BasicSetting.getNameEn();
								count_of_class++;

							}
						}
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

	//Class �ڵ� ����
	public void changeClass(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.

		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				changeClass(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
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
							for(int i=0; i<count_of_class; i= i+2){
								index = line.indexOf(classList[i]);
								if(index !=-1){
									//�� ���� �� ����
									if( (index-1<0 || line.substring(index-1, index).equals(" ")|| line.substring(index-1, index).equals("\t") || line.substring(index-1, index).equals("\t")) &&
											(line.substring(index+classList[i].length(),index+classList[i].length()+1).equals(" ")  ) ){

										line = line.replace(classList[i], classList[i+1]);
									}
								}

								index = line.indexOf(classList[i]);
								if(index !=-1){
									//�� ���� �� ��ȣ
									if( (index-1<0 || line.substring(index-1, index).equals(" ")||line.substring(index-1, index).equals("\t")) &&
											(line.substring(index+classList[i].length(),index+classList[i].length()+1).equals("(")  ) ){
										line = line.replace(classList[i], classList[i+1]);
									}
								}

								index = line.indexOf(classList[i]);
								if(index !=-1){
									//�� ���� �� .
									if( (index-1<0 || line.substring(index-1, index).equals(" ")||line.substring(index-1, index).equals("\t")) &&
											(line.substring(index+classList[i].length(),index+classList[i].length()+1).equals(".")  ) ){
										line = line.replace(classList[i], classList[i+1]);
									}
								}
								
								index = line.indexOf(classList[i]);
								if(index !=-1){
									//�� ���� �� {
									if( (index-1<0 || line.substring(index-1, index).equals(" ")||line.substring(index-1, index).equals("\t")) &&
											(line.substring(index+classList[i].length(),index+classList[i].length()+1).equals("{")  ) ){
										line = line.replace(classList[i], classList[i+1]);
									}
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

	//Class ������ �̸� ����
	public void changeClassTittle(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				changeClassTittle(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸�
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					String fileName = file.getName().substring(0,idx);
					for(int i=0; i<count_of_class; i= i+2){
						if(fileName.equals(classList[i])){
							File newFile = new File(file.getParent()+"\\"+classList[i+1]+".java");
							if(!file.renameTo(newFile)) 
								System.err.println("�̸� ���� ���� : " + file);
							else{
								String temp = "";
								File tempFile = new File(file.getAbsolutePath());
								while(!tempFile.getParentFile().getName().equals("src")){
									temp += file.getParentFile().getName()+"\\";
									tempFile = tempFile.getParentFile();
								}
								File deleteFile = new File(tempFile.getParentFile().getParent()+"\\bin\\"+temp+classList[i]+".class");
								deleteFile.delete();
							}
							break;
						}
					}
				}
			}
		}
	}

}
