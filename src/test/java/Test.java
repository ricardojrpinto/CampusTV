import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;



public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.put("chouriço","porco preto");
		p.store(new PrintWriter("orgulhonacional"), 
				"Eis uma lista de iguarias portuguesas com a respetiva \"fonte orgânica\"");
	}

}
