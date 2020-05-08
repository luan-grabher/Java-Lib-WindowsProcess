package WindowsProcess;


import java.io.InputStream;
import java.util.Scanner;

public class WindowsProcess
{
    /**
     * Procura se o processo está ativo na memória.
     * http://stackoverflow.com/a/19005828/3764804
     * @param processName Nome do processo para verificar se está ativo.
     * @return Retorna se está ativo ou não.
     */
    public static boolean processRunning(String processName)          
    {
        return processRunning(processName, false);
    }
    
    /**
     * Procura o processo na memória e remove.
     * @param processName Nome do processo para verificar se está ativo.
     * @param kill Se deseja remover o processo da memória ou não.
     * @return Retorna se removeu ou não.
     */
    public static boolean processRunning(String processName, boolean kill) 
    {
        boolean bool = false;
        try
        {
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
            Process process = processBuilder.start();
            String taskList = streamToString(process.getInputStream());
            System.out.println("Searching process...");
            if(taskList.contains(processName)) {
                bool = true;
                System.out.println("Removing process...");
                if (kill == true) {                 
                    Runtime.getRuntime().exec("taskkill /F /IM " + processName);
                    System.out.println("Process removed!");
                }
            }
        } catch (Exception e)
        {
            System.out.println("Is not possible verify the process because happened an error: ");
            e.printStackTrace();
        }
        return bool;
    }
    
    // http://stackoverflow.com/a/5445161/3764804
    private static String streamToString(InputStream input)
    {
        Scanner scanner = new Scanner(input, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        return string;
    }
}
