package org.agid.restrobustezza;

import javax.ws.rs.core.Response;

public class RESTBlockingImpl implements RESTBlockingIface {
    @Override
    public Response PushMessage(MType M, int id_resource) {
		MResponseType returnValue = new MResponseType();
		returnValue.c = id_resource + "";
		return Response.status(200).entity(returnValue).build();
	}
}
