﻿
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;



public class snGen {

	 
	public static void main(String[] args) {
		 
		String testStr = "001";//you can define the length of this String 
		 
		try{
				iteratorStr(testStr);
		}catch(Exception e){
				System.out.println("stop now");
		}
	}

	 public static String iteratorStr(String str) throws Exception{
		System.out.println(str);
		String returnStr= voucherGen(str);
		return iteratorStr(returnStr);
	}
	

	public static String voucherGen(String voucherNo) throws  Exception {
		 String headStr =  "";//  get the letters
		 String numStr ="";// get the numbers
		 int num = -1 ;
		
		//find the index when the first number char  occur 
		int no = -1 ;
		char[] charArray = voucherNo.toCharArray();
		for(int i = 0; i< charArray.length ; i++){
			int asc = charArray[i] ;//convert letter to acsii number  直接把字符转换成asc数字
			if(asc >= 48 && asc <= 57){
				no=i;
				break;
			}
		}
	 if(no == -1){
		 //not number char 
		 headStr = voucherNo ;
	 }else{
		 headStr =  voucherNo.substring(0, no);//get the letters part
		  numStr = voucherNo.substring(no,charArray.length);//get the  number part  获取数字部分
	 }
	 
	 char first = charArray[0];//the first letter to identify which round (round1: A-Z , round2:a-z ) 第一个字母,用来判断第几轮
	 
	 if(StringUtils.isNotBlank(numStr)){
		 //numberStr is not blank 数字位不为空先加
		 num = NumberUtils.toInt(numStr);
		 num++;
		 
		 if( ObjectUtils.toString(num).length() > numStr.length()){
			
			 //pure number over flow  如果是纯数字溢出
			 if(StringUtils.isBlank(headStr)){
				 //turn the first letter to 'A' 最高位变成A
				 charArray[0]='A';
				 no=1;//following part of letters  后面重置从1开始
			}
			 
			 //如果数字位溢出，加到字母位
			 char preAdd = (char) ((int)charArray[no-1]+1) ;//预先字母位加字符
			 
			 //先判断是第几轮的字母
			 if(first>= 65 && first <=90 ){
				 //第一轮加字母 A-Z
				 
				 //如果发现字母下标溢出,即超过Z或者超过z
				 if(preAdd>90){
					 //第一轮字母溢出
					 charArray[no] =  'A';
					 no++;
					 //其他的数字位都置为0
				 }else{
					 charArray[no-1]=preAdd ;
				 }
				 
			 }else if(first>= 97 && first <=122){
				 //第二轮加字母 a-z
				 
				 if(preAdd>122){
					 //第二轮字母溢出
					 charArray[no] =  'a';
					 no++;
					 }else{
						 charArray[no-1]=preAdd ;
					 }
			 }else if(first>= 48 && first <=57){
				 //纯数字，没加过字母
				 charArray[0]='A';
			 }
			 //如果溢出都要进行数字位重置
			 
			 //先循环从倒数第一位到数字位之后填0
			 for(int k=no ; k < charArray.length-1 ; k++){
				 charArray[k]='0';
			 }
			 
			 //最后一位设置1,如果只有只有一位溢出就不需要设置1
			 if(no<charArray.length){
				 charArray[charArray.length-1]='1';//最后一位设为1
			 }
			 
		 }else{
			 //数字位没有溢出
			 String numReturn=ObjectUtils.toString(num);
			 
			 //填充缺少的0进去
			 if(ObjectUtils.toString(num).length() < numStr.length()){
				 for(int m=0; m<numStr.length()-ObjectUtils.toString(num).length();m++){
					 numReturn = "0"+numReturn;
				 }
			 }
			 return headStr+numReturn ;
		 }
		 
	 }else{
		 //数字位为空，纯字母加减
		 char preAdd = (char) (charArray[charArray.length-1]+1) ;//预先字母位加好字符
		 
		 //先判断是第几轮递增
		 if(first>= 65 && first <=90 ){
			 //第一轮,最后一个字母加
			 
			 if(preAdd>90){
				 //边界条件，从第一轮加到第二轮
				 charArray[0]='a';
				 for(int k=1 ; k < charArray.length-1 ; k++){
					 charArray[k]='0';
				 	}
				 charArray[charArray.length-1]='1';//最后一位设为1
			 }else{
					 charArray[charArray.length-1]=preAdd;//正常的加
				 }
		 }else if(first>= 97 && first <=122){
			 //第二轮
			 if(preAdd>122){
				 //边界条件，从第二轮越界
				 throw  new RuntimeException("Voucher Number Over Flow !");
			 }else{
					 charArray[charArray.length-1]=preAdd;//正常的加
			 }
		 }
	 }
	return new String(charArray);
	}
 

}
