package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ipartek.formacion.controller.controller.HabilidadController;
import com.ipartek.formacion.model.pojo.Usuario;

public class UsuarioDAO {

	private final static Logger LOG = LogManager.getLogger(UsuarioDAO.class);


	private final String SQL_LOGIN = "SELECT u.id, u.nombre, u.password FROM usuarios u WHERE u.nombre = ? AND u.password = ?;";

	private static UsuarioDAO INSTANCE = null;

	private UsuarioDAO() {
		super();
	}

	public static synchronized UsuarioDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}
		return INSTANCE;
	}


	public Usuario login(Usuario usuario) {
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_LOGIN)) {
			pst.setString(1, usuario.getNombre());
			pst.setString(2, usuario.getPassword());
			LOG.trace(pst);
			try(ResultSet rs = pst.executeQuery()) {
				if(rs.next()) {
					usuario.setId(rs.getInt("id"));
				} else {
					usuario = null;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return usuario;
	}
}
