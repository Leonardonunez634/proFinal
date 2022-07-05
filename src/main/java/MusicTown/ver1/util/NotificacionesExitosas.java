
package MusicTown.ver1.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;


@Component
public class NotificacionesExitosas extends SimpleUrlAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
            HttpServletResponse response, 
            Authentication authentication) throws IOException, ServletException {
        
        SessionFlashMapManager flashMang = new SessionFlashMapManager();
        FlashMap fMap = new FlashMap();
        
        fMap.put("exito", "Has ingresado con exito"); /*Inserto mi mensaje*/
        flashMang.saveOutputFlashMap(fMap, request, response); /*Con esto lo inserto en el super*/
        
        super.onAuthenticationSuccess(request, response, authentication); //To change body of generated methods, choose Tools | Templates.
    }
 
}
