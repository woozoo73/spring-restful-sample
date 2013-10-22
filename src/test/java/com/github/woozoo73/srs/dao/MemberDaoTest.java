package com.github.woozoo73.srs.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.woozoo73.srs.AbstractTest;
import com.github.woozoo73.srs.domain.Member;

public class MemberDaoTest extends AbstractTest {

	@Autowired
	private MemberDao memberDao;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		List<Member> list = memberDao.getAllList();
		for (Member member : list) {
			memberDao.delete(member.getId());
		}
	}

	@Test
	public void testCreate() {
		memberDao.create(member);

		Member loaded = memberDao.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test(expected = RuntimeException.class)
	public void testCreateDuplicateMember() {
		memberDao.create(member);
		memberDao.create(member);
	}

	@Test
	public void testUpdate() {
		memberDao.create(member);

		String newName = "foo";
		Integer newAge = 20;
		member.setName(newName);
		member.setAge(newAge);

		int updated = memberDao.update(member);
		assertEquals(1, updated);

		Member loaded = memberDao.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(newName, loaded.getName());
		assertEquals(newAge, loaded.getAge());
	}

	@Test
	public void testUpdateNotExist() {
		int updated = memberDao.update(member);
		assertEquals(0, updated);
	}

	@Test
	public void testDelete() {
		memberDao.create(member);

		int deleted = memberDao.delete(member.getId());
		assertEquals(1, deleted);

		Member loaded = memberDao.get(member.getId());
		assertNull(loaded);
	}

	@Test
	public void testDeleteNotExist() {
		int deleted = memberDao.delete(member.getId());
		assertEquals(0, deleted);
	}

	@Test
	public void testGet() {
		memberDao.create(member);

		Member loaded = memberDao.get(member.getId());
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test
	public void testGetNotExist() {
		Member loaded = memberDao.get(member.getId());
		assertNull(loaded);
	}

	@Test
	public void testGetAllList() {
		List<Member> list = memberDao.getAllList();
		assertEquals(0, list.size());

		memberDao.create(member);

		list = memberDao.getAllList();
		assertEquals(1, list.size());

		Member loaded = list.get(0);
		assertEquals(member.getId(), loaded.getId());
		assertEquals(member.getName(), loaded.getName());
		assertEquals(member.getAge(), loaded.getAge());
	}

	@Test
	public void testGetAllListEmpty() {
		List<Member> list = memberDao.getAllList();
		assertTrue(list.isEmpty());
	}

}
