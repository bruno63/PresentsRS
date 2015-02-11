package org.opentdc.presents.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.opentdc.presents.PresentData;
import org.opentdc.presents.PresentService;
import org.opentdc.presents.exception.DuplicateException;
import org.opentdc.presents.exception.NotFoundException;
import org.opentdc.presents.exception.ValidationException;

public class PresentTest {
	private static final String APP_URI = "http://localhost:8080/opentdc/api/presents/";
	private static WebClient webclient = null;

	@BeforeClass
	public static void initializeTests() {
		System.out.println("initializing");
		JAXRSClientFactoryBean _sf = new JAXRSClientFactoryBean();
		_sf.setResourceClass(PresentService.class);
		_sf.setAddress(APP_URI);
		BindingFactoryManager _manager = _sf.getBus().getExtension(
				BindingFactoryManager.class);
		JAXRSBindingFactory _factory = new JAXRSBindingFactory();
		_factory.setBus(_sf.getBus());
		_manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
				_factory);
		// PresentService _service = _sf.create(PresentService.class);
		webclient = _sf.createWebClient();
	}

	@After
	public void reset() {
		System.out.println("resetting the web client");
		webclient.reset();
	}

	@AfterClass
	public static void cleanup() {
		System.out.println("cleaning up");
		webclient.close();
	}

	private List<PresentData> list() throws Exception {
		System.out.println("listing all presents");
		webclient.type(MediaType.APPLICATION_JSON).accept(
				MediaType.APPLICATION_JSON);
		List<PresentData> _collection = (List<PresentData>) webclient
				.getCollection(PresentData.class);
		return _collection;
	}

	private PresentData create(PresentData p) {
		System.out.println("creating a present");
		webclient.type(MediaType.APPLICATION_JSON).accept(
				MediaType.APPLICATION_JSON);
		Response _r = webclient.post(p);
		return _r.readEntity(PresentData.class);
	}

	private PresentData read(String id) throws Exception {
		System.out.println("reading a present");
		webclient.accept(MediaType.APPLICATION_JSON);
		return webclient.path(id).get(PresentData.class);
	}

	private PresentData update(PresentData p) {
		System.out.println("updating a present");
		webclient.type(MediaType.APPLICATION_JSON).accept(
				MediaType.APPLICATION_JSON);
		Response _r = webclient.put(p);
		return _r.readEntity(PresentData.class);
	}

	private int delete(String id) {
		System.out.println("deleting a present");
		return webclient.path(id).delete().getStatus();
	}

	private int deleteAll() {
		System.out.println("deleting all presents");
		// return webclient.delete().getStatus();
		// we don't execute this by default; please be careful and think twice
		// before enabling
		return 200;
	}

	private int count() throws Exception {
		return webclient.path("count").get(Integer.class);
		/*
		 * URL _url = new URL(APP_URI + "count"); InputStream _in =
		 * _url.openStream(); int _recs = new
		 * Integer(getStringFromInputStream(_in));
		 * System.out.println("count() = " + _recs + " records"); _in.close();
		 * return _recs;
		 */
	}

	@Test
	public void crudTests() throws Exception {
		PresentData _p0 = new PresentData();
		// TODO: set some attributes manually
		org.junit.Assert.assertNull("initially, there should be no ID",
				_p0.getId());
		org.junit.Assert.assertEquals("there should be no data to start with",
				0, count());
		PresentData _p1 = create(_p0);
		org.junit.Assert
				.assertNotNull("a unique ID should be set", _p1.getId());
		// TODO: all other attributes should be the same.

		PresentData _p2 = create(new PresentData());
		org.junit.Assert
				.assertNotNull("a unique ID should be set", _p2.getId());
		org.junit.Assert.assertNotSame("IDs should be different", _p1.getId(),
				_p2.getId());
		// TODO: check on the default attribute values of _p2
		// TODO: check the date format
		// TODO: try to set invalid data attributes

		PresentData _p3 = create(new PresentData());
		org.junit.Assert
				.assertNotNull("a unique ID should be set", _p3.getId());
		org.junit.Assert.assertNotSame("IDs should be different", _p2.getId(),
				_p3.getId());
		org.junit.Assert.assertNotSame("IDs should be different", _p1.getId(),
				_p3.getId());
		org.junit.Assert.assertEquals("there should be 3 presents", 3, count());
		try {
			create(_p1);
			org.junit.Assert.assertTrue(
					"creating a duplicate should raise an exception", true);
		} catch (DuplicateException _ex) {
			System.out
					.println("DuplicateException was raised correctly when trying to create a duplicate");
			// test for exception message
		}
		List<PresentData> _l = list();
		System.out.println("list() = <" + _l + ">");
		PresentData _p11 = read(_p1.getId());
		assertEquals("ID should be equal when read twice", _p1.getId(),
				_p11.getId());
		// TODO: same for all attributes

		try {
			read(null);
			org.junit.Assert.assertTrue(
					"reading a present with ID = null should fail", true);
		} catch (ValidationException ex) {
			System.out
					.println("ValidationException was raised correctly for invalid ID");
		}
		try {
			read("12366");
			org.junit.Assert.assertTrue(
					"reading a non-existing present should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 3 presents", 3, count());

		String _id = _p2.getId();
		System.out.println("status = " + delete(_id));

		assertEquals("there should be only 2 presents", 2, count());

		try {
			System.out.println("status = " + delete(_id));
			org.junit.Assert.assertTrue(
					"deleting a deleted present should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}
		assertEquals("there should be still 2 presents", 2, count());
		try {
			read(_id);
			org.junit.Assert.assertTrue(
					"reading a deleted present should fail", true);
		} catch (NotFoundException ex) {
			System.out.println("NotFoundException was raised correctly");
		}

		_p1.setFrom("hans");
		_p1.setTo("fritz");
		_p1.setComment("changed");
		_p1 = update(_p1);
		assertEquals("from should be hans", "hans", _p1.getFrom());
		assertEquals("to should be fritz", "fritz", _p1.getTo());
		assertEquals("comment should be changed", "changed", _p1.getComment());

		_p2 = read(_p1.getId());
		assertEquals("from should be hans", "hans", _p1.getFrom());
		assertEquals("to should be fritz", "fritz", _p1.getTo());
		assertEquals("comment should be changed", "changed", _p1.getComment());
		assertEquals("IDs should be the same", _p1.getId(), _p2.getId());

		// - update(4) -> not Found
		System.out.println("status = " + deleteAll());
		assertEquals("there should be no presents left", 0, count());
		assertEquals("there should be no presents left", 0, count());
	}

	/*
	 * Test for exceptions: try { mustThrowException(); fail(); } catch
	 * (Exception e) { // expected // could also check for message of exception
	 * etc. }
	 */
	public static void main(String[] args) {
		Result _result = JUnitCore.runClasses(PresentTest.class);
		System.out
				.println("BEWARE: this test will the data will be emptied when running this test !!");
		System.out.println("Proceed ? (y/n)");
		BufferedReader _br = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			if (_br.readLine().toLowerCase().startsWith("y")) {
				for (Failure _failure : _result.getFailures()) {
					System.out.println(_failure.toString());
				}
			}
		} catch (IOException ioe) {
			System.out.println("IO error trying to read command line input");
		}
	}
}
