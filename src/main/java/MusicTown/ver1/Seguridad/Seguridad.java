 
package MusicTown.ver1.Seguridad;

import MusicTown.ver1.servicio.UsuarioServicios;
import MusicTown.ver1.util.NotificacionesExitosas;
import MusicTown.ver1.util.NotificacionesLogout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicios usuarioServicio;
    
    @Autowired
    private NotificacionesExitosas notisExitosas;
    
    @Autowired
    private NotificacionesLogout notisLogout;

    @Autowired
    public void globalConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//Con esto hago que se desactive la configuracion por defecto
                .authorizeRequests().antMatchers("css/**", "js/**", "assets/**").permitAll() //recursos Statics
                .antMatchers("/", "/inicio", "/iniciarSesion", "producto/catalogo" , "/registrar").permitAll()//htmls (Direcciones url) permitidas para todos
                .antMatchers("/admin/**").hasRole("ADMIN")// Direcciones url con acceso restringido
                .antMatchers("/Usuario/**").hasRole("USUARIOREGISTRADO")
                .and()
                .formLogin()
                .loginPage("/iniciarSesion")
                .successHandler(notisExitosas) 
                .loginProcessingUrl("/logincheck")
                .usernameParameter("mail")
                .passwordParameter("clave")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(notisLogout)
                .logoutUrl("/logout");

    }
}
