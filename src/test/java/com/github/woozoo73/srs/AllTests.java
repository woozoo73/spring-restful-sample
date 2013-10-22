package com.github.woozoo73.srs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.github.woozoo73.srs.controller.MemberControllerTest;
import com.github.woozoo73.srs.dao.MemberDaoTest;
import com.github.woozoo73.srs.service.MemberServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	AbstractTest.class, 
	MemberDaoTest.class, 
	MemberServiceTest.class, 
	MemberControllerTest.class, 
})
public class AllTests {

}
