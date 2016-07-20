package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ProjectUtilities {
	
	public static double averageOfFive(double one, double two, double three, double four, double five) {
		return ((one+two+three+four+five)/5);
	}

	public static void serializeObject(Object obj) throws IOException {
		FileOutputStream fileOut =
		         new FileOutputStream("./" + ProjectProperties.treeObjectFileName);
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(obj);
		         out.close();
		         fileOut.close();
		         System.out.printf("Serialized data is saved in " + ProjectProperties.treeObjectFileName);
		
	}

	public static Object getSerializedObject(String objectFileName) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream("./" + objectFileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object inObject = in.readObject();
        in.close();
        fileIn.close();
        return inObject;
	}

}
