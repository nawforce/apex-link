import com.nawforce.runtime.parsers.ApexLexer;
import com.nawforce.runtime.parsers.ApexParser;
import com.nawforce.runtime.parsers.CaseInsensitiveInputStream;
import com.nawforce.runtime.parsers.CollectingErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.StringReader;

public class ParseClass {

    public static void main(String[] args) throws IOException {
        CaseInsensitiveInputStream stream = new CaseInsensitiveInputStream(new StringReader("public class Dummy {}"));

        CommonTokenStream tokens = new CommonTokenStream(new ApexLexer(stream));
        tokens.fill();

        CollectingErrorListener listener = new CollectingErrorListener("Dummy.cls");
        ApexParser parser = new ApexParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(listener);

        ApexParser.CompilationUnitContext context = parser.compilationUnit();
        if (listener.issues().length == 0)
            System.out.println("Parse succeeded");
    }
}
