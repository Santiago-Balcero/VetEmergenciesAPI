package tc_sb_35_api.tc_sb_35_api.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.model.Subscription;

@Slf4j
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    // This batch process gets all active subscriptions and checks if each
    // subscription has expired
    // If subscription has expired it deactivates it

    @Autowired
    DataSource dataSource;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobCompletionNotificationListener listener;

    @Autowired
    JobExplorer jobExplorer;

    // Method set to be executed every day at 00 hours to check for expired
    // subscriptions
    // @Scheduled(fixedRate = 60000) for each 60 seconds for test and demo 
    // purposes, enough to manually set expired accounts as active in DB
    // @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 0 * * ?")
    public void launchJob() throws Exception {
        log.info("***** INTERNAL PROCESS: check for expired subscriptions job starting");
        // jobExplorer gets next job parameters for a new execution of job
        JobParametersBuilder params = new JobParametersBuilder(jobExplorer);
        params.getNextJobParameters(checkForExpiredSubscriptionsJob());
        JobExecution jobExecution = jobLauncher.run(checkForExpiredSubscriptionsJob(), params.toJobParameters());
        log.info("***** INTERNAL PROCESS: check for expired subscriptions job finished with status "
                + jobExecution.getStatus());
    }

    // StepScope annotation is for creating a new instance of this reader for each
    // step execution
    @Bean
    @StepScope
    public JdbcCursorItemReader<Subscription> reader() {
        return new JdbcCursorItemReaderBuilder<Subscription>()
                .dataSource(this.dataSource)
                .name("subscriptionReader")
                // Query to get active subscriptions for evaluation
                .sql("SELECT id_subscription, finish_date, is_active, last_update_date, last_update_action FROM subscription WHERE is_active = 1")
                .rowMapper(new SubscriptionRowMapper())
                .build();
    }

    @Bean
    public ExpiredSubscriptionProcessor processor() {
        return new ExpiredSubscriptionProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Subscription> writer(DataSource datasource) {
        return new JdbcBatchItemWriterBuilder<Subscription>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("UPDATE subscription SET is_active = :isActive, last_update_date = :lastUpdateDate, last_update_action = :lastUpdateAction WHERE id_subscription = :idSubscription")
                .dataSource(datasource)
                .build();
    }

    @Bean
    public Job checkForExpiredSubscriptionsJob() {
        return jobBuilderFactory.get("checkForExpiredSubscriptionsJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .listener(listener)
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Subscription, Subscription>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer(dataSource))
                .allowStartIfComplete(true)
                .build();
    }

}
