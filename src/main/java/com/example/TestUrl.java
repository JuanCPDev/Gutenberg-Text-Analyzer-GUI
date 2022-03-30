package com.example;

import org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUrl {

	@Test
	void test() {
		App listSize = new App("https://www.gutenberg.org/files/67345/67345-h/67345-h.htm");
		int output =  listSize.run();
		assertEquals(20
				,output);
	}

}
