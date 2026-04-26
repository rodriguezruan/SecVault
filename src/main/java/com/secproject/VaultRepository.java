package com.secproject;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class VaultRepository {
    private String filePath;
    private CryptoService cryptoService = new CryptoService();

    public VaultRepository(String filePath) {
        this.filePath = filePath;
    }

    public void save(List<Credential> credeciais, String password) throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(credeciais);
        String encrypted = cryptoService.encrypt(json, password);
        Files.writeString(Path.of(filePath), encrypted);
    }

    public List<Credential> load(String password) throws Exception {
        Gson gson = new Gson();
        String conteudo = Files.readString(Path.of(filePath));
        String decrypt = cryptoService.decrypt(conteudo,password);
        Type type = new TypeToken<List<Credential>>(){}.getType();
        List<Credential> list = gson.fromJson(decrypt, type);

        return list;
    }
}
