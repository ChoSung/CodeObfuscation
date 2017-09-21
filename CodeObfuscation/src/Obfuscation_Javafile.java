import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Obfuscation_Javafile {
	File selectFile;
	public Obfuscation_Javafile(){
		selectFile = BasicSetting.getSelectFile();
	}

	public void obfuscationJavaFile(){
		findJavaClass(selectFile);
	}

	//class 인식
	public void findJavaClass(File selectFile){
		File[] ff = selectFile.listFiles();  //복사할 디렉토리안의 폴더와 파일들을 불러옵니다.
		for (File file : ff) {
			if (file.isDirectory()){ //만약 파일이 아니고 디렉토리(폴더)라면
				findJavaClass(file);      //폴더의 내부를 다시 살펴봅니다.
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
						int count = 0;
						String importPart = "";
						String outputLine = "";
						String mainLine = "";
						String className = file.getName().substring(0,idx);
						boolean flag = false;
						while( (line = reader.readLine()) != null ) {
							idx = line.indexOf("class ");
							if(idx!= -1){

								//class를 찾는다.

								//따움포 안에 있는 class
								if(line.indexOf("\"")!=-1){
									if(idx> line.indexOf("\"")){
										if(idx<line.lastIndexOf("\"")){
											if(count ==0)
												importPart += line+"\n";
											else
												outputLine += line+"\n";
											continue;
										}
									}
								}

								//앞 공백 확인
								if(idx-1>0){
									if(!line.substring(idx-1, idx).equals(" ")){
										if(count ==0)
											importPart += line+"\n";
										else
											outputLine += line+"\n";
										continue;
									}
								}

								tempLine = line.substring(idx+6);
								idx = tempLine.indexOf(" ");
								if(idx == -1)
									idx = tempLine.indexOf("{");
								tempLine = tempLine.substring(0, idx);



								count++;
								if(flag == true){
									mainLine = importPart+"\n"+outputLine;
									flag = false;
								}
								else if(className.equals(tempLine)){
									flag = true;
								}
								else{
									File newLinkFile = new File(file.getParentFile().getAbsolutePath()+"\\"+tempLine+".java");
									FileWriter fw = new FileWriter(newLinkFile);
									fw.write(importPart+"\n"+outputLine);
									fw.close();
								}


								outputLine = "";
								if(!className.equals(tempLine))
									outputLine += "public ";
								outputLine += line+"\n";



							}
							else{
								if(count ==0)
									importPart += line+"\n";
								else
									outputLine += line+"\n";
							}
						}
						if(count ==1){
							mainLine = importPart+"\n"+outputLine;

							File newLinkFile2 = new File(file.getParentFile().getAbsolutePath()+"\\"+className+".java");
							FileWriter fw2 = new FileWriter(newLinkFile2);
							fw2.write(mainLine);
							fw2.close();
							reader.close();
						}
						else{
							File newLinkFile = new File(file.getParentFile().getAbsolutePath()+"\\"+tempLine+".java");
							FileWriter fw = new FileWriter(newLinkFile);
							fw.write(importPart+"\n"+outputLine);
							fw.close();

							File newLinkFile2 = new File(file.getParentFile().getAbsolutePath()+"\\"+className+".java");
							FileWriter fw2 = new FileWriter(newLinkFile2);
							fw2.write(mainLine);
							fw2.close();
							reader.close();
						}

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
