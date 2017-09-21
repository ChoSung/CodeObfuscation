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
			System.out.println("creatStringEncryptionClass ����");
			e.printStackTrace();
		}
	}

	public void findString(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.

		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				findString(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸�
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
									//********************************************************�̺κ�
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

		//��Ű�� ���� ���� ���� �ؾ� �Ѵ�.
		File orgFile = new File("src\\StringEncryption.java");

		FileInputStream inputStream = new FileInputStream(orgFile);
		//FileOutputStream outputStream = new FileOutputStream(selectFile+"\\src\\StringEncryption.java"); 
		FileChannel fcin =  inputStream.getChannel();


		//src ������ class ���� ��� bin ���Ϸ� �̵�
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
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
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.

		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���

			}else{                   
				if(file.getName().equals("StringEncryption.java")){
					try {
						// BufferedReader ������ file�� �ִ´�
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// ������ ���پ� �о� �ֱ� ���� ���� line
						String line = "";
						String outputCode = "package "+selectFile.getName()+";\n";
						int index ;
						// �� �پ� �о line�� ���� �� null�� �ƴϸ� ����
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
