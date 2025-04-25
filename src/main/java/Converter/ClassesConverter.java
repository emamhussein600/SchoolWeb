package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;
import javax.ejb.EJB;
import com.mycompany.entities.Classes;
import com.mycompany.testapp.services.SchoolServicesLocal;

@FacesConverter(value="classesConverter", managed=true)
public class ClassesConverter implements Converter<Classes> {

    @EJB
    private SchoolServicesLocal service;

    @Override
    public Classes getAsObject(FacesContext ctx, UIComponent comp, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return service.findClassById(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent comp, Classes cls) {
        return (cls == null || cls.getClassId() == null)
             ? ""
             : cls.getClassId().toString();
    }
}