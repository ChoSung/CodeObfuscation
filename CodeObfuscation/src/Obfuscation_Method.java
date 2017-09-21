import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Obfuscation_Method {
	//�ϴ� �迭�� �޼ҵ� ��� 300������
	String methodList[];
	String classList[];
	int count_of_method = 0;
	int count_of_class;
	File selectFile;

	public Obfuscation_Method(){
		methodList = new String[300];
		count_of_method= 0;
		selectFile = BasicSetting.getSelectFile();
		classList = BasicSetting.getClassList();
		count_of_class = BasicSetting.getCountOfClass();

	}

	public void obfuscationMethod(){
		findMethod(selectFile);
		changeMethod(selectFile);
		
	}

	//method �ν�
	public void findMethod(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				findMethod(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
			}else{                   //���� �����̸�
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					try {
						// BufferedReader ������ file�� �ִ´�
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// ������ ���پ� �о� �ֱ� ���� ���� line
						String line = "";
						boolean flag = false;
						// �� �پ� �о line�� ���� �� null�� �ƴϸ� ����
						while( (line = reader.readLine()) != null ) {
							if(flag){
								flag = false;
								continue;
							}
							if(line.indexOf("@Override")!= -1)
								flag = true;
							
							idx = line.indexOf("(");
							if(idx != -1){
								if(line.indexOf("{") != -1 &&line.indexOf("class")==-1 && line.indexOf("while")== -1
										&& line.indexOf("if")== -1 && line.indexOf("for")==-1 && line.indexOf("catch")== -1
										&& line.indexOf("main")==-1 && !isInQuotationmark(line, line.indexOf("{")) ){
									//�ϴ� ��ȣ�ϰ� ���ȣ ������ �޼ҵ��ΰɷ� �ν� ���� ����
									String ifMethod="";
									int num = line.lastIndexOf(" ",idx);
									if(num != -1){
										ifMethod = line.substring(num+1, idx);
										if(!Arrays.asList(classList).contains(ifMethod)){
											methodList[count_of_method] = ifMethod;
											methodList[++count_of_method] = BasicSetting.getNameEn();
											count_of_method++;
										}
									}
								}
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

	//method �ڵ� ����
	public void changeMethod(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.

		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				changeMethod(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
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
							for(int i=0; i<count_of_method; i= i+2){
								while(true){
									index = line.indexOf(methodList[i]);
									if(index !=-1 && !isInQuotationmark(line, index))
										line = line.replace(methodList[i], methodList[i+1]);
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

	//idx�� ��ġ�� ""Ȥ�� '' �ȿ� �ִ��� Ȯ��
	public boolean isInQuotationmark(String line, int idx){
		int moveNum = 0;
		int temp;
		int flag = 0;
		while(true){
			flag++;
			temp= moveNum;
			moveNum = line.indexOf("\"",moveNum)+1;
			if(moveNum==0)
				break;
			
			if(flag%2 ==0){
				if(idx>=temp && idx<=moveNum){
					return true;
				}
			}
			
		}
		return false;
	}

}
