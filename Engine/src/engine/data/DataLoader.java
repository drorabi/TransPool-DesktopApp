package engine.data;

import engine.converted.classes.Transpool;
import engine.exceptions.*;
import engine.schema.generated.TransPool;
import engine.ui.Engine;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class DataLoader extends Task<Boolean> {
    private static String XML_PATH;
    private TransPool data;
    private final static String JAXB_XML_PACKAGE_NAME = "engine.schema.generated";
    private String massage="";
    private boolean fileUploadSuccessfully=false;


    public DataLoader(String path) {
        XML_PATH = path;
    }

    public void loadXML() throws NotXmlFile, FileDoesNotExist, JAXBException {
        File dataFile = new File(XML_PATH);
        String ext = getFileExtension(dataFile);
        if (ext.equals("xml")) {
            if (dataFile.isFile()){
                    data = deserializeFrom(dataFile);
            } else {
                throw new FileDoesNotExist();
            }
        } else {
            throw new NotXmlFile();
        }
    }

    private static TransPool deserializeFrom(File in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (TransPool) u.unmarshal(in);
    }

    public void load() throws InvalidMap, InvalidPathDepatureDestination, InvalidRoute,
            InvalidStationsNames, InvalidStationsLocation,
            InvalidStationsCoordinates, InvalidPathNames, NotXmlFile, FileDoesNotExist, JAXBException,
            InvalidRideStartDay, InvalidRideStartHour, InvalidRouteThroughTheStationTwice, InvalidRideStartMinutes, NameExsitInSystem {
        loadXML();
        convert();
    }


    private void convert() throws InvalidPathNames, InvalidMap, InvalidPathDepatureDestination, InvalidStationsNames,
            InvalidStationsCoordinates, InvalidRoute, InvalidStationsLocation, InvalidRideStartDay, InvalidRideStartHour, InvalidRouteThroughTheStationTwice, InvalidRideStartMinutes, NameExsitInSystem {
        Transpool transpool = new Transpool(data);
        Engine.data = transpool;
    }

    String getFileExtension(File file) {
        if (file == null) {
            return "";
        }
        String name = file.getName();
        int i = name.lastIndexOf('.');
        String ext = i > 0 ? name.substring(i + 1) : "";
        return ext;
    }

    @Override
    public Boolean call()  {
       try {
           for(int i=0;i<=100;i++) {
               Thread.sleep(5);
               updateProgress(i*0.01,1);
           }
           load();
           fileUploadSuccessfully=true;
       }catch (InvalidMap | InvalidPathDepatureDestination | NameExsitInSystem | InvalidRoute | InvalidStationsNames | InvalidStationsLocation | InvalidStationsCoordinates | InvalidPathNames | NotXmlFile | FileDoesNotExist | JAXBException | InvalidRideStartDay | InvalidRideStartHour | InvalidRouteThroughTheStationTwice | InvalidRideStartMinutes | InterruptedException e){
           setMassage(e.getMessage());
       }
        return true;
    }

    private void setMassage(String e){
        massage=e;
    }

    public String getMassage() {
        return massage;
    }

    public boolean IsFileUploadSuccessfully(){
        return fileUploadSuccessfully;
    }
}
