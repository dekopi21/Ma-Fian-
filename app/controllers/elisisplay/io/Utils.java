package controllers.elisisplay.io;

import org.apache.commons.io.FileUtils;
import play.Play;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Bienvenu on 01/09/2018 in elisisplay.
 */
public class Utils {


    public static final String cantMkDir = "Can't create the directory.";
    private static final String defaultPictureName = "PICTURE";

    /**
     * Permet d'enregistrer une image.
     * @param image Le fichier Image à enregistrer.
     * @param pathToSave Chemin oû enregistrer l'image (chemin relatif partant de la racine
     *                   du projet Play).
     * @return "" When all has been done fine.
     * "1" : For IOException
     * "-1" : For other exception.
     * "2" : Can't access and create the path.
     */
    public static String savePic(File image, String pathToSave) {

        return savePicture(image, pathToSave, defaultPictureName);

    }

    /**
     * Permet d'enregistrer une image.
     * @param picture Le fichier Image à enregistrer.
     * @param pathToSave Chemin oû enregistrer l'image (chamin relatif partant de la racine
     *                   du projet Play).
     * @return "" When all has been done fine.
     * "1" : For IOException
     * "-1" : For other exception.
     * "2" : Can't access and create the path.
     */
    public static String savePicture(File picture, String pathToSave, String picName) {

        File dirAbsolutePath = new File(Play.applicationPath.getAbsolutePath(), pathToSave);

        //Tentes de créer le dossier de sauvegarde s'il n'existe pas.
        if (!dirAbsolutePath.exists() && !dirAbsolutePath.mkdirs()) {
            return cantMkDir;
        }


        try {

            File absolutePath;
            do {
                //Obtenir le nom définitif du fichier.
                absolutePath = new File(dirAbsolutePath, getFileNameComplete(picture, picName));
            }while (!absolutePath.exists());
            FileUtils.moveFile(picture, absolutePath);
            return pathToSave + "/" + absolutePath.getName();
        } catch (IOException ioe) {
            return "1";
        } catch (Exception e) {
            return "-1";
        }
    }

    public static String getFileNameComplete(File file, String picName) {
        return codeFileName(picName) + "." + getFileExtension(file);
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public static String codeFileName(String picName) {
        //TODO MAke it really random.
        return picName + new SimpleDateFormat("YddMM_hhmmss").format(new Date()) +
                "_" + new Random().nextInt();
    }
}
