package org.partners.foodpackhub.data;

import javax.sql.DataSource;

import org.partners.foodpackhub.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class Batchconfiguration {
	
	private final String[] CSV_FIELD_NAMES = new String[] {"productId","productName","productType","price"};
	
	@Bean
	public FlatFileItemReader<ProductInput> reader() {
	  return new FlatFileItemReaderBuilder<ProductInput>()
	    .name("productItemReader")
	    .resource(new ClassPathResource("products-data.csv"))
	    .delimited()
	    .names(CSV_FIELD_NAMES)
	    .fieldSetMapper(new BeanWrapperFieldSetMapper<ProductInput>() {{
	      setTargetType(ProductInput.class);
	    }})
	    .build();
	}

	@Bean
	public ProductItemProcessor processor() {
	  return new ProductItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Product> writer(DataSource dataSource) {
	  return new JdbcBatchItemWriterBuilder<Product>()
	    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	    .sql("INSERT INTO product (product_id, product_name, product_type, price) VALUES (:productId, :productName, :productType, :price)")
	    .dataSource(dataSource)
	    .build();
	}
	
	@Bean
	public Job importUserJob(JobRepository jobRepository,
	    JobCompletionNotificationListener listener, Step step1) {
	  return new JobBuilder("productDataCsvToH2InMemoryDatabase", jobRepository)
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .flow(step1)
	    .end()
	    .build();
	}

	@Bean
	public Step step1(JobRepository jobRepository,
	    PlatformTransactionManager transactionManager, JdbcBatchItemWriter<Product> writer) {
	  return new StepBuilder("processingStep", jobRepository)
	    .<ProductInput, Product> chunk(10, transactionManager)
	    .reader(reader())
	    .processor(processor())
	    .writer(writer)
	    .build();
	}
}
