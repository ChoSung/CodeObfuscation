import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Obfuscation_Debug {
	File selectFile;
	public Obfuscation_Debug(){
		selectFile = BasicSetting.getSelectFile();
	}

	public void removeDebug(){
		removeDebug(selectFile);
	}

	public void removeDebug(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				removeDebug(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸�
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					removeAnnotation(file);
					removeSysout(file);
					removeEmptyLine(file);
				}
			}
		}
	}

	//Sysout ����
	public void removeSysout(File file){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int idx;
			String line = "";
			String outputCode = "";
			while( (line = reader.readLine()) != null ) {
				idx = line.indexOf("System.out.");
				if(idx!= -1){
					if(!isInQuotationmark(line, idx))
						line = line.substring(0,idx);
				}
				outputCode += line+"\n";
			}
			FileWriter fw = new FileWriter(file);
			fw.write(outputCode);
			fw.close();
			reader.close();
		} catch( IOException e) {
			e.printStackTrace();
		}
	}

	//�ּ� ����
	public void removeAnnotation(File file){
		int idx, idx2;
		String outputCode ="";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			idx = -1; idx2 = -1;;
			boolean flag = true;
			String lineTemp = null;
			while( (line = reader.readLine()) != null ) {
				if(flag){
					idx = line.indexOf("//");
					if(idx!= -1){
						if(!isInQuotationmark(line, idx))
							line = line.substring(0,idx);
					}

					idx = line.indexOf("/*");
					if(idx!= -1){
						if(!isInQuotationmark(line, idx)){
							lineTemp = line.substring(0,idx);
							idx2 = line.indexOf("*/");
							if(idx2!= -1){
								line = lineTemp + line.substring(idx+1,idx2);
							}
							else{
								line = lineTemp;
								flag = false;
							}
						}
					}
				}
				else{
					idx2 = line.indexOf("*/");
					if(idx2!= -1){
						line = line.substring(idx2+2);
						flag = true;
					}
					else{
						line = "";
					}
				}
				outputCode += line+"\n";
			}
			FileWriter fw = new FileWriter(file);
			fw.write(outputCode);
			fw.close();
			reader.close();

		}catch( IOException e) {
			e.printStackTrace();
		}
	}
	
	//���� ���� ����
	public void removeEmptyLine(File file){
		String outputCode ="";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while( (line = reader.readLine()) != null ) 
				if(!isLineEmpty(line))
					outputCode += line+"\n";
			FileWriter fw = new FileWriter(file);
			fw.write(outputCode);
			fw.close();
			reader.close();

		}catch( IOException e) {
			e.printStackTrace();
		}
	}

	//line�� ���� Ȥ�� \t�θ� ���� �� �ִ��� Ȯ��
	public boolean isLineEmpty(String line){
		if(line.equals(""))
			return true;
		for(int i=0; i<line.length(); i++)
			if(!line.substring(i, i+1).equals("\t"))
				return false;
		return true;
	}

	//idx�� ��ġ�� ""Ȥ�� '' �ȿ� �ִ��� Ȯ��
	public boolean isInQuotationmark(String line, int idx){
		if(line.indexOf("\"")!=-1 && idx> line.indexOf("\"") && idx<line.lastIndexOf("\""))
			return true;
		if(line.indexOf("\'")!=-1 && idx> line.indexOf("\'") && idx<line.lastIndexOf("\'"))
			return true;
		return false;
	}
	
}
