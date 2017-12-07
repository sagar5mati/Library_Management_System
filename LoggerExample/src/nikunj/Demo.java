package nikunj;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {
	public static void main(String []args) {
		
		MyLogger.getInstance().log(Level.ALL, "NO EXCEPTION");
		MyLogger.getInstance().log(Level.ALL, "NICE!");
		try {
			int []a = {1,2,3};
			int index = 4;
			System.out.println(a[index]);
		}
		catch (Exception e) {
			MyLogger.getInstance().log(Level.ALL, " From New logger", e);
		}
		return;
	}
}