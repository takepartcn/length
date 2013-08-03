import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class length {
	static String output="tangtao1109@163.com\r\n\r\n";
	static HashMap<String, Double> units=new HashMap<String ,Double>();
	static List<String> equals=new ArrayList<String>();
	public static void main(String[] args) {
		//从指定目录下加载input.txt文件
		readFile("D:/input.txt");
		for(int i=0;i<equals.size();i++){
			String equ[]=equals.get(i).split(" ");
			jisuan(ruzhan(equ));
			
		}
		Write2File(output);
	}
	//模拟将等式入栈		
	public static List<String> ruzhan(String[] str){
		Double fist=0.0;
		List<String> list=new ArrayList<String>();
		for(int i=0;i<str.length;i++){
			if(i%3==0){
				fist=Double.parseDouble(str[i]);
			}
			if(i%3==1){
				String temp=str[i];
				Double d=units.get(temp);
				if(d==null){
					if(temp.equals("miles")){
						temp="mile";
					}else if(temp.equals("inches")){
						temp="inch";
					}else if(temp.equals("feet")){
						temp="foot";
					}else if(temp.equals("faths")){
						temp="fath";
					}else if(temp.equals("yards")){
						temp="yard";
					}
					d=units.get(temp);
				}
				if(d==null){
					d=units.get(temp.substring(0,temp.length()-2));
				}
				
				fist= d*fist;
				list.add(fist.toString());
			}
			if(i%3==2){
				list.add(str[i]);
			}
		}
		return list;
	}
	//模拟将等式出栈并计算
	public static void jisuan(List<String> str){
		Double resule=0.0;
		for(int i=0;i<str.size();i++){
			if(str.get(i).equals("+")){
				resule=resule+Double.parseDouble(str.get(i+1));
				i++;
			}else if(str.get(i).equals("-")){
				resule=resule-Double.parseDouble(str.get(i+1));
				i++;
			}else{
				resule=resule+Double.parseDouble(str.get(i));
			}
		}
		int j=(int)(resule*100+0.5); 
		//把小数点后两位移动到个位，然后+0.5舍去小数 
		resule=(double)j/100.00;
		
		//补全java省去的最后为0的小数位
		String temp=resule+"";
		temp=temp.split("\\.")[1];
		if(temp.length()==1){
			output=output+resule+"0 m\r\n";
		}else{
			output=output+resule+" m\r\n";
		}
		
		
	}
	//根据正则表达式提取单位换算与计算表达式
	public static boolean getUnit(String inputStr){
			String str="\\d+\\s*\\w+\\s*=\\s*\\d+\\.\\d+\\s*m";
			Pattern pattern = Pattern.compile(str,Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(inputStr);
			boolean flage=false;
			while (matcher.find())
			{
				String strs[]=matcher.group().split(" ");
				double value= Double.parseDouble(strs[3])/ Double.parseDouble(strs[0]);
				units.put(strs[1].trim(),value);
				flage=true;
			}
			return flage;
	}
	//读取文件，并将等式与单位换算存入静态变量中
	 private static void readFile(String path) {
		 InputStreamReader read=null;
         String line = null;
         try {
        	 read = new InputStreamReader(new FileInputStream(path));
        	 BufferedReader reader = new BufferedReader(read);
             while ((line = reader.readLine()) != null) {  //处理换行符
            	 if(!getUnit(line)&&!line.trim().equals("")){
            		 equals.add(line);
            	 }
             }
             read.close();
         } catch (IOException e) {
             e.printStackTrace();
         } 
	 }
	 private static void Write2File(String str){
		 FileWriter writer;
		 File file=new File("D:/output.txt");//将结果输出到D盘下
	            try {
					writer = new FileWriter(file);
					writer.write(str);
		            writer.flush();
		            writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	 }
}

