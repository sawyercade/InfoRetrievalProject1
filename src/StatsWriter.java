import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StatsWriter {
    private File statsFile;
    private FileOutputStream outputStream;

    public StatsWriter(File outFile) throws IOException {
        this.statsFile = outFile;
        if(!statsFile.exists()){
            statsFile.createNewFile();
        }
        outputStream = new FileOutputStream(this.statsFile);
    }

    public void writeEntry(int numFiles, long numNonUniqueTokens, long numUniqueTokens, long runtime) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append(numFiles);
        sb.append(",");
        sb.append(numNonUniqueTokens);
        sb.append(",");
        sb.append(numUniqueTokens);
        sb.append(",");
        sb.append(runtime);
        sb.append("\n");

        outputStream.write(sb.toString().getBytes());
    }

    public void close() throws IOException{
        outputStream.close();
    }
}
