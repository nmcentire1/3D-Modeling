
package org.onebeartoe.modeling.openscad.test.suite.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.onebeartoe.modeling.openscad.test.suite.OpenScadCameraDirections;
import org.onebeartoe.modeling.openscad.test.suite.RunProfile;

public class PngGenerator
{
    private Logger logger;
    
    public PngGenerator()
    {
        String name = getClass().getName();
        logger = Logger.getLogger(name);
    }
    
//TODO: Rename this method to generateOne()    
    /**
     * Here is the refactor for driving whether the STD IOE of the openscad executable 
     * gets printed like regular or redirected to a file. 
     */
    private int generate(String commandList, RunProfile runProfile) throws IOException, InterruptedException
    {
        String [] strs = commandList.split("\\s+");
        
        List <String> command = Arrays.asList(strs);
        
        ProcessBuilder builder = new ProcessBuilder(command);
        Process jobProcess = builder.start();                
        int waitValue = jobProcess.waitFor();
        
        InputStream is = jobProcess.getInputStream();
        String stdout = new BufferedReader( new InputStreamReader(is))
                              .lines()
                              .collect(Collectors.joining("\n"));
                
        InputStream es = jobProcess.getErrorStream();
        String stderr = new BufferedReader( new InputStreamReader(es))
                              .lines()
                              .collect(Collectors.joining("\n"));

        if(runProfile.redirectOpenscad)
        {
//            System.out.println("redirection");
            //TODO: actually redirect the openscad output to a file
        }
        else
        {
            StringBuilder sb = new StringBuilder();
        
            // standard error
            sb.append( stderr.trim() );

            // standard out
            sb.append( stdout.trim() );

            System.out.println(commandList);
            System.out.println(sb.toString());
            System.out.println();
        }
        
        return waitValue;
    }
    
    public List<Boolean> generateDirectionalPngs(Path oscadInputFile, 
                                                 boolean forcePngGeneration,
                                                 RunProfile runProfile,
                                                 DirectoryProfile directoryProfile)
    {
    	List<Boolean> exitCodes = new ArrayList();

        Stream.of( OpenScadCameraDirections.values() )
                .parallel()
                .forEach((v) -> 
                {
		    try 
		    {
                        boolean exitCode = generateOneDirectionalPng(oscadInputFile, 
                                                                     forcePngGeneration, 
                                                                     v, 
                                                                     runProfile,
                                                                     directoryProfile);
                        
                        
                        exitCodes.add(exitCode);
		    } 
		    catch (Exception e)
		    {
		    	e.printStackTrace();
		    }		    
                });
        
        return exitCodes;
    }

    /**
     * This method calls the OpenSCAD executable to create a snapshot of a rendered .scad file.
     * 
     * Here is a sample invocation:
     * 
     *	$ /cygdrive/c/opt/openscad/openscad-2015.03-2/openscad -o out.png --camera=0,0,0,0,0,0,350 space-invaders-scenes-CUSTOMIZER.scad
     * 
     */
    public boolean generateOneDirectionalPng(Path oscadInputFile, 
                                             boolean forceGeneration, 
                                             OpenScadCameraDirections direction,
                                             RunProfile runProfile,
                                             DirectoryProfile directoryProfile) throws IOException, InterruptedException
    {
        String outfileName = DataSetValidator.baselineNameFor(oscadInputFile, forceGeneration, direction);

        File outfile = new File(outfileName);

        // assume a failed (non-zero) value for the return status
        int exitCode = -1;
        
        if(forceGeneration == false)
        {
            // set the return code to succuss when the PNG genration is not forced (no-opp)
            exitCode = 0;
        }

        // test to see if the proposed baseline PNG file exists OR if the PNG is to 
        // be generated regardless of the file existing or not (generate-proposed-pngs.sh )
        if (!outfile.exists() || forceGeneration)
        {
            String infilePath = oscadInputFile.toString().replace("\\", "/");
            if (infilePath.startsWith("./"))
            {
                // remove the 'current directory' notation from the start of the path
                infilePath = infilePath.substring(2);
            }

            // The OpenSCAD executable --camera option takes these parameters: translatex,y,z,rotx,y,z,dist
            int distance = 250;
            String rotateParams = direction.getRotateParams().replaceAll(" ", "");
            
            boolean viewall = directoryProfile.viewall();
            
            // The --viewall parameter is used to make sure the entire model is in view.
            String command = runProfile.executablePath
                    + " -o " + outfileName + " " 
                    + "--camera=0,0,0," + rotateParams + "," + distance + " "
                    + (viewall ? "--viewall" + " " : "")
                    + infilePath;
            
            exitCode = generate(command, runProfile);
        }

        // an exit code of 0 is expected for successful execution of a system command 
        return exitCode == 0;
    }

    public boolean generatePngs(boolean forcePngGeneration,
                                RunProfile runProfile) throws IOException,
                                                              InterruptedException
    {
        List<Boolean> exitCodes = new ArrayList();

        runProfile.openscadPaths.stream().parallel().forEach((path) -> 
        {
            DirectoryProfile directoryProfile = new DirectoryProfile();
            Path parent = path.getParent();
            directoryProfile.setPath(parent);
            try
            {
                loadDirectoryProperties(directoryProfile);
            } 
            catch (IOException ex)
            {   
                logger.warning("could not load directory properties for " + parent.toFile().getAbsolutePath() );                             
            }
            
            List<Boolean> directionalExitCodes = generateDirectionalPngs(path, 
                                                                         forcePngGeneration,
                                                                         runProfile,
                                                                         directoryProfile);
            
            exitCodes.addAll(directionalExitCodes);
            
            if( directionalExitCodes.contains(false) )
            {
                System.err.println("An error occured with proposed baseline PNG:");
                System.err.println(path.toString() );
            }
        });

        boolean masterExitCode = true;

        for (Boolean ec : exitCodes)
        {
            if (!ec)
            {
                masterExitCode = false;
                
                // break out early, on the first occurrence of a failure
                break;
            }
        }

        return masterExitCode;
    }

    /**
     * The directory properties are loaded from a file in the same directory as 
     * the .scad file.
     * 
     * It contains any custom camera options.
     * 
     * @param runProfile 
     */
    private void loadDirectoryProperties(DirectoryProfile directoryProfile) throws FileNotFoundException, IOException
    {
        Path path = directoryProfile.getPath();
        
        File parent = path.toFile();
        String propertiesFileName = "openscad.properties";
        
        File infile = new File(parent, propertiesFileName);
        
        if( infile.exists() )
        {
            Properties properties = new Properties();
            InputStream inStream = new FileInputStream(infile);
            properties.load(inStream);

            String viewallValue = properties.getProperty("viewall");

            boolean viewAll = Boolean.parseBoolean(viewallValue);

            directoryProfile.setViewall(viewAll);
        }
    }
}
