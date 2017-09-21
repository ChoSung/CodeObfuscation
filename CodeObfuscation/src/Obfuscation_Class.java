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
	
	//class 인식
	public void findClass(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				findClass(file);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					try {
						// BufferedReader 변수에 file을 넣는다
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// 파일을 한줄씩 읽어 넣기 위한 변수 line
						String line = "";
						String tempLine = "";
						// 한 줄씩 읽어서 line에 넣은 후 null이 아니면 실행
						while( (line = reader.readLine()) != null ) {
							idx = line.indexOf("class ");
							if(idx!= -1){

								//class를 찾는다.

								//따움포 안에 있는 class
								if(line.indexOf("\"")!=-1){
									if(idx> line.indexOf("\"")){
										if(idx<line.lastIndexOf("\"")){
											continue;
										}
									}
								}

								//앞 공백 확인
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

	//Class 코드 변경
	public void changeClass(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.

		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				changeClass(file);      //폴더의 내부를 다시 살펴봅니다.
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
							for(int i=0; i<count_of_class; i= i+2){
								index = line.indexOf(classList[i]);
								if(index !=-1){
									//앞 공백 뒤 공백
									if( (index-1<0 || line.substring(index-1, index).equals(" ")|| line.substring(index-1, index).equals("\t") || line.substring(index-1, index).equals("\t")) &&
											(line.substring(index+classList[i].length(),index+classList[i].length()+1).equals(" ")  ) ){

										line = line.replace(classList[i], classList[i+1]);
									}
								}

								index = line.indexOf(classList[i]);
								if(index !=-1){
									//앞 공백 뒤 괄호
									if( (index-1<0 || line.substring(index-1, index).equals(" ")||line.substring(index-1, index).equals("\t")) &&
											(line.substring(index+classList[i].length(),index+classList[i].length()+1).equals("(")  ) ){
										line = line.replace(classList[i], classList[i+1]);
									}
								}

								index = line.indexOf(classList[i]);
								if(index !=-1){
									//앞 공백 뒤 .
									if( (index-1<0 || line.substring(index-1, index).equals(" ")||line.substring(index-1, index).equals("\t")) &&
											(line.substring(index+classList[i].length(),index+classList[i].length()+1).equals(".")  ) ){
										line = line.replace(classList[i], classList[i+1]);
									}
								}
								
								index = line.indexOf(classList[i]);
								if(index !=-1){
									//앞 공백 뒤 {
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

	//Class 파일의 이름 변경
	public void changeClassTittle(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				changeClassTittle(file);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					String fileName = file.getName().substring(0,idx);
					for(int i=0; i<count_of_class; i= i+2){
						if(fileName.equals(classList[i])){
							File newFile = new File(file.getParent()+"\\"+classList[i+1]+".java");
							if(!file.renameTo(newFile)) 
								System.err.println("이름 변경 에러 : " + file);
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
