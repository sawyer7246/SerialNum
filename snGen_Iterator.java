	
	/**
	 * 
	 * simple and fast CharacterMode:[0~9 A~Z a~z]
	 * @param no
	 * @param ignoreFlag
	 * @return
	 * @throws Exception
	 */
	private String increaseByCharacterMode(String no ,boolean ignoreFlag) throws Exception{
		int len=no.length();
		char last=no.charAt(len-1);
		String front=len>1?no.substring(0,len-1):"";
		last+=1;
		
		if(ignoreFlag){
			//ignore the confusing letter , such as 'I','l','o'
			if(last=='I' || last=='l' ||last=='O')last+=1;
		}
		if(last>'9'&&last<'A'){
			last='A';
		}else if(last>'Z'&& last<'a'){
			last='a';
		}else if(last>'z'){
			if(len>1){
				last='0';
				front=increaseByCharacterMode(no.substring(0,len-1));
			}else{
				throw new Exception();
			}
		}
		byte[] temp=new byte[1];
		temp[0]=(byte) last;
		
		return front+new String(temp);
	}