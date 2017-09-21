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

	//class �ν�
	public void findJavaClass(File selectFile){
		File[] ff = selectFile.listFiles();  //������ ���丮���� ������ ���ϵ��� �ҷ��ɴϴ�.
		for (File file : ff) {
			if (file.isDirectory()){ //���� ������ �ƴϰ� ���丮(����)���
				findJavaClass(file);      //������ ���θ� �ٽ� ���캾�ϴ�.
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
						int count = 0;
						String importPart = "";
						String outputLine = "";
						String mainLine = "";
						String className = file.getName().substring(0,idx);
						boolean flag = false;
						while( (line = reader.readLine()) != null ) {
							idx = line.indexOf("class ");
							if(idx!= -1){

								//class�� ã�´�.

								//������ �ȿ� �ִ� class
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

								//�� ���� Ȯ��
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
