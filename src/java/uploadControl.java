import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.importing.pnml.PnmlImport;
import petrinet.PetriNetUtil;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win 8.1
 */

@ManagedBean
@SessionScoped
public class uploadControl {
 UploadedFile file1;
 UploadedFile file2;
 PetriNet pn1,pn2;
 File tempFile1, tempFile2;
 private ArrayList<File> model;
        
        public void setModel(ArrayList<File> model)
	{
		this.model = model;
	}
	
	public ArrayList<File> getModel()
	{
		return model;
	}
 
    public UploadedFile getFile1() {
        return file1;
    }
 
    public void setFile1(UploadedFile file1) {
        this.file1 = file1;
    }
    
    public UploadedFile getFile2() {
        return file2;
    }
 
    public void setFile2(UploadedFile file2) {
        this.file2 = file2;
    }
    
    public PetriNet getpn1() {
        return pn1;
    }
 
    public void setpn1(PetriNet pn1) {
        this.pn1 = pn1;
    }
    
    public PetriNet getpn2() {
        return pn2;
    }
 
    public void setpn2(PetriNet pn2) {
        this.pn2 = pn2;
    }
    
    public File gettempFile1() {
        return tempFile1;
    }
 
    public void settempFile1(File temFile1) {
        this.tempFile1 = temFile1;
    }
    
     public File gettempFile2() {
        return tempFile2;
    }
 
    public void settempFile2(File temFile2) {
        this.tempFile2 = temFile2;
    }
 
    public void dummyAction() throws IOException, Exception{
       
    //create an InputStream from the uploaded file
    InputStream inputStr1;
    InputStream inputStr2;
    //float similarity;
    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext(); 
        inputStr1 = file1.getInputstream();
        inputStr2 = file2.getInputstream();
        PnmlImport input = new PnmlImport();
        pn1 = input.read(inputStr1);
        pn2 = input.read(inputStr2);
        similarityCalculation sim = new similarityCalculation();
        float similarity = sim.similarityStructural(pn1, pn2, servletContext);
        if(pn1==null)
        {
            System.out.println("Sedih oooom");
        }
        else
        {
            System.out.println("Coba "+similarity);
        }
    }
    public void handleFileUpload() {
        File folder = new File("F:\\BDL");
        File[] filelist = folder.listFiles();
        model = new ArrayList<>();
            for (File file : filelist)
            {
		if (file.getAbsolutePath().endsWith(".pnml"))
            	{
                	model.add(file);
		}
            }
        FileInputStream is1 = null;
	FileInputStream is2 = null;
        PnmlImport input = new PnmlImport();
        try {
			is1 = new FileInputStream(model.get(0));
			is2 = new FileInputStream(model.get(1));		
		} catch (FileNotFoundException e) {
	}
        try {
		pn1 = input.read(is1);
		} catch (Exception e) {
		}
		try {
			pn2 = input.read(is2);
		} catch (Exception e) {
		}
        if(pn1==null)
        {
            System.out.println("Sedih oooom");
        }
        else
        {
            System.out.println("Coba"+pn1.getTransitions().get(1));
        }
    }
}
