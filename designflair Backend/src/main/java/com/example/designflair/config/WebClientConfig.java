package com.example.designflair.config;

import com.example.designflair.Constants;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutException;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.dns.DnsNameResolverTimeoutException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
    @Bean
    HttpClient designHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Constants.INT_10000)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(Constants.INT_10000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(Constants.INT_10000, TimeUnit.MILLISECONDS)));

    }

    @Bean
    HttpClient esrganHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Constants.INT_10000)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(Constants.INT_10000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(Constants.INT_10000, TimeUnit.MILLISECONDS)));

    }

    @Bean
    public ExchangeStrategies esrganExchangeStrategy(){
        long totalMemory = Runtime.getRuntime().maxMemory();
        return ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize((int) totalMemory)
                )
                .build();
    }

    @Bean
    public WebClient.Builder esrganClientBuilder() {
        return WebClient.builder()
                .baseUrl(Constants.LOCALHOST + ":" + Constants.ESRGAN_PORT)
                .exchangeStrategies(esrganExchangeStrategy())
                .clientConnector(new ReactorClientHttpConnector(esrganHttpClient()));
    }




    @Bean
    public WebClient.Builder designClientBuilder() throws ReadTimeoutException, WriteTimeoutException, DnsNameResolverTimeoutException {
        return WebClient.builder()
                .baseUrl(Constants.HUGGING_FACE_BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(designHttpClient()));
    }

}
