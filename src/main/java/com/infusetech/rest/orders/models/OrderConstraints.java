package com.infusetech.rest.orders.models;

public class OrderConstraints {

    public static class Common {
        public static final int NOME_MIN_SIZE = 5;
        public static final int NOME_MAX_SIZE = 100;
        
        public static final String NOME_OUT_OF_RANGE_ERROR_MESSAGE = "O nome deverá ser limitado entre " +
            NOME_MIN_SIZE + " e " +
            NOME_MAX_SIZE + " caracteres";
    }

    public static class Create {
        // Criação de um registro de pedido
        public static final String NOME_IS_BLANK_ERROR_MESSAGE = "O nome deve ser preenchido";
        public static final String NUMERO_CONTROLE_IS_NULL_ERROR_MESSAGE = "O número de controle é obrigatório";
        public static final String CODIGO_CLIENTE_IS_NULL_ERROR_MESSAGE = "O código do cliente é obrigatório";
        public static final String VALOR_ERROR_MESSAGE = "O valor deve ser positivo";
        public static final String QUANTIDADE_ERROR_MESSAGE = "A quantidade deve ser um inteiro positivo";    

        // Criação de pedidos em lote
        public static final int LISTA_PEDIDOS_MIN_SIZE = 1;
        public static final int LISTA_PEDIDOS_MAX_SIZE = 10;
        public static final String LISTA_PEDIDOS_OUT_OF_RANGE_ERROR_MESSAGE = "A lista de pedidos deve conter entre " +
            LISTA_PEDIDOS_MIN_SIZE + " e " +
            LISTA_PEDIDOS_MAX_SIZE + " pedidos.";
    }
    
    public static class Update {
        public static final String VALOR_ERROR_MESSAGE = "Valor deve ser positivo ou zero";
        public static final String QUANTIDADE_ERROR_MESSAGE = "Quantidade deve ser um inteiro positivo ou zero";    
    }
}
