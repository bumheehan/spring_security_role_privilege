package xyz.bumbing.api.controller.dto;

import lombok.Data;

public class UserResponse {

    @Data
    public static class V1 {
        private String name;

        public V1(String name) {
            this.name = name;
        }

    }

}
