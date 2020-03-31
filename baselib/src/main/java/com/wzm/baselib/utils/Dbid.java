package com.wzm.baselib.utils;



/**
*Generator ID
*
*
*/
public class Dbid {
	
	/**
	*Usage sample:
	*String yourID=Faimis.getID();
	*/
	public static String getID(){
		UUIDHexGenerator uuidHex=new UUIDHexGenerator();
		return uuidHex.generate().toString();
		 
	}
}