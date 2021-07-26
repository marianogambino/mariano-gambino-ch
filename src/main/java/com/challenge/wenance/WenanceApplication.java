package com.challenge.wenance;

import com.challenge.wenance.model.*;
import com.challenge.wenance.service.CurrencyAverageService;
import com.challenge.wenance.service.impl.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@EnableMongoRepositories(basePackages={"com.challenge.wenance.repository"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class WenanceApplication {


	public static void main(String[] args) {
		SpringApplication.run(WenanceApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		return restTemplate;
	}

	@Bean
	@ConditionalOnMissingBean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
				= new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient( closeableHttpClient() );
		return clientHttpRequestFactory;
	}

	@Bean
	public CloseableHttpClient closeableHttpClient(){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient;
	}

	@Bean
	public CurrencyAverageService<Btcars> btcarsCurrencyAverageService(){
		return new BtcarsAverageServiceImpl();
	}
	@Bean
	public CurrencyAverageService<Btcdai> btcdaiCurrencyAverageService(){
		return new BtcdaiAverageServiceImpl();
	}

	@Bean
	public CurrencyAverageService<Daiars> daiarsCurrencyAverageService(){
		return new DaiarsAverageServiceImpl();
	}

	@Bean
	public CurrencyAverageService<Daiusd> daiusdCurrencyAverageService(){
		return new DaiusdAverageServiceImpl();
	}

	@Bean
	public CurrencyAverageService<Ethars> etharsCurrencyAverageService(){
		return new EtharsAverageServiceImpl();
	}

	@Bean
	public CurrencyAverageService<Ethdai> ethdaiCurrencyAverageService(){
		return new EthdaiAverageServiceImpl();
	}

}
