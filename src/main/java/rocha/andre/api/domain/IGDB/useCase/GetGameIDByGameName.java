package rocha.andre.api.domain.IGDB.useCase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.IGDB.DTO.GameReturnDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetGameIDByGameName {
    @Value("${igdb.api.url.games}")
    private String apiUrl;

    @Value("${igdb.api.authorization}")
    private String authorization;

    @Value("${igdb.api.client-id}")
    private String clientId;

    public ArrayList<GameReturnDTO> getGameId(String gameName) {
        try {
            var url = new URL(apiUrl);

            // Abrindo a conexão
            var connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Headers
            connection.setRequestProperty("Authorization", authorization);
            connection.setRequestProperty("Client-ID", clientId);
            connection.setRequestProperty("Content-Type", "application/json");

            // Permitindo a escrita no corpo da requisição
            connection.setDoOutput(true);

            // Definindo o corpo da requisição
            var encodedGameName = URLEncoder.encode(gameName, "UTF-8");
            var requestBody = "search \"" + encodedGameName + "\"; fields id,name,summary,url;";
            connection.getOutputStream().write(requestBody.getBytes("UTF-8"));

            // Obtendo a resposta
            int responseCode = connection.getResponseCode();

            // Lendo a resposta
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            // Transformando em um json de string
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Resposta da API: " + response.toString());

            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.toString(), new TypeReference<ArrayList<GameReturnDTO>>() {});

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Aconteceu um problema no processo de receber a resposta do Cover de um jogo pela API do IGDB.");
        }
    }

}
