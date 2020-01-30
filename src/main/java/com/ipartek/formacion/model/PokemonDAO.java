package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ipartek.formacion.model.pojo.Habilidad;
import com.ipartek.formacion.model.pojo.Pokemon;

public class PokemonDAO implements IDAO<Pokemon> {

	private static PokemonDAO INSTANCE;

	private final static Logger LOG = LogManager.getLogger(PokemonDAO.class);


	private final String SQL_GET_BYID = "SELECT p.id 'pokemonId', p.nombre 'pokemonNombre' , h.id 'habilidadId', h.nombre 'habilidadNombre' FROM habilidad h, pokemon p, pokemon_has_habilidad phh WHERE phh.pokemonId = p.id AND phh.habilidadId = h.id AND p.id  = ? ORDER BY h.id ASC LIMIT 500;";
	private final String SQL_GET_BYNAME = "SELECT p.id 'pokemonId', p.nombre 'pokemonNombre' , h.id 'habilidadId', h.nombre 'habilidadNombre' FROM habilidad h, pokemon p, pokemon_has_habilidad phh WHERE phh.pokemonId = p.id AND phh.habilidadId = h.id AND p.nombre LIKE ? ORDER BY p.id ASC LIMIT 500;";


	private final String SQL_DELETE= "DELETE FROM pokemon WHERE id = ?;";

	private PokemonDAO() {
		super();
	}

	public static synchronized PokemonDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PokemonDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Pokemon> getAll() {

		String sql = "SELECT p.id 'pokemonId', p.nombre 'pokemonNombre' , h.id 'habilidadId', h.nombre 'habilidadNombre' FROM habilidad h, pokemon p, pokemon_has_habilidad phh WHERE phh.pokemonId = p.id AND phh.habilidadId = h.id ORDER BY p.id ASC LIMIT 500;";

		List<Pokemon> resul = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery() ) {
			resul = mapper(rs);
		} catch (Exception e) {
			// TODO: LOG
			e.printStackTrace();
		}

		return resul;
	}

	@Override
	public Pokemon getById(int id) {
		Pokemon resul = null;
		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BYID);
				) {
			pst.setInt(1, id);
			try(ResultSet rs = pst.executeQuery()) {
				if(rs.next()) {
					resul = new Pokemon();
					resul.setId(rs.getInt("pokemonId"));
					resul.setNombre(rs.getString("pokemonNombre"));

					List<Habilidad> habilidades = resul.getHabilidades();
					Habilidad habilidad;
					do {
						habilidad = new Habilidad();
						habilidad.setId(rs.getInt("habilidadId"));
						habilidad.setNombre(rs.getString("habilidadNombre"));
						habilidades.add(habilidad);
					} while (rs.next());
				}
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		// TODO Auto-generated method stub
		return resul;
	}

	@Override
	public Pokemon delete(int id) throws Exception {
		Pokemon resul = getById(id);

		try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE)) {
			pst.setInt(1, id);

			int affectedRows = pst.executeUpdate();

			if(affectedRows == 1) {
				LOG.info("Pokemon borrado correctamente de la BD");
			} else {
				LOG.info("Parece que se ha borrado " + affectedRows + " pokemons");
				resul = null;
			}

		} catch (Exception e) {
			LOG.error(e);
		}
		return resul;
	}

	@Override
	public Pokemon update(int id, Pokemon pojo) throws Exception {
		LOG.trace("not implemented yet");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pokemon create(Pokemon pojo) throws Exception {
		LOG.trace("not implemented yet");
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pokemon> getByName(String nombreP) {

		List<Pokemon> resul = null;
		try( Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BYNAME);) {

			pst.setString(1, "%" + nombreP + "%");

			try( ResultSet rs = pst.executeQuery();) {
				resul = mapper(rs);
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return resul;
	}

	private List<Pokemon> mapper( ResultSet rs) throws SQLException {

		Map<Integer, Pokemon> mapPokemons = new HashMap<Integer, Pokemon>();

		while( rs.next() ) {
			// TODO mapper
			int id = rs.getInt("pokemonId");
			String nombre = rs.getString("pokemonNombre");
			LOG.trace(id + " " + nombre);

			Pokemon p;
			if(mapPokemons.containsKey(id)) {
				// Como el pokemon esta, lo obtenemos y le añadimos la habilidad
				p = mapPokemons.get(id);
			} else {
				// Si el pokemon no esta, lo crea
				p = new Pokemon();
				p.setId(id);
				p.setNombre(nombre);

				mapPokemons.put(id, p);
			}

			Habilidad habilidad = new Habilidad();
			habilidad.setId(rs.getInt("habilidadId"));
			habilidad.setNombre(rs.getString("habilidadNombre"));

			p.getHabilidades().add(habilidad);

		}

		return new ArrayList<Pokemon>(mapPokemons.values());

	}

}
