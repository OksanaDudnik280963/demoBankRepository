package com.example.demoBankRepository.utils;

import com.example.demoBankRepository.dto.AccountRequest;
import com.example.demoBankRepository.entity.Account;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
@Slf4j
public class JsonUtils {
    private JsonUtils() {
    }

    public static final Gson gson = new Gson();

    public static String toJson(Account entity){
        return gson.toJson(entity);
    }
    public static String toJson(AccountRequest entity){
        return gson.toJson(entity);
    }

    public static String toJson(List<Account> entities){
        for (Account user : entities) {
            if (log.isDebugEnabled())
                log.info(toJson(user), "\n\r");
        }
        return gson.toJson(entities);
    }

    public static Account fromJson(String json) {
        return gson.fromJson(json, Account.class);
    }
}
