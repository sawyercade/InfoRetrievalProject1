import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Project1 {

    final static String tokensSortedByTermFilename = "sortedByToken.txt";
    final static String tokensSortedByFreqFilename = "sortedByFreq.txt";

    public static void main(String[] args) throws IOException{
        //timers
        long startTimeMs = System.currentTimeMillis();
        long endTimeMs, tokenizeAndWriteStartTime, tokenizeAndWriteEndTime, sortAndWriteStartTime, sortAndWriteEndTime;

        //files
        File inDir = new File(args[0]);
        File[] inFiles = inDir.listFiles();
        int numFiles = inFiles.length;
        File outDir = new File(args[1]);

        //stores tokens and frequencies across documents
        TokenCollector collector = new TokenCollector();

        tokenizeAndWriteStartTime = System.currentTimeMillis();

        //Process each file
        int fileCounter = 0;
        for(File inFile : inFiles){
            DocumentAnalyzer analyzer = new DocumentAnalyzer(); //processes the document
            Map<String, Integer> freqs = analyzer.tokenize(inFile.getAbsolutePath());
            collector.addTokens(freqs);

//            for (Map.Entry<String, Integer> entry : freqs.entrySet()){
//
//            }

            writeTokensToFile(freqs, outDir.getAbsolutePath()+"/"+inFile.getName());
            fileCounter++;
        }

        tokenizeAndWriteEndTime = System.currentTimeMillis();

        //Write all tokens in tokenCollector
        sortAndWriteStartTime = System.currentTimeMillis();
        writeSortedTokens(collector.sortFrequenciesByFreq(), outDir.getAbsolutePath()+"/"+ tokensSortedByFreqFilename);
        writeSortedTokens(collector.sortFrequenciesByTerm(), outDir.getAbsolutePath()+"/"+ tokensSortedByTermFilename);

        sortAndWriteEndTime = endTimeMs = System.currentTimeMillis();

        System.out.println("Number of files: " + numFiles);
        System.out.println("Number of unique tokens: " + collector.getUniqueTokens());
        System.out.println("Number of non-unique tokens: " + collector.getNonuniqueTokens());
        System.out.println("Total runtime: " + (System.currentTimeMillis() - startTimeMs)+"ms");
        System.out.println("Runtime of tokenizing + writing tokens to files: " + (tokenizeAndWriteEndTime-tokenizeAndWriteStartTime)+"ms");
        System.out.println("Runtime of sorting + writing sorted files: " + (sortAndWriteEndTime - sortAndWriteStartTime)+"ms");
    }

    public static void writeSortedTokens(List<TermFreq> termFreqs, String outputFilepath) throws IOException{
        File outFile = new File(outputFilepath);
        if(outFile.exists()){
            outFile.delete();
        }
        outFile.createNewFile();

        FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for(TermFreq t : termFreqs){
            bw.write(String.format("%s %d\n", t.getTerm(), t.getFreq()));
        }
        bw.close();
        fw.close();
    }

    public static void writeTokensToFile(Map<String, Integer> tokens, String outputFilepath)throws IOException{
        File outFile = new File(outputFilepath);
        if(outFile.exists()){
            outFile.delete();
        }
        outFile.createNewFile();

        FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for(Map.Entry<String, Integer> entry : tokens.entrySet())
        {
            for(int i = 0; i < entry.getValue(); i++){
                bw.write(String.format("%s ", entry.getKey()));
            }

        }
        bw.close();
        fw.close();
    }
}
