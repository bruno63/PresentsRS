package org.opentdc.presents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.opentdc.presents.exception.DuplicateException;
import org.opentdc.presents.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/presents")
public class PresentService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, PresentData> data = new HashMap<String, PresentData>();
	private static final String mongodbHost = "localhost";
	private static final int mongodbPort = 27017;
	private static final boolean persistentData = true;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PresentData> list() {
		List<PresentData> _list = getData();
		logger.info("list() -> " + getDataSize() + " values");
		return _list;
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PresentData create(PresentData dataObj) throws DuplicateException {
		if (getData(dataObj.getId()) != null) {
			throw new DuplicateException();
		}

		// TODO: do we need to validate dataObj with BeanPropertyBindingResult ?
		// see example in LocationService

		setNewID(dataObj);
		storeData(dataObj);
		return dataObj;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PresentData read(@PathParam("id") String id)
			throws NotFoundException {
		PresentData _dataObj = getData(id);
		if (_dataObj == null) {
			throw new NotFoundException();
		}
		// response.setId(id);
		logger.info("read(" + id + "): " + _dataObj);
		return _dataObj;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PresentData update(PresentData dataObj) {
		if (getData(dataObj.getId()) == null) {
			setNewID(dataObj);
		}
		storeData(dataObj);
		return dataObj;
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") String id) throws NotFoundException {
		PresentData _dataObj = getData(id);

		if (_dataObj == null) {
			throw new NotFoundException();
		}
		removeData(id);
		logger.info("delete(" + id + ")");
	}

	@DELETE
	@Path("/")
	public void deleteAll() {
		clearData();
		logger.info("all data deleted");
	}

	@GET
	@Path("/count")
	public int count() {
		return getDataSize();
	}

	// we encapsulate the access to the persistency in order to prepare for
	// pluggable persistence implementations
	// TODO: create external interface and implementations for internal,
	// mongodb, opencrx...
	private void setNewID(PresentData dataObj) {
		String _id = UUID.randomUUID().toString();
		dataObj.setId(_id);
	}

	private void storeData(PresentData dataObj) {
		data.put(dataObj.getId(), dataObj);
	}

	private PresentData getData(String id) {
		return data.get(id);
	}

	private List<PresentData> getData() {
		return new ArrayList<PresentData>(data.values());
	}

	private int getDataSize() {
		int _retVal = 0;
		if (data != null) {
			_retVal = data.size();
		}
		return _retVal;
	}

	private void removeData(String id) {
		data.remove(id);
	}

	private void clearData() {
		data.clear();
	}
}
