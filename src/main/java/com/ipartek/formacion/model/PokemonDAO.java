package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ipartek.formacion.model.pojo.Habilidad;
import com.ipartek.formacion.model.pojo.Pokemon;

public class PokemonDAO implements IDAO<Pokemon> {

	private static PokemonDAO INSTANCE;

	private final static org.apache.log4j.Logger LOG = LogManager.getLogger(PokemonDAO.class);


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

		Map<Integer, Pokemon> mapPokemons = new HashMap<Integer, Pokemon>();

		ArrayList<Pokemon> registros = new ArrayList<Pokemon>();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery() ) {

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


		} catch (Exception e) {
			// TODO: LOG
			e.printStackTrace();
		}

		return new ArrayList<Pokemon>(mapPokemons.values());
	}

	@Override
	public Pokemon getById(int id) {
		LOG.trace("not implemented yet");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pokemon delete(int id) throws Exception {
		LOG.trace("not implemented yet");
		// TODO Auto-generated method stub
		return null;
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

}
