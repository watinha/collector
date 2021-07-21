import edu.utfpr.xbi.collector.Collector;
import edu.utfpr.xbi.merger.Merger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CollectorPipeline {
    public static void main (String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
                new FileReader("url_alexa_downloaded.txt"));
        String linha;
        int count = 1;
        int start = -1;
        if (args.length > 0)
          start = Integer.parseInt(args[0]);
        while ((linha = br.readLine()) != null) {
            int index = count++;
            System.out.println(String.format("%d : %s", index, linha));
            if (start == -1 || start <= index) {
              String [] arguments = { linha };
              File dir = new File("log/" + index);
              if (!dir.exists())
                  dir.mkdir();
              Collector.main(arguments);
              Merger.main(arguments);
              Runtime.getRuntime().exec(
                      String.format("cp -rf results/ log/%d/", index)).waitFor();
              Runtime.getRuntime().exec(
                      String.format("find results/ -name *.csv -delete")).waitFor();
              Runtime.getRuntime().exec(
                      String.format("find results/ -name *.png -delete")).waitFor();
              Runtime.getRuntime().exec(
                      String.format("find results/ -name *.arff -delete")).waitFor();
            }
        }
        br.close();
    }
}
