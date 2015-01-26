package demo.jaxrs.helloworld;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Produces({MediaType.APPLICATION_JSON})
public class HelloWorldRS {
 
	/**
	 * HelloWorld
	 *
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement
	public static class HelloWorld {
	
		private String id;

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "HelloWorld [id=" + id + "]";
		}
		
	}

	/**
	 * SayHelloResponse
	 *
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement
	public static class SayHelloResponse {
	
		private String text;

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		/**
		 * @param text the text to set
		 */
		public void setText(String text) {
			this.text = text;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "SayHelloWorldResponse [text=" + text + "]";
		}
		
	}

	@GET
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public HelloWorld getHelloWorld(
		@PathParam("id")String id
	) throws Exception {
		HelloWorld response = new HelloWorld();
		response.setId(id);
        System.out.println("getHelloWorld: " + response);
        return response;
    }

	@GET
    @Path("/{id}/sayHello")
	@Produces(MediaType.APPLICATION_JSON)
	public SayHelloResponse sayHello(
		@PathParam("id")String id
	) throws Exception {
		SayHelloResponse response = new SayHelloResponse();
		String text = null;
        if("en".equals(id)) {
        	text = "Hello";
        } else if("de".equals(id)) {
        	text = "Hallo";
        } else if("fr".equals(id)) {
        	text = "Salut";
        } else {
        	text = "N/A";
        }
        response.setText(text);
        System.out.println("sayHello: " + response);
        return response;
    }
	
}
