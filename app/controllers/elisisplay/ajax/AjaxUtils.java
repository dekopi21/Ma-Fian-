package controllers.elisisplay.ajax;

import controllers.elisisplay.lang.StringUtils;
import play.mvc.Controller;

import java.util.Map;

/**
 * Some ajax rendering utils.
 */
@SuppressWarnings("Duplicates")
public class AjaxUtils extends Controller{

    /**
     * Render an ajax object.
     * In his actual state this method has no purpose,
     * apart uniformize the use of AjaxUtils to do Ajax actions,
     * and to maintain this use in the future version.
     * @param object Object to render.
     * @param format Format of rendering.
     */
    public static void renderAjax(Object object, String format) {
        switch (format.toLowerCase()) {
            case "html":
                renderHtml(object);
                break;
            case "text":
            case "txt":
                renderText(object);
                break;
            case "json":
                renderJSON(object);
                break;
            case "xml":
                renderXml(object);
                break;
            default:
                renderJSON(object);
        }
    }
    /**
     * Render an ajax object.
     * In his actual state this method has no purpose,
     * apart uniformize the use of AjaxUtils to do Ajax actions,
     * and to maintain this use in the future version.
     * @param object Object to render.
     * @param format Format of rendering.
     */

    public static void renderAjax(Object object, AjaxFormat format) {
        switch (format) {
            case HTML:
                renderHtml(object);
                break;
            case TEXT:
                renderText(object);
                break;
            case JSON:
                renderJSON(object);
                break;
            case XML:
                renderXml(object);
                break;
        }
    }


    /**
     * Render an ajax object.
     * In his actual state this method has no purpose,
     * apart uniformize the use of AjaxUtils to do Ajax actions,
     * and to maintain this use in the future version.
     * @param format Format of rendering.
     * @param s String to render.
     */
    public static void renderAjax(String s, String format) {
        switch (format.toLowerCase()) {
            case "html":
                renderHtml(s);
                break;
            case "text":
            case "txt":
                renderText(s);
                break;
            case "json":
                renderJSON(s);
                break;
            case "xml":
                renderXml(s);
                break;
            default:
                renderJSON(s);
        }
    }

    /**
     * Returns a a object include in a map.
     * @param obj The map
     * @param format Format
     * @param key The key
     */
    public static void renderAjax(Map<String, Object> obj, String format, String key) {

        //This key exists really in the map.
        if (StringUtils.isEmpty(key) && obj.keySet().contains(key))
            return;

        renderAjax(obj.get(key), format);
    }
}
