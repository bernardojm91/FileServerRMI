package cliente;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Scanner;

import Interface.FileInterface;

public class FileClient
{
	
	public static void main(String argv[]) throws MalformedURLException, RemoteException, NotBoundException 
	{
	
		if(argv.length != 2) 
		{
			
			System.out.println("Usage: java FileClient fileName machineName");
			
			System.exit(0);
		
		}
		//donde me piden el puerto del lado del cliente??
		String name = "//" + argv[1] + "/FileServer";
		FileInterface fi = (FileInterface) Naming.lookup(name);
		final Scanner entradaOpc = new Scanner(System.in);
 
		int opc = 0;
		while(true)
		{
		System.out.print("\t\tQue desea hacer?\n1)Archivos disponibles en Servidor\n2)Descargar Archivo\n3)Subir Archivo\n4)Archivos disponibles en Localmente\n6)Salir\n");
		opc= entradaOpc.nextInt();
		
		
			switch ( opc ) 
			{
		      case 1:
		    	  
		           System.out.print(fi.ShowFiles());
		           break;
		      case 2:
		    	  	
		    	  	Scanner entradaNombreArchD = new Scanner(System.in);
			    	try 
			  		{
			    		
			    		System.out.print("ingrese el nombre del archivo a descargar\n");
			    		
			  			String filename = entradaNombreArchD.nextLine();
			  			// llamada remota 
			  			byte[] filedata = fi.downloadFile(filename);
			  			//crea nuevo file
			  			
			  			//escribe los bytes en el file nuevo
			  			if(filedata==null)
			  			{
			  				
			  				System.out.print("ERROR:\nEl archivo \""+filename+"\" NO se logro descargar\nCompuebe que este disponible en el servidor\n");
			  				break;
			  			}
			  			File file = new File(filename);
			  			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getName()));
			  			output.write(filedata,0,filedata.length);

			  			output.flush();
			  			
			  			output.close();
			  			
			  			System.out.print("El archivo \""+filename+"\" se ha descargado correctamente\n");
			  		} 
			  		catch(Exception e) 
			  		{
			  		
			  			System.err.println("FileServer exception: "+ e.getMessage());
			  			
			  			e.printStackTrace();
			  		
			  		}
			    	//entradaNombreArchD.close();
			    	break;
		    	  
		           
		      case 3:
		    	  	Scanner entradaNombreArchS = new Scanner(System.in);
		    	  	System.out.print("ingrese el nombre del archivo a subir\n");
		    	  	String fileNameUp = entradaNombreArchS.nextLine();
		    	  	try 
			  		{
		    	  		String respuesta = null;
			  			File file = new File(fileNameUp);
			  			
			  			byte buffer[] = new byte[(int)file.length()];
			  			
			  			BufferedInputStream input = new
			  			
			  			BufferedInputStream(new FileInputStream(fileNameUp));
			  			
			  			input.read(buffer,0,buffer.length);
			  			
			  			input.close();
			  			
			  			respuesta = fi.uploadFile(buffer,fileNameUp);
			  			System.out.print(respuesta);
			  		
			  		} 
		    	  	catch(Exception e)
			  		{
			  		
			  			System.out.println("FileImpl: "+e.getMessage());
			  			
			  			e.printStackTrace();
			  		}
		    	  	//entradaNombreArchS.close();
		    	  	break;
			          
		      case 4:
		    	  System.out.println(ShowFiles());
		    	  break;
		    	  
		      case 5:
		      case 6:
		    	  entradaOpc.close();
		    	  System.out.println("Cliente Off");
		    	  System.exit(0);
		    
		    
		           
		      default:
		           System.out.println("Opcion invalida" );
		           break;
		          
		          
		    }
		}
		
	} 

	public static String ShowFiles()
	{
		String ret = "";
		File dir = new File(".");
		File[] filesList = dir.listFiles();
		for (File file : filesList) 
		{
		    if (file.isFile() && !(file.getName().equals(".")) && !(file.getName().equals("..")) ) 
		    {
		       ret = ret + file.getName()+"\n";
		    }
		}
		return(ret);
	}
}