package com.JavaJunkie.JavaJunkie;

import com.JavaJunkie.JavaJunkie.Service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class GitHubUserActivityApplication implements CommandLineRunner {

	@Autowired
	private GitHubService gitHubService;

	public static void main(String[] args) {
		SpringApplication.run(GitHubUserActivityApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter GitHub username: ");
		String username = scanner.nextLine();
		scanner.close();

		String activity = gitHubService.fetchGitHubActivity(username);
		System.out.println("\nGitHub Activity for " + username + ":\n");
		System.out.println(activity);
	}
}
