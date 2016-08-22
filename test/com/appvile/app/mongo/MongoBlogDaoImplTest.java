package com.appvile.app.mongo;

import static org.junit.Assert.*;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appville.MongoBlog.config.ApplicationContextConfig;


@ContextConfiguration(classes = ApplicationContextConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoBlogDaoImplTest {

	@Autowired
	private MongoBlogDaoImpl testDao;
	private static Document smith;
	private static Document jones;
	private static Document brown;
	private static Document brown2;
	private List<Document> testDocList;

	@Before
	public void setUp() throws Exception {
		testDao.getColl().drop();

	}

	@BeforeClass
	public static void setupClass() {
		smith = new Document("name", "Smith")
				.append("age", 30)
				.append("profession", "programmer");

		jones = new Document("name", "Jones")
				.append("age", 35)
				.append("profession", "hacker");
		brown = new Document("name", "Brown")
				.append("age", 25)
				.append("profession", "blogger");
		brown2 = new Document("name", "Brown")
				.append("age", 19)
				.append("profession", "student");
	}

	@Test
	public void testInsertSuccess() {
		assertEquals(testDao.create(jones), jones);
	}

	@Test
	public void testBulkInsertSuccess() {
		testDocList = new ArrayList<>(Arrays.asList(smith, brown));
		List<Document> returnList = testDao.bulkCreate(testDocList);
		assertEquals(returnList, testDocList);
		assertThat(testDocList, IsIterableContainingInOrder.contains(returnList.toArray()));
	}
	
	@Test
	public void testFindAllWithFilterSuccess(){
		testDocList = new ArrayList<>(Arrays.asList(brown2, brown, smith));
		testDao.bulkCreate(testDocList);
		
		Bson filter = and(eq("name", "Brown"),eq("age", 19));
		List<Document> returnList = testDao.readAllByFilter(filter);
		assertNotNull(returnList);
		assertThat(Arrays.asList(brown2), IsIterableContainingInOrder
				.contains(returnList.toArray()));
	}

}
