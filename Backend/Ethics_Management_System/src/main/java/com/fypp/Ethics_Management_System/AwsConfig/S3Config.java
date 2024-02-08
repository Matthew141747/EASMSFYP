package com.fypp.Ethics_Management_System.AwsConfig;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    ProfilesConfigFile configFile = new ProfilesConfigFile("C:/Users/Matthew/.aws/credentials");
    ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(configFile, "default");
    @Bean
    public AmazonS3 s3client() {
        // This will automatically use the default credentials provider chain
        return AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.EU_WEST_1)
                .build();
    }
}
