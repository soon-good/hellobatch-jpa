package io.study.batch.hellobatch_jpa.config.rabbitmq;

import lombok.Getter;

public class BookBrokerConstants {

    @Getter
    public enum ChannelType{
        SIMPLE_BROADCAST(1, "BOOK_SIMPLE_EXCHANGE_1"){};

        private final int channelTypeCd;
        private final String exchangeName;

        ChannelType(int channelTypeCd, String exchangeName){
            this.channelTypeCd = channelTypeCd;
            this.exchangeName = exchangeName;
        }
    }
}
