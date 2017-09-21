import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Obfuscation_String {
	File selectFile;
	public Obfuscation_String(){
		selectFile = BasicSetting.getSelectFile();
	}

	public void obfuscationString(){
		findString(selectFile);
		try {
			creatStringEncryptionClass(selectFile, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("creatStringEncryptionClass 오류");
			e.printStackTrace();
		}
	}

	public void findString(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.

		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				findString(file);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					try {
						BufferedReader reader = new BufferedReader(new FileReader(file));
						String line = "";
						String outputCode = "";
						int index , index2;
						while( (line = reader.readLine()) != null ) {
							index = line.indexOf("\"");
							index2 = line.indexOf("\"", index+1);
							if(index!=-1 && index2!=-1){
								if(line.charAt(index2-1)!='\\'){
									String temp = line.substring(index+1, index2);
									String temp2 = line.substring(index, index2+1);
									line = line.replace(temp2, changeStringLaw(temp)); 
									//********************************************************이부분
								}
							}
							outputCode += line;
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

	public void creatStringEncryptionClass(File selectFile , boolean flag) throws IOException{

		//패키지 마다 전부 복사 해야 한다.
		File orgFile = new File("src\\StringEncryption.java");

		FileInputStream inputStream = new FileInputStream(orgFile);
		//FileOutputStream outputStream = new FileOutputStream(selectFile+"\\src\\StringEncryption.java"); 
		FileChannel fcin =  inputStream.getChannel();


		//src 폴더에 class 있을 경우 bin 파일로 이동
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				if(flag){
					FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath()+"\\StringEncryption.java");
					System.out.println(file.getAbsolutePath());
					FileChannel fcout = outputStream.getChannel(); 
					long size = fcin.size();
					fcin.transferTo(0, size, fcout); 
					fcout.close();
					outputStream.close();
					importPackage(file);
					creatStringEncryptionClass(file, true); 
				}
				else if(file.getName().equals("src")){
					FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath()+"\\StringEncryption.java");
					System.out.println(file.getAbsolutePath());
					FileChannel fcout = outputStream.getChannel(); 
					long size = fcin.size();
					fcin.transferTo(0, size, fcout); 
					fcout.close();
					outputStream.close();
					creatStringEncryptionClass(file, true); 
				}
				else{
					creatStringEncryptionClass(file, false);
				}
			}
		}


		fcin.close(); 

		inputStream.close();
	}

	public void importPackage(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.

		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면

			}else{                   
				if(file.getName().equals("StringEncryption.java")){
					try {
						// BufferedReader 변수에 file을 넣는다
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// 파일을 한줄씩 읽어 넣기 위한 변수 line
						String line = "";
						String outputCode = "package "+selectFile.getName()+";\n";
						int index ;
						// 한 줄씩 읽어서 line에 넣은 후 null이 아니면 실행
						while( (line = reader.readLine()) != null ) {
							

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

	public String changeStringLaw(String input){
		if(input.equals("")){
			return "\"\"";
		}
		char[] a = null;
		a = input.toCharArray();
		String returnStr = "StringEncryption.getStr(\"";
		for(int i=0; i<a.length; i++){
			returnStr += (int)a[i]+"l";
		}
		returnStr+= "\")";
		return returnStr;
	}
}
