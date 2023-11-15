package enyi.kata2.Sqlite;

import enyi.kata2.Pokemon;
import enyi.kata2.PokemonLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlitePokemonLoader implements PokemonLoader {
    private final Connection connection;
    private final String SQL = "select * from nombre_de_la_tabla";

    public SqlitePokemonLoader(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Pokemon> loadAll() {
        try {
            return load(queryAll());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private List<Pokemon> load(ResultSet resultSet) throws SQLException {
        List<Pokemon> result = new ArrayList<>();
        while(resultSet.next())
            result.add(pokemonFrom(resultSet));
        return result;
    }

    private Pokemon pokemonFrom(ResultSet resultSet) throws SQLException {
        return new Pokemon(
                resultSet.getString("n"),
                resultSet.getString("Name"),
                resultSet.getString("Type1")
        );
    }

    private ResultSet queryAll() throws SQLException {
        return connection.createStatement().executeQuery(SQL);
    }
}
