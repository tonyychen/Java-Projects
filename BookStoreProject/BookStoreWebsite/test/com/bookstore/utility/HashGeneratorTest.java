package com.bookstore.utility;

public class HashGeneratorTest {
	public static void main(String[] args) throws HashGenerationException {
		String secret = "hello";
		String hashedSecret = HashGenerator.generateMD5(secret);
		System.out.println(hashedSecret);
	}
}
