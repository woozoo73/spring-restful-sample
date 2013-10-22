package com.github.woozoo73.srs.it;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.github.woozoo73.srs.AbstractIT;
import com.github.woozoo73.srs.domain.Member;
import com.github.woozoo73.srs.domain.MemberList;

public class MemberIT extends AbstractIT {

	private String contextUrl = "http://localhost:8080/";

	@Autowired
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		try {
			MemberList memberList = getAllList(HttpStatus.OK);

			List<Member> list = memberList.getMember();
			for (Member member : list) {
				delete(member.getId(), HttpStatus.OK);
			}
		} catch (Throwable e) {
			// ignore
		}
	}

	@Test
	public void testCreate() {
		create(member, HttpStatus.CREATED);
	}

	@Test
	public void testCreateDuplicateMember() {
		create(member, HttpStatus.CREATED);
		create(member, HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testUpdate() {
		create(member, HttpStatus.CREATED);

		String newName = "foo";
		Integer newAge = 20;
		member.setName(newName);
		member.setAge(newAge);

		update(member, HttpStatus.OK);

		Member loaded = get(member.getId(), HttpStatus.OK);
		assertEquals(member.getId(), loaded.getId());
		assertEquals(newName, loaded.getName());
		assertEquals(newAge, loaded.getAge());
	}

	@Test
	public void testUpdateNotExist() {
		update(member, HttpStatus.NOT_FOUND);
	}

	@Test
	public void testDelete() {
		create(member, HttpStatus.CREATED);
		delete(member.getId(), HttpStatus.OK);
		delete(member.getId(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testGet() {
		restTemplate.postForEntity(contextUrl + "members", member, null);

		Member loaded = get(member.getId(), HttpStatus.OK);
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test
	public void testGetNotExist() {
		get(member.getId(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testGetAllList() {
		create(member, HttpStatus.CREATED);

		MemberList memberList = getAllList(HttpStatus.OK);
		List<Member> list = memberList.getMember();
		assertEquals(1, list.size());

		Member loaded = list.get(0);
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test
	public void testGetAllListNoContent() {
		getAllList(HttpStatus.NO_CONTENT);
	}

	private void create(Member member, HttpStatus expected) {
		try {
			ResponseEntity<?> response = restTemplate.postForEntity(contextUrl + "members", member, null);
			assertEquals(expected, response.getStatusCode());
		} catch (HttpClientErrorException e) {
			assertEquals(expected, e.getStatusCode());
		}
	}

	private void update(Member member, HttpStatus expected) {
		try {
			restTemplate.put(contextUrl + "members/{id}", member, member.getId());
		} catch (HttpClientErrorException e) {
			assertEquals(expected, e.getStatusCode());
		}
	}

	private void delete(String id, HttpStatus expected) {
		try {
			restTemplate.delete(contextUrl + "members/{id}", id);
		} catch (HttpClientErrorException e) {
			assertEquals(expected, e.getStatusCode());
		}
	}

	private Member get(String id, HttpStatus expected) {
		try {
			ResponseEntity<Member> response = restTemplate.getForEntity(contextUrl + "members/{id}", Member.class, id);
			assertEquals(expected, response.getStatusCode());

			return response.getBody();
		} catch (HttpClientErrorException e) {
			assertEquals(expected, e.getStatusCode());

			return null;
		}
	}

	private MemberList getAllList(HttpStatus expected) {
		try {
			ResponseEntity<MemberList> response = restTemplate.getForEntity(contextUrl + "members", MemberList.class);
			assertEquals(expected, response.getStatusCode());

			return response.getBody();
		} catch (HttpClientErrorException e) {
			assertEquals(expected, e.getStatusCode());

			return null;
		}
	}

}
