
package org.onebeartoe.modeling.openscad.test.suite;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.onebeartoe.modeling.openscad.test.suite.utils.DataSetValidator;
import org.onebeartoe.modeling.openscad.test.suite.utils.ImageComparisonResult;
import org.onebeartoe.modeling.openscad.test.suite.utils.OpenScadFileFinder;
import org.onebeartoe.modeling.openscad.test.suite.utils.OpenScadTestSuiteService;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public abstract class OpenScadTestSuiteTest 
{
    // this was added to make sure the test get picked up
    public abstract void willItWork();
    
    private Logger logger;
    
    private RunProfile runProfile;
    
    private OpenScadTestSuiteService testService;
    
    public OpenScadTestSuiteTest() throws Exception
    {
        File pwd = new File(".");
      
        logger = Logger.getLogger( getClass().getName() );
     
        System.out.println("pwd: " + pwd.getAbsolutePath() );
        
        String key = "OPENSCAD_SUBPATH";
        String openScadSubpath = System.getProperty(key, "");
        System.out.println("the " + key + " is: " + openScadSubpath);
        
        openScadSubpath = System.getenv(key);
        openScadSubpath = openScadSubpath == null ? "" : openScadSubpath;
        System.out.println("the env " + key + " is: " + openScadSubpath);
        
        testService = new OpenScadTestSuiteService();

        runProfile = new RunProfile();

        runProfile.executablePath = "openscad";
//TODO: use the nightly version, since it is the latest version?
//        runProfile.executablePath = "openscad-nightly";

        runProfile.path = getRootTargetPath() + openScadSubpath;
        
        OpenScadFileFinder openScadFinder = new OpenScadFileFinder();
        Path inpath = FileSystems.getDefault().getPath(runProfile.path);

        runProfile.openscadPaths = openScadFinder.getFiles(inpath);
        runProfile.redirectOpenscad = false;
        
        DataSetValidator inputValidator = new DataSetValidator();
        List<String> missingPngs = inputValidator.validate(runProfile.openscadPaths);
        testService.printValidationResults(missingPngs);

        if (!missingPngs.isEmpty())
        {   
            String message = "The test suite will not continue with missing baseline PNG images.";
            
            System.err.println();
            System.err.println(message);

            throw new Exception(message);
        }

        int count = testService.generateProposedBaselines(runProfile);

        // check if the count is less than 0
        if(count < 0)
        {
            String message = "There was an error with proposed baseline generation.";
            
            throw new Exception(message);
        }        
    }
    
    /**
     * This dataset provider returns all files tested
     * 
     * @return
     * @throws Exception 
     */
    @DataProvider(name="errorFiles")
    public Object[][] getOpenScadFiles() throws Exception
    {
        ImageComparisonResult compareResults = testService.compareImages(runProfile);

        testService.printHighLevelErrorReport(runProfile, compareResults.errorFiles);
        testService.saveErrorPngFilenames(compareResults.errorFiles);
        
        int parameterCount = 2;
        
        List<Object []> errorRows = compareResults.errorFiles.stream()
                .sorted()
                .map( l -> 
                {
                    Object [] array = new Object[parameterCount];
                    
                    boolean passed = false;
                    
                    array[0] = passed;
                    array[1] = l;
                    
                    return array;
                }).collect(Collectors.toList());
        
        List<Object []> successRows = compareResults.successFiles.stream()
                .sorted()
                .map( s ->
                {
                    boolean passed = true;
                    
                    Object [] array = new Object[parameterCount];
                    
                    array[0] = passed;
                    array[1] = s;
                    
                    return array;
                    
                }).collect(Collectors.toList() );
        
        int rowCount = errorRows.size() + successRows.size();
        Object[][] data = new Object[rowCount][parameterCount];        
        
        int r = 0;        
        for(Object [] row : errorRows)
        {
            data[r] = row;
            
            r++;
        }
        
        for(Object [] row : successRows)
        {
            data[r] = row;
            
            r++;
        }
        
        return data;
    }
    
    protected String getRootTargetPath()
    {
        return "src/main/openscad/";
    }

    @Test(dataProvider="errorFiles", groups = {"openscad-test-suite"})
    /**
     * 
     */
    public void reportErrors(boolean passed, String comparisonFile) throws Exception
    {
        if(passed)
        {
            // do nothing
        }
        else
        {
            String message = "The comparison failed for: " + comparisonFile;

            throw new Exception(message);
        }
    }
}
