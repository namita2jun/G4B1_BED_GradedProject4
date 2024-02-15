package com.greatLearning.employeeManagement.util;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.greatLearning.employeeManagement.model.Employees;
import com.greatLearning.employeeManagement.model.Role;
import com.greatLearning.employeeManagement.model.User;
import com.greatLearning.employeeManagement.repository.EmployeeJpaRepository;
import com.greatLearning.employeeManagement.repository.UserJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BootstrapAppData {

	private final EmployeeJpaRepository employeeRepository;
	private final UserJpaRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	@Value("${app.employeesCount}")
	private int employeesCount;

	private final Faker fake = new Faker();

	@EventListener(ApplicationReadyEvent.class)
	public void handleApplicationReadyEvent(ApplicationReadyEvent event) {
		IntStream.range(1, employeesCount).forEach(val -> {
			Name firstName = fake.name();
			Name lastName = fake.name();
			Employees employee = Employees.builder().firstName(firstName.firstName()).lastName(lastName.lastName())
					.email(firstName.firstName() + "@" + fake.internet().domainName()).build();
			this.employeeRepository.save(employee);
		});

	}

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void bootstrapUserData(ApplicationReadyEvent event) {
		String encodedPassword = this.passwordEncoder.encode("welcome");
		User kiran = User.builder().email("kiran@gmail.com").name("kiran").password(encodedPassword).build();

		User vinay = User.builder().email("vinay@gmail.com").name("vinay").password(encodedPassword).build();
		Role userRole = Role.builder().roleName("ROLE_USER").build();
		Role adminRole = Role.builder().roleName("ROLE_ADMIN").build();

		kiran.addRole(userRole);

		vinay.addRole(userRole);
		vinay.addRole(adminRole);

		this.userRepository.save(kiran);
		this.userRepository.save(vinay);
		IntStream.range(1, employeesCount).forEach(val -> {
			Name name = fake.name();
			User user = User.builder().name(name.name()).email(name.firstName() + "@" + fake.internet().domainName())
					.password("12345").build();

			user.addRole(userRole);
			this.userRepository.save(user);

		});
	}
}
