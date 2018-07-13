package com.stampbot.knowledge.service;

import com.google.common.collect.Maps;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class HelloWorldExample implements AutoCloseable {
	private final Driver driver;

	public HelloWorldExample(String uri, String user, String password) {
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
	}

	@Override
	public void close() {
		driver.close();
	}

	public void printGreeting(final String message) {
		try (Session session = driver.session()) {
			String greeting = session.writeTransaction(tx -> {
				tx.run("", Maps.newHashMap());
				StatementResult result = tx.run("CREATE (a:Relation) " +
								"SET a.message = $message " +
								"RETURN a.message + ', from node ' + id(a)",
						parameters("message", message));
				return result.single().get(0).asString();
			});
			System.out.println(greeting);
		}
	}

	public static void main(String... args) throws Exception {
		try (HelloWorldExample greeter = new HelloWorldExample("bolt://localhost:7687", "neo4j", "secret")) {
			greeter.printGreeting("hello, world");
		}
	}
}