package com.ipartek.formacion.controller.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ipartek.formacion.model.pojo.Pokemon;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static Logger LOG = Logger.getLogger(LoginController.class);

	private Usuario[] usuarios = {
	                              new Usuario(1, "admin", "admin")
	                              };

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if(session.getAttribute("usuario") != null) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recibir el objeto de la peticion.
		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		Usuario usuarioRecibido = null;
		usuarioRecibido = gson.fromJson(reader, Usuario.class);

		// Probar que esta en la BD.
		//TODO: Conectarse a la BD.

		boolean loginCorrecto = false;
		Usuario usuario = null;
		for (Usuario u : usuarios) {
			if(u.getNombre().equals(usuarioRecibido.getNombre()) && u.getPassword().equals(usuarioRecibido.getPassword())) {
				loginCorrecto = true;
				usuario = u;
				break;
			}
		}

		if(loginCorrecto) {
			// Si el login es correcto creamos la sesion y respondemos con codigo 200

			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuario);
			session.setMaxInactiveInterval(600);

			response.setStatus(HttpServletResponse.SC_OK);


		} else {
			// Si el login no es correcto pues tendremos que responder con el codigo 401
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

}
