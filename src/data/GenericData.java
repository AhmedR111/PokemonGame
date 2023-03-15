package data;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic class which transforms data from a file to various object instances
 * @param <T>
 */
public abstract class GenericData<T>  {

    private String dataSourceFile;

    /**
     * Constructor gets initialized with the relative path to the input file
     * @param dataSourceFile
     */
    public GenericData(String dataSourceFile) {
        this.dataSourceFile = this.getThePathToInit() + "\\" +dataSourceFile;

    }

    protected abstract T transformare(List<String> allLinesInFile);

    /**
     * Reading the src folder path
     * @return
     */
    protected String getThePathToInit(){
        String path = "src/init";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        return absolutePath;
    }

    /**
     * Method dedicated to processing one entity type from the file (i.e. Arena)
     * @return
     */
    public T findOne(){
        T result = null;
        try{
            List<String> fileLines = Files.readAllLines(Paths.get
                    (dataSourceFile), StandardCharsets.UTF_8);
            result = transformare(fileLines);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }



}
