package com.sda.app.utils;



public class ApiResponse {
    private int status;
    private String message;
    private Object data;

    private ApiResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public static class Builder{
        private int status;
        private String message;
        private Object data;
        public Builder(){
        }

        public Builder status(int status){
            this.status = status;
            return this;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder data(Object data){
            this.data = data;
            return this;
        }

        public ApiResponse build(){
            return new ApiResponse(this);
        }
    }


}
