package com.github.woozoo73.srs;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.woozoo73.srs.domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:test-applicationContext.xml" })
@Ignore
public class AbstractTest {

	protected Member member;

	@Before
	public void setUp() throws Exception {
		member = randomMember();
	}

	@After
	public void tearDown() throws Exception {
	}

	protected Member randomMember() {
		String id = UUID.randomUUID().toString();
		String name = id + "-name";
		Integer age = (int) (100 * Math.random());

		Member member = new Member();
		member.setId(id);
		member.setName(name);
		member.setAge(age);

		return member;
	}

}
