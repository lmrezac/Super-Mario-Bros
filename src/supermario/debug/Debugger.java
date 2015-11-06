package supermario.debug;

import java.lang.reflect.Member;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Debugger{
	public static boolean debug = true;
	public static boolean debugDebug = false;
	public static void debug(Object obj, String msg){
		if(debug){
			Member m = getMethodFromStack(3);
			Class c = m.getDeclaringClass();
			System.out.println("["+c.getSimpleName()+"] "+((obj != null)? "("+obj.toString()+") " : "")+m.getName()+"() "+msg);
		}
	}
	public static void debug(Object obj, Exception e, String msg){
		if(debug){
			Member m = getMethodFromStack(3);
			Class c = m.getDeclaringClass();
			System.out.println("["+c.getSimpleName()+":"+e.getStackTrace()[0].getLineNumber()+"] "+((obj != null)? "("+obj.toString()+") " : "")+m.getName()+"() "+msg);
		}	
	}
	public static Member getMethodFromStack(int index){
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		String methodname = stack[index].getMethodName();
		//String val = stack[index].toString();
		//System.out.println("[Debugger:26] getMethodFromStack() classname = "+val);
		//int i = val.indexOf(".");
		//Pattern pattern = Pattern.compile("(?<=([a-z]{1,50}\\.){1,10}[A-Z][a-z]{1,50})\\.");
		//Matcher matcher = pattern.matcher(val);
		//if(!matcher.find()){
		//	System.out.println("[Debugger:33] getMethodFromStack() Error: couldn't find appropriate class name.");
		//	System.exit(0);
		//}else{
		//	i = matcher.end();
		//}
		int i;
		String classname = stack[index].getClassName();//val.substring(0,i);
		if(classname.contains("$")){
			i = classname.indexOf("$");
			//classname = classname.substring(i+1);
		}
		//System.out.println("Val = "+val);
	//	System.out.println(Thread.currentThread().getStackTrace()[3].getMethodName());
		if(debug && debugDebug)
			System.out.println("[Debugger:46] getMethodFromStack() trace:"+stack[3].getMethodName()+"(); method:"+methodname+"(); classname = "+classname);
		
		
			//classname = classname.substring(i+1);
		
		Class c = null;
		try{
			c = Class.forName(classname);
		}catch(ClassNotFoundException e){
			System.out.println("[Debugger:55] getMethodFromStack() Error: class not found: "+classname);System.exit(0);return null;
		}
		if(debug&&debugDebug)
			System.out.println("[Debugger:58] getMethodFromStack() class = "+c.getSimpleName());
		Member[] methods;
		if(methodname.equals("<init>")){
			methods = (Member[])c.getConstructors();
			return methods[0];
		}else methods = (Member[])c.getDeclaredMethods();
		for(Member m : methods){
			if(m.getName().equals(methodname)){
				return m;
			}
		}
		System.out.println("[Debugger:69] getMethodFromStack() Error: method "+methodname+" not found.");
		return null;
	}
	public static void debug(String msg){
		if(debug){
			Member m = getMethodFromStack(3);
			Class c = m.getDeclaringClass();
			System.out.println("["+c.getSimpleName()+"] "+m.getName()+"() "+msg);
		}
	}
	public static void debug(Exception e,String msg){
		if(debug){
			Member m = getMethodFromStack(3);
			Class c = m.getDeclaringClass();
			System.out.println("["+c.getSimpleName()+":"+e.getStackTrace()[0].getLineNumber()+"] "+m.getName()+"() "+msg);
		}
	}

	public static Object error(String msg){ 
		Member m = getMethodFromStack(3);
		Class c = m.getDeclaringClass();
		System.out.println("["+c.getSimpleName()+"] "+m.getName()+"() "+msg);	
		System.exit(0);return null;
	}
	public static Object error(Exception e,String msg){ 
		Member m = getMethodFromStack(3);
		Class c = m.getDeclaringClass();
		System.out.println("["+c.getSimpleName()+":"+e.getStackTrace()[0].getLineNumber()+"] "+m.getName()+"() "+msg);	
		System.exit(0);return null;
	}
		
	public static Object error(Object obj, String msg){
		Member m = getMethodFromStack(3);
		Class c = m.getDeclaringClass();
		System.out.println("["+c.getSimpleName()+"] "+((obj != null)? "("+obj.toString()+") " : "")+m.getName()+"() "+msg);	
		System.exit(0);return null;
	}
	public static Object error(Object obj,Exception e, String msg){
		Member m = getMethodFromStack(3);
		Class c = m.getDeclaringClass();
		System.out.println("["+c.getSimpleName()+":"+e.getStackTrace()[0].getLineNumber()+"] "+((obj != null)? "("+obj.toString()+") " : "")+m.getName()+"() "+msg);	
		System.exit(0);return null;
	}
}
