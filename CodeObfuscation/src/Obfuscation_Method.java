import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Obfuscation_Method {
	//일단 배열로 메소드 경우 300개까지
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

	//method 인식
	public void findMethod(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				findMethod(file);      //폴더의 내부를 다시 살펴봅니다.
			}else{                   //만약 파일이면
				int idx = file.getName().indexOf(".");
				String getFileNameExtension = file.getName().substring(idx+1);
				if(getFileNameExtension.equals("java")){
					try {
						// BufferedReader 변수에 file을 넣는다
						BufferedReader reader = new BufferedReader(new FileReader(file));
						// 파일을 한줄씩 읽어 넣기 위한 변수 line
						String line = "";
						boolean flag = false;
						// 한 줄씩 읽어서 line에 넣은 후 null이 아니면 실행
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
									//일단 괄호하고 대괄호 있으면 메소드인걸로 인식 추후 수정
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

	//method 코드 변경
	public void changeMethod(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.

		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				changeMethod(file);      //폴더의 내부를 다시 살펴봅니다.
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

	//idx의 위치가 ""혹은 '' 안에 있는지 확인
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
