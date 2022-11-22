package com.labs.spring.core;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beans-config.xml");
		int count = ctx.getBeanDefinitionCount();
		System.out.println("Number of beans created :"+count);
		String[] beanDefNames = ctx.getBeanDefinitionNames();
		for(String beanName : beanDefNames) {
			System.out.println(beanName);
		}
		
		Order order = ctx.getBean("order1",Order.class);
		System.out.println(order);
	}
}
